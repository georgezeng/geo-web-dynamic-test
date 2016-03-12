package com.geo.enums

/**
  * Created by GeorgeZeng on 16/2/19.
  */
object Environment extends Enumeration {
  val Dev = Value("dev")
  val Test = Value("test")
  val Uat = Value("uat")
  val Staging = Value("staging")
  val Prod = Value("prod")

  var WebRootPath: String = _
  var GroovyRootPaths: java.util.List[String] = _

  def current: Value = {
    var env = System.getenv("ENV_SYSTEM")
    if (env == null) {
      env = System.getProperty("ENV_SYSTEM")
    }
    if(env == null) {
      env = "dev"
    }
    env = env.toLowerCase
    env match {
      case "dev" => Dev
      case "test" => Test
      case "uat" => Uat
      case "staging" => Staging
      case "prod" => Prod
      case other => throw new RuntimeException("Not support environment: " + other)
    }
  }

  def isDev(): Boolean = {
    current == Dev
  }

  def isTest(): Boolean = {
    current == Test
  }
}

