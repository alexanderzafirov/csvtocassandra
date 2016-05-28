## ETL/copy of data from S3 to Cassandra

### How to use
1. In the root of the dir run `sbt`
2. Inside sbt execute e.g. `runMain CsvToCassandraApp /home/zbz/data/part-00000 10 4`,
   where the last three parameters are
```
  filename
        Path to CSV file
  batchsize
        Number of rows per single batch
  maxcores
        Number of cores to use by this application
```

### Processing duration

| CSV           | Duration(s)|
|:-------------:|:-----------|
| part-00000    |  301       |
| part-00001    |  329       |
| part-00002    |  283       |
| part-00003    |  298       |
| part-00004    |  305       |
| part-00005    |  277       |
| part-00006    |  294       |
| part-00007    |  337       |
| part-00008    |  303       |
| part-00009    |  297       |
| part-00010    |  294       |
| part-00011    |  311       |
| part-00012    |  289       |
| part-00013    |  306       |
| part-00014    |  308       |
| part-00015    |  285       |
| part-00016    |  304       |
| part-00017    |  282       |
| part-00018    |  301       |
| part-00019    |  285       |
| part-00021    |  336       |
| part-00022    |  290       |
| part-00023    |  293       |
| part-00024    |  284       |
| part-00025    |  283       |
| part-00026    |  284       |
| part-00027    |  306       |
| part-00028    |  324       |
| part-00029    |  325       |

Total:  8714s ~ 2.45h

### What could be done better:
 - assemble a fat jar with spark dependencies and use that instead of running code on server
 - fine tune spark and cassandra for optimal performance
 - separation of concerns within the app
 - add tests to verify csv parsing
