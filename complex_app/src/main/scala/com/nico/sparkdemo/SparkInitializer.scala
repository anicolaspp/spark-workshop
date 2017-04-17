/**
  * Created by anicolaspp on 4/16/17.
  */
package com.nico.sparkdemo

import org.apache.spark.{SparkConf, SparkContext}

trait SparkInitializer {
  /**
    * Add any needed Spark configuration prior to initializing SparkContext.
    * Once the SparkContext is created, its configuration is immutable.
    */
  def configureSpark(sparkConfig: SparkConf) : Unit

  def initializeSpark(sc: SparkContext) : Unit
}
