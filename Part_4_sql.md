
# Spark SQL, bring your data to everyone. 

- **Spark SQL** is one of the main modules of *Spark*.

## Interoperability with `RDD`s

Given an `RDD` we can create a `DataFrame`.

- `DataFrame`s have structure / shema as any `SQL` table. 
- `DataFrame`s can be queried using pure `SQL`.
- `DataFrame`s can be queried using *Spark SQL DLS*.


```
FROM RDD TO DATAFRAME CODE GOES HERE...
```

## Using `DataFrame`s and `DataSet`s

```
df.printSchema()

df.select(....)

df.registerTempTable("table_name")

sql("SELECT * FROM table_name")
```

## Native CSV, JSON, and XML Support


## Let's play!

![alt tag](sql-dataset.md)

### Using Spark Packages / Libs

## Related Blog Posts

- [Apache Spark as a Distributed SQL Engine](https://medium.com/@anicolaspp/apache-spark-as-a-distributed-sql-engine-4373e254e0f9)
- [Extending Our Spark SQL Query Engine](https://medium.com/hacker-daily/extending-our-spark-sql-query-engine-5f4a088de986)
