/**
  * Created by anicolaspp on 4/16/17.
  */
package com.nico.sparkdemo

trait Logging {

  @transient lazy val logger = org.slf4j.LoggerFactory.getLogger(getClass)
}
