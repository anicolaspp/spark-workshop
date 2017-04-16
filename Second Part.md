# Part two (1.5 - 2 hours)

# What is Spark?

[Spark, The Ultimate Scala Collections, by Martin Ordersky](https://www.slideshare.net/SparkSummit/spark-the-ultimate-scala-collections-by-martin-odersky)

- Spark is a `D`omain `S`pecific `L`anguage (`DSL`) on top of Scala for distributed data processing.
- Implemented in Scala
- Embeded in Scala as a host language

![alt tag](spark-the-ultimate-scala-collections-by-martin-odersky-5-638.jpg)


- Immutable data set and functional transformers.
- Support for 
  - Scala 
  - Java 
  - Python
  - R
  
### Why Spark makes heavy use of `type`s?

- Functional operations do not have hidden dependencies.
- Interactions are given in terms of `types`.
- Logic errors are (usually) translated into `types` errors.

# Spark Context, Interacting with the Outside World

We normally get a *SparkContext* (`sc`) when we open the shell. The `sc` has methods to interact with the outside world.

```
val linesRDD = sc.textFile("/Users/anicolaspp/b.txt")
```
Running this `.textFile` operation does nothing, it just makes a transformation that will be exucuted later on. 

Let's do something with our *lines*.

```
linesRDD.count()
```

```
res0: Long = 131843   
```

# Computational Model & Resilient Distributed Datasets (`RDD`s)

- map
- mapPartitions
- flatMap
- filter
- reduce
- fold
- aggregate
- union
- intersaction
- distinct 

### Map

*map* is a functional transformater.

```
def map[A, B, M[_]](ma: M[A], f: A => B): M[B]
```
***Monads*** have `map` and in Scala collection are *Monads*.

`RDD`s are collections, so they have `map`!

```
val linesRDD = sc.textFile("/Users/anicolaspp/b.txt")

val lineLengthsRDD = linesRDD.map(line => line.length)

counts.foreach(println)
```

### FlatMap

*flatMap* is another functional transformation, but a little more interesting

```
def flatMap[A, B, M[_]](ma: M[A], f: A => M[B]): M[B]
```

Let's get some use for it. 

We can use `flatMap` to extract *each* word from *line*

```
val wordsRDD = linesRDD.flatMap(_.split(" "))
```

```
res5: org.apache.spark.rdd.RDD[String] = MapPartitionsRDD[3] at flatMap at <console>:27
```

Again, nothing is executed. Let's run a *foreach* to print some of them out.

```
wordsRDD.take(20).foreach(pritnln)
```

### Filter

In the same way we used `map` and `flatMap`, we can use *filter*

```
def filter[A, M[_]](ma: M[A], f: A => Boolean): M[A]
```

Let's use it now.

```
val lordRDD = wordsRDD.filter(word => word == "Lord")

lordRDD.count()

res6: Long = 5000
```
