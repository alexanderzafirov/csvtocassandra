package rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import net.liftweb.json.{NoTypeHints, Serialization}
import net.liftweb.json.Serialization._
import org.apache.spark.{SparkConf, SparkContext}

object RestApp extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  private val typeSafeConfig = system.settings.config

  val sparkConf = new SparkConf(true)
    .setMaster(typeSafeConfig.getString("spark.master"))
    .setAppName(getClass.getSimpleName)
    .set("spark.cassandra.connection.host", typeSafeConfig.getString("spark.cassandra.connection.host"))
    .set("spark.cassandra.output.concurrent.writes", typeSafeConfig.getString("spark.cassandra.cores.max"))

  val sc = new SparkContext(sparkConf)

  val keyspaceName = typeSafeConfig.getString("spark.cassandra.keyspace")
  val table = typeSafeConfig.getString("spark.cassandra.table")

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
    implicit val formats = Serialization.formats(NoTypeHints)

    val server = Http().bind(host, port)

    val route =
      path("observations") {
        get {
          parameters('observation_week.as[Int], 'carrier_id.as[Int]) { (observationWeek, carrierId) =>
            complete {
              sc.cassandraTable[Observations](keyspaceName, table)
                .where("observation_week = ? AND carrier_id = ?", observationWeek, carrierId)
                .collectAsync()
                .map(write(_))
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