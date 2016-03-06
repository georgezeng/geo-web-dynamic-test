package com.geo.config

import javax.sql.DataSource

import com.geo.entity.User
import com.geo.enums.Environment
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.dbcp2.BasicDataSource
import org.hibernate.SessionFactory
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager

/**
  * Created by GeorgeZeng on 16/2/19.
  */
@Configuration
@ComponentScan(Array("com.geo.dao"))
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

  @Bean
  def sessionFactory(dataSource: DataSource, config: Config): LocalSessionFactoryBean = {
    createSessionFactory(dataSource, config)
  }

  @Bean
  def transactionManager(sessionFactory: SessionFactory): PlatformTransactionManager = {
    createTransactionManager(sessionFactory)
  }

  override protected def defineHibernateAnnotatedClasses(): Array[Class[_]] = {
    Array[Class[_]](classOf[User])
  }

}
