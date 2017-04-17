/**
  * Created by anicolaspp on 4/16/17.
  */
package com.nico.sparkdemo

import org.apache.spark.SparkContext

class MyApp extends SparkApp {
  override def name: String = "My App"

  override def execute(implicit sc: SparkContext): Unit = {

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
