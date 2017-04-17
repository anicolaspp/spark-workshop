/**
  * Created by anicolaspp on 4/16/17.
  */
package com.nico.sparkdemo

import org.apache.spark.SparkContext

/**
  * Base class for Spark applications
  */
abstract class SparkApp extends DefaultInitializer {
  def name: String
  def execute(implicit sc: SparkContext) : Unit
}
