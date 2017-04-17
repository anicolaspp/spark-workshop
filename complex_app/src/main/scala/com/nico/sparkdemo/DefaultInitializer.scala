/**
  * Created by anicolaspp on 4/16/17.
  */
package com.nico.sparkdemo

import org.apache.spark.{SparkConf, SparkContext}

trait DefaultInitializer extends SparkInitializer {
  def configureSpark(sparkConfig: SparkConf) : Unit = {}

  def initializeSpark(sc: SparkContext) : Unit = {}
}
