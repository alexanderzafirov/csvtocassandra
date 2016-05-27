## ETL/copy of data from S3 to Cassandra

### System info
| Category         | Value                                   |
|:----------------:|:----------------------------------------|
| Operating system |  Linux Mint 17.2 Cinnamon 64-bit        |
| Cinnamon Version |                          2.6.13         |
| Linux Kernel     |  3.16.0-38-generic                      |
| Processor        |  Inter Core i7-4710HQ CPU @ 2.50 GHz x 4|
| Memory           |  15.6 GB                                |
| Memory           |  15.6 GB                                |


### Setup
 - Install [Cassandra3.5](http://mirrors.rackhosting.com/apache/cassandra/3.5/apache-cassandra-3.5-bin.tar.gz) locally
 - Follow [quick start guide](https://wiki.apache.org/cassandra/GettingStarted)

### How to use
1. In the root of the dir run `sbt`
2. Inside sbt execute e.g. `runMain MainApp /home/zbz/data/part-00000 10 4`,
   where the last three parameters are
```
  filename
        Path to CSV file
  batchsize
        Number of rows per single batch
  maxcores
        Number of cores to use by this application
```

### Frameworks/libraries used
 - scopt to provide command line arguments
 - spark-cassandra-connector, spark-core and spark-sql for programmatic schema generation
   and parallel batch insert into cassandra
 - typesafe config for standardized Scala configuration

### Processing duration

| CSV           | Duration(s)|
|:-------------:|:-----------|
| part-00000.gz |  301       |
| part-00001.gz |  329       |
| part-00002.gz |  283       |
| part-00003.gz |  298       |
| part-00004.gz |  305       |
| part-00005.gz |  277       |
| part-00006.gz |  294       |
| part-00007.gz |  337       |
| part-00008.gz |  303       |
| part-00009.gz |  297       |
| part-00010.gz |  294       |
| part-00011.gz |  311       |
| part-00012.gz |  289       |
| part-00013.gz |  306       |
| part-00014.gz |  308       |
| part-00015.gz |  285       |
| part-00016.gz |  304       |
| part-00017.gz |  282       |
| part-00018.gz |  301       |
| part-00019.gz |  285       |
| part-00021.gz |  336       |
| part-00022.gz |  290       |
| part-00023.gz |  293       |
| part-00024.gz |  284       |
| part-00025.gz |  283       |
| part-00026.gz |  284       |
| part-00027.gz |  306       |
| part-00028.gz |  324       |
| part-00029.gz |  325       |

Total:  8714s ~ 2.45h

### What could be done better:
 - assemble a fat jar with spark dependencies and use that instead of running code on server
 - fine tune spark and cassandra for optimal performance
 - separation of concerns within the app
 - add tests to verify csv parsing
