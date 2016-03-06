package com.geo.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

/**
  * Created by GeorgeZeng on 16/2/19.
  */
@SpringBootApplication
@ComponentScan(basePackages = Array("com.geo"))
class TestConfig {
}


object TestApp extends App {
  System.setProperty("ENV_SYSTEM", "test")
  SpringApplication.run(classOf[TestConfig])
  while (true) {}
}
