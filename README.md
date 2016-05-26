## ETL/copy of data from S3 to Cassandra

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

### Persistence duration

| CSV           | Duration(s)|
|:-------------:|:-----------|
| part-00000.gz |  false     |
| part-00001.gz |  false     |
| part-00002.gz |  true      |
| part-00003.gz |  true      |
| part-00004.gz |  true      |
| part-00005.gz |  true      |
| part-00006.gz |  true      |
| part-00007.gz |  true      |
| part-00008.gz |  true      |
| part-00009.gz |  true      |
| part-00010.gz |  true      |
| part-00011.gz |  true      |
| part-00012.gz |  true      |
| part-00013.gz |  true      |
| part-00014.gz |  true      |
| part-00015.gz |  true      |
| part-00016.gz |  true      |
| part-00017.gz |  true      |
| part-00018.gz |  true      |
| part-00019.gz |  true      |
| part-00021.gz |  true      |
| part-00022.gz |  true      |
| part-00023.gz |  true      |
| part-00024.gz |  true      |
| part-00025.gz |  true      |
| part-00026.gz |  true      |
| part-00027.gz |  true      |
| part-00028.gz |  true      |
| part-00029.gz |  true      |

Total:


### What could be done better:
 - assemble a fat jar with spark dependencies and use that instead of running code on server
 - fine tune spark for optimal performance
 - separation of concerns within the app
 - add tests
