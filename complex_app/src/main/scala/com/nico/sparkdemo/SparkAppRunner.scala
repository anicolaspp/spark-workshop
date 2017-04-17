/**
  * Created by anicolaspp on 4/16/17.
  */
package com.nico.sparkdemo

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.{Failure, Success, Try}

/**
  * Common code for a VPS spark application
  */
trait SparkAppRunner extends Logging {

  def executeWithSpark(app: SparkApp) = {

    def executeAppWithContext(sc: SparkContext) = Try {
      app.execute(sc)
    } match {
      case Success(_)         =>  shutdown()(sc)
      case Failure(exception) =>  shutdown(Some(exception))(sc)
    }

    executeAppWithContext(initSparkContext(app))
  }

  private def shutdown(ex: Option[Throwable] = None)(implicit sc: SparkContext): Unit = {
    val exitCode = ex.map { e =>
      logger.error("Spark app failed with exception", e)
      1
    }

    logger.info(s"Shutting down Spark app.")

    try { sc.stop() } catch { case t: Throwable => logger.error(s"Error stopping spark app", t) }

    exitCode.foreach { code =>
      logger.error("Exiting with error code " + code)
      System.exit(code)
    }
  }


  /**
    * Configure, create, and initialize a SparkContext
    */
  private def initSparkContext(app: SparkApp): SparkContext = {
    val sparkConfig = new SparkConf().setAppName(app.name)

    // Get app-specific configuration
    ifInitializer(app, _.configureSpark(sparkConfig))

    val sc = new SparkContext(sparkConfig)

    // Do app-specific context initialization
    ifInitializer(app, _.initializeSpark(sc))

    sc
  }

  /**
    * Execute block if app mixes in the SparkInitializer trait
    */
  private def ifInitializer(app: SparkApp, body: (SparkInitializer) => Unit): Unit = app match {
    case i if i.isInstanceOf[SparkInitializer] => body(i.asInstanceOf[SparkInitializer])
    case _ =>
  }

}
