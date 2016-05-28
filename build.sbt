name := "csvtocassandra"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.datastax.spark" % "spark-cassandra-connector_2.11" % "1.6.0-M2",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.2",
  "org.apache.spark" % "spark-core_2.11" % "1.6.1",
  "org.apache.spark" % "spark-sql_2.11" % "1.6.1",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.akka" % "akka-http-core_2.11" % "2.4.4",
  "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.4"
)