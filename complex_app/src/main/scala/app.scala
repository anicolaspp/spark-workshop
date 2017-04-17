package com.nico.sparkdemo

object app extends SparkAppRunner {
  def main(args: Array[String]) {

    val app = new MyApp

    executeWithSpark(app)
  }
}





