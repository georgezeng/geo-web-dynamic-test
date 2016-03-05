package com.geo.config

import javax.sql.DataSource

import com.geo.enums.Environment
import com.typesafe.config.{ConfigFactory, Config}
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * Created by GeorgeZeng on 16/2/19.
  */
trait ResourceConfigModel {
  @Bean
  def appConfig(): Config = {
    val configDefault = ConfigFactory.load("application.conf")
    val configEnv = ConfigFactory.load(Environment.current + "/application.conf")
    val config = configEnv.withFallback(configDefault)
    Environment.GroovyRootPaths = config.getConfig("groovy").getStringList("baseResourceRootDirs")
    config
  }

  @Bean
  def dataSource(config: Config): DataSource = {
    val ds = new BasicDataSource
    val dbConfig = config.getConfig("database")
    ds.setUrl(dbConfig.getString("url"))
    ds.setUsername(dbConfig.getString("username"))
    ds.setPassword(dbConfig.getString("password"))
    ds.setDriverClassName(dbConfig.getString("driver"))
    ds
  }

}

@Configuration
class ResourceConfig extends ResourceConfigModel {

}
