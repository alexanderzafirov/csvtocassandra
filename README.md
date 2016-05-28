## tst

### System info
| Category         | Value                                   |
|:----------------:|:----------------------------------------|
| Operating system |  Linux Mint 17.2 Cinnamon 64-bit        |
| Linux Kernel     |  3.16.0-38-generic                      |
| Processor        |  Inter Core i7-4710HQ CPU @ 2.50 GHz x 4|
| Memory           |  15.6 GB                                |

### Prerequisites
 - Install [Cassandra3.5](http://mirrors.rackhosting.com/apache/cassandra/3.5/apache-cassandra-3.5-bin.tar.gz) locally
 - Follow [quick start guide](https://wiki.apache.org/cassandra/GettingStarted)

### Frameworks/libraries used
 - `scopt` to provide command line arguments
 - `spark-cassandra-connector`, `spark-core` and `spark-sql` for interaction between Apache Spark and Apache Cassandra
 - `typesafe config` for standardized Scala configuration
 - `akka-http and akka-core` for REST Api generation

### Solutions
- [ETL/copy of data from S3 to Cassandra](src/main/scala/csvtocassandra/README.md)
- [Rest service exercise](src/main/scala/rest/README.md)