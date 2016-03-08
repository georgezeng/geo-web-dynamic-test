package com.geo.config

import java.util
import javax.sql.DataSource

import com.geo.enums.Environment
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, EnableAspectJAutoProxy}

/**
  * Created by GeorgeZeng on 16/2/19.
  */
@Configuration
@ComponentScan(Array("com.geo.spring", "com.geo.dao", "com.geo.service"))
@EnableAspectJAutoProxy(proxyTargetClass = true)
class ApplicationConfig extends BasicResourceConfig {
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

  override protected def defineHibernateAnnotatedClasses(): util.Set[Class[_]] = {
    new util.LinkedHashSet[Class[_]]
  }

}
