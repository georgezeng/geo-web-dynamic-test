package com.geo.test

import org.hibernate.SessionFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, ComponentScan}

/**
  * Created by GeorgeZeng on 16/2/19.
  */
@SpringBootApplication
@ComponentScan(Array("com.geo.config", "com.geo.test", "com.geo.dao", "com.geo.service"))
class TestConfig {
  @Bean
  def dbSetup(sessionFactory: SessionFactory): DBSetup = {
    new DBSetup(sessionFactory)
  }
}


object TestApp extends App {
  org.h2.tools.Server.main("-web")
  SpringApplication.run(classOf[TestConfig])
  while (true) {}
}
