
# Creating Spark Applications

In the *shell* everything is nice, right? But most of the time we want applications we can run multiple times. We need applications that can be deployed, and executed in different environments. 

## The process

- Create Scala Application
- Build an *assemly* with external dependencies if required.
- Deploy *assembly* using *Spark Submit* command. 

Let's start with **build.sbt**

**build.sbt**
```
name := "spark-demo"

version := "1.0"

scalaVersion := "2.12."

libraryDependencies += "org.apache.spark" % "spark-core_2.12" % "2.0.1"
```

**app.scala**

```
package com.nico.spark-demo

object app {
  def main(args: Array[String]) {
  
    val config = new SparkConf().setAppName("spark-demo")
    val sc = new SparkContext(config)

    sc.setLogLevel("WARN")

    val linesRDD = sc.textFile("/Users/anicolaspp/b.txt")

    val sorted = 
      linesRDD
        .flatMap(_.split(" "))
        .map(w => (w, 1))
        .reduceByKey(_ + _)
        .map {case (x, y) => (y, x)}
        .sortByKey(false)

    sorted.saveAsTextFile("/Users/anicolaspp/out_dir")
  }
}
```

`sbt compile package`

```
./spark-submit --class app \
                --jar <ourjar>.jar
```

# Some *Best* Practices we use

- build a runner with all the configuration need (log, args).
- create an app that the runner can execute.
- test your application (unit tests and integration tests).
