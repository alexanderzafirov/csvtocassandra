package csvtocassandra

import com.typesafe.config._
import org.apache.spark.{Logging, SparkConf, SparkContext}

case class CmdLineArgs(filename: String = "exampleCsv", batchSize: String = "10", maxCores: String = "4")

object CsvToCassandraApp extends App with Logging {

  override def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[CmdLineArgs]("csvToCassandra") {
      head("CSV to Cassandra")
      arg[String]("filename") action { (arg, config) => config.copy(filename = arg)} text {
        "Path to CSV file"
      }
      arg[String]("batchsize") action { (arg, config) => config.copy(batchSize = arg)} text {
        "Number of rows per single batch"
      }
      arg[String]( "maxcores") action { (arg, config) => config.copy(maxCores = arg)} text {
        "Number of cores to use by this application"
      }

      help("help") text {
        "CLI Help"
      }
    }

    parser.parse(args, CmdLineArgs()) map { args =>
      init(args)
    } getOrElse {
      System.exit(1)
    }
  }

  private def init(args: CmdLineArgs): Unit = {
    val typeSafeConf = ConfigFactory.load()

    val sparkConf = new SparkConf(true)
      .setMaster(typeSafeConf.getString("spark.master"))
      .setAppName(getClass.getSimpleName)
      .set("spark.cores.max", args.maxCores)
      .set("spark.cassandra.connection.host", typeSafeConf.getString("spark.cassandra.connection.host"))
      .set("spark.cassandra.output.batch.size.rows", args.batchSize)
      .set("spark.cassandra.output.concurrent.writes", typeSafeConf.getInt("spark.cassandra.concurrent.writes").toString)

    val keyspaceName = typeSafeConf.getString("spark.cassandra.keyspace")
    val table = typeSafeConf.getString("spark.cassandra.table")

    insertDbTable(sparkConf, keyspaceName, table)

    val sc = new SparkContext(sparkConf)

    val start = System.currentTimeMillis()
    csvToCassandra(sc, args.filename, keyspaceName, table)
    val end = System.currentTimeMillis()
    val timeDiff = end - start

    log.info("Processing time = " + timeDiff)
  }

  private def insertDbTable(conf: SparkConf, keyspaceName: String, tableName: String): Unit = {
    import com.datastax.spark.connector.cql.CassandraConnector

    CassandraConnector(conf).withSessionDo { session =>
      session.execute(
        s"""CREATE KEYSPACE IF NOT EXISTS $keyspaceName with replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }"""
      )
      session.execute(
        s"""CREATE TABLE IF NOT EXISTS $keyspaceName.$tableName (
                          observed_date_min_as_infaredate smallint,
                          observed_date_max_as_infaredate smallint,
                          full_weeks_before_departure int,
                          carrier_id int,
                          searched_cabin_class text,
                          booking_site_id int,
                          booking_site_type_id tinyint,
                          is_trip_one_way tinyint,
                          trip_origin_airport_id int,
                          trip_destination_airport_id int,
                          trip_min_stay smallint,
                          trip_price_min float,
                          trip_price_max float,
                          trip_price_avg float,
                          aggregation_count int,
                          out_flight_departure_date_as_infaredate smallint,
                          out_flight_departure_time_as_infaretime int,
                          out_flight_time_in_minutes smallint,
                          out_sector_count tinyint,
                          out_flight_sector_1_flight_code_id int,
                          out_flight_sector_2_flight_code_id int,
                          out_flight_sector_3_flight_code_id int,
                          home_flight_departure_date_as_infaredate smallint,
                          home_flight_departure_time_as_infaretime int,
                          home_flight_time_in_minutes smallint,
                          home_sector_count tinyint,
                          home_flight_sector_1_flight_code_id int,
                          home_flight_sector_2_flight_code_id int,
                          home_flight_sector_3_flight_code_id int,
                          uuid uuid,
                          observation_week smallint,
                          PRIMARY KEY((observation_week, carrier_id), trip_price_min, uuid)
                        )""")
    }
  }

  private def csvToCassandra(sc: SparkContext, filename:String, keyspaceName: String, tableName: String): Unit = {
    import com.datastax.spark.connector._

    val textFileRDD = sc.textFile(filename)
    val lineRDD = textFileRDD.map(s => new CsvModel(s.split("\t", -1)))

    lineRDD.saveToCassandra(keyspaceName, tableName)
    sc.stop()
  }
}
