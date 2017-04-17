
# Creating Spark Applications (1 Hour)

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
package com.nico.sparkdemo

import org.apache.spark.{SparkConf, SparkContext}

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
./bin/spark-submit --class "com.nico.sparkdemo.app" \
          .../simple_app/target/scala-2.11/spark-demo_2.11-1.0.jar
```

# Some *Best* Practices we use

- build a runner with all the configuration need (log, args).
- create an app that the runner can execute.
- test your application (unit tests and integration tests).

### SparkApp

```
/**
 * Base class for Spark applications
 */
abstract class SparkApp extends DefaultInitializer {
  def name: String
 
 def execute(implicit sc: SparkContext) : Unit
}

trait SparkInitializer {
  /**
   * Add any needed Spark configuration prior to initializing SparkContext.
   * Once the SparkContext is created, its configuration is immutable.
   */
  def configureSpark(sparkConfig: SparkConf) : Unit

  def initializeSpark(sc: SparkContext) : Unit
}
```

### SparkAppRunner

```
trait SparkAppRunner {
  
  /**
   * Run Spark app
   */
  def executeWithSpark(app: SparkApp): Unit

  private def shutdown(ex: Option[Throwable] = None)(implicit sc: SparkContext): Unit

  /**
   * Configure, create, and initialize a SparkContext
   */
  private def initSparkContext(app: SparkApp): SparkContext

  /**
   * Execute block if app mixes in the SparkInitializer trait
   */
  private def ifInitializer(app: SparkApp, body: (SparkInitializer) => Unit): Unit
}
```

### Main Class

```
object MyApplication extends SparkAppRunner
  with ArgParser {

  def main(args: Array[String]): Unit = {
    val config = argParser.parse(args, TPAppCmdLineConfig()) match {
      case Some(config) => config
      case None         => sys.exit(1)
    }

    val app = SomeApp(config)

    executeWithSpark(app)
  }
}

```

## Configuring Logging

Spark provides a logger that can be used to *log* what the application is doing.

```
lazy val logger = org.slf4j.LoggerFactory.getLogger(getClass)
```

Using the `logger` should be the same as in any other application you have ever written. 

```
logger.debug("HELLO LOGGER")
```

The problem comes when we try to use the `logger` in a distributed fashion. 

the `Logger` class is not serializable which means Sparks is not able the send the instance of the object over the network. 

Lern more about the problem here [How to Log in Apache Spark](https://medium.com/hacker-daily/how-to-log-in-apache-spark-f4204fad78a)

A simple solution we use:

```
trait Logging {
  @transient lazy val logger = org.slf4j.LoggerFactory.getLogger(getClass)
}

```

Now we can mixin the `Logging` *trait* to get access to the logger object.

This might not be the better way to do loggin (in general). Some of us prefer a more *functional* approach to the problem. Scala offers the right tools to solve this problem, once again, using **Monad**s. 

For more information how to use the *Monad Writer* look at this presentation from the **Scala Workshop, Jan, 2017** [Monadic Loggin](https://docs.google.com/presentation/d/1DkoH12dbEKuu2ZcikAavBsEZ3HzKcJrypvPKyLP09D8/edit?usp=sharing)

## Functional (Monadic) Logging? (maybe not for everyone)

[How to Log in Apache Spark, a Functional Apporach](https://medium.com/hacker-daily/how-to-log-in-apache-spark-a-functional-approach-e48ffbbd935b)

## Running an App
