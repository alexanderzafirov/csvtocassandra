package rest

import akka.actor.ActorSystem
import akka.event.slf4j.Logger
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.apache.spark.{SparkConf, SparkContext}

object MainApp extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  private val log = Logger(this.getClass.getSimpleName)

  private val typeSafeConfig = system.settings.config
  private val maxCores = typeSafeConfig.getString("cassandra.max-cores")

  val sparkConf = new SparkConf(true)
    .setMaster(s"local[$maxCores]")
    .setAppName(getClass.getSimpleName)
    .set("spark.cassandra.connection.host", typeSafeConfig.getString("cassandra.host"))
    .set("spark.cassandra.output.concurrent.writes", maxCores)

  val sc = new SparkContext(sparkConf)

  val keyspaceName = typeSafeConfig.getString("cassandra.keyspace-name")
  val table = typeSafeConfig.getString("cassandra.table")

  setupServer(
    typeSafeConfig.getString("akka.http.server.interface"),
    typeSafeConfig.getInt("akka.http.server.port"),
    sc,
    keyspaceName,
    table
  )

  def setupServer(host: String, port: Int, sc: SparkContext, keyspaceName: String, table: String) = {
    import com.datastax.spark.connector._

    import scala.concurrent.ExecutionContext.Implicits.global

    val server = Http().bind(host, port)

    val route =
      path("data") {
        get {
          parameters("carrier_id") { carrierId =>
            complete {
              sc.cassandraTable[Int](keyspaceName, table)
                .select("carrier_id")
                .where("carrier_id = ? AND observation_week = ?", carrierId, 749)
                .collectAsync()
                .map(_.mkString)
            }
          }
        }
      }

    server.to(Sink.foreach {
      connection =>
        connection handleWith route
    }).run()
  }
}