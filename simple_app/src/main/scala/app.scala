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
