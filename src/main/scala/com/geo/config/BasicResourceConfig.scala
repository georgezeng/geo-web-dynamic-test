package com.geo.config

import java.util.Properties
import javax.sql.DataSource

import com.typesafe.config.Config
import org.hibernate.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.orm.hibernate5.{HibernateTransactionManager, LocalSessionFactoryBean}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@EnableTransactionManagement(proxyTargetClass = true)
abstract class BasicResourceConfig {
  @Bean
  def sessionFactory(ds: DataSource, config: Config): LocalSessionFactoryBean = {
    val sessionFactory = new LocalSessionFactoryBean()
    sessionFactory.setAnnotatedClasses(defineHibernateAnnotatedClasses(): _*)
    sessionFactory.setDataSource(ds)
    val hibernateConfig = config.getConfig("hibernate")
    val props = new Properties
    props.setProperty("hibernate.dialect", hibernateConfig.getString("dialect"))
    props.setProperty("hibernate.show_sql", hibernateConfig.getString("showSql"))
    props.setProperty("hibernate.format_sql", hibernateConfig.getString("formatSql"))
    defineHibernateProperties(props, hibernateConfig)
    sessionFactory.setHibernateProperties(props)
    sessionFactory
  }

  protected def defineHibernateAnnotatedClasses(): Array[Class[_]]

  protected def defineHibernateProperties(props: Properties, config: Config): Unit = {

  }

  @Bean
  def transactionManager(sessionFactory: SessionFactory): PlatformTransactionManager = {
    val manager = new HibernateTransactionManager()
    manager.setSessionFactory(sessionFactory)
    manager
  }

}