
# Spark SQL, bring your data to everyone. 

- **Spark SQL** is one of the main modules of *Spark*.

## Related Blog Posts

- [Apache Spark as a Distributed SQL Engine](https://medium.com/@anicolaspp/apache-spark-as-a-distributed-sql-engine-4373e254e0f9)


## Interoperability with `RDD`s

Given an `RDD` we can create a `DataFrame`.

- `DataFrame`s have structure / shema as any `SQL` table. 
- `DataFrame`s can be queried using pure `SQL`.
- `DataFrame`s can be queried using *Spark SQL DLS*.


```
FROM RDD TO DATAFRAME CODE GOES HERE...
```

## Using `DataFrame`s and `DataSet`s

We can load data using the `SparkContext` and then create a `DataSet` from it.

```
val data = sc.textFile("/Users/anicolaspp/workshop-sql-dataset/airlines.csv")

val pairs = data.map(_.split(",")).map(ws => (ws(0), ws(1)))

val airlinesDF = pairs.toDF
```

`airlinesDF` is a data frame, which means it has an schema because it is actually a table.

We can see that schema by doing

```
airlinesDF.printSchema()
```

Now, we want to enforce an *schema* on top of our data. For that we need to create a *Schema* value.

```
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType, StructField, StringType}

val schema = StructType(data.first.split(",").map(fieldName ⇒ StructField(fieldName, StringType, true)))
```
At this point only need to re-create our `DataFrame` enforcing the *Schema*.

```
val pairs = data.map(_.split(",")).map(ws => Row(ws(0), ws(1)))
val airlinesDF = spark.createDataFrame(pairs, schema)

airlinesDF.printSchema()
```

To see our data we could

```
airlineDF.show()
```

*We need to filter out the first line of the file.*

```
val pairs = data.map(_.split(",")).map(ws => Row(ws(0), ws(1)))
```

Do you see anything weird?

*- The headers should not be included in the data.*

```
.filter(line => line != <"HEADER">)
```

### Queries Our Tables

```
airlinesDF.columns

airlinesDF.head

airlinesDF.orderBy("IATA_CODE")

airlinesDF.orderBy("IATA_CODE").show
```
```
airlinesDF.registerTempTable("Airlines")
```
```
spark.sql("select * from Airlines")
spark.sql("select * from Airlines").show

```

## Native CSV, JSON, and XML Support

*Spark SQL* comes with something called *Data Sources* that we can add via **packages**. 

In order to add packages to the `spark-shell` we need to open it with the `--packages` options.

Let's use a `csv` data source by doing.

```
./spark-shell --packages com.databricks:spark-csv_2.11:1.5.0
```	

Now we should have access to the data source.

Let's reload the data (`csv`) using the data source.

```
 val airlinesDF = spark
 	.read
 	.format("com.databricks.spark.csv")
 	.option("header", "true").option("inferSchema", "true")
 	.load("/Users/anicolaspp/workshop-sql-dataset/airlines.csv")
```

In the same way we can write using a *data source*.

```
airlinesDF
	.select("IATA_CODE")
	.write
	.format("com.databricks.spark.csv")
	.option("header", "true")
	.save("codes.csv")
```

There are a lot of data sources for a lot of different data format, but we can also write our own, if we need it. 

This is a detailed example about how we can write one. 

[Extending Our Spark SQL Query Engine](https://medium.com/hacker-daily/extending-our-spark-sql-query-engine-5f4a088de986)

----

## Let's play!

[Data Set](sql-dataset.md)

### Intersting Questions
- How many flights cancelled?
- How many flights delayed?
- What is the average waiting time?
- What is airline with more cancelled flights?
- What is the best airline?
- What is the two destinations with more flights cancelled between them?
- What is the more delayed aircraft? From what airline?
- What is avg time that an airplane spends from landed until it gets to the terminal?
...



