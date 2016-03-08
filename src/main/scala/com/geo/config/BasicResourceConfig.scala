package com.geo.config

import java.util
import java.util.Properties
import javax.sql.DataSource

import com.geo.entity.{RoleAuthority, UserRole, Role, User}
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
    val clsSet = defineHibernateAnnotatedClasses()
    clsSet.add(classOf[User])
    clsSet.add(classOf[Role])
    clsSet.add(classOf[UserRole])
    clsSet.add(classOf[RoleAuthority])
    sessionFactory.setAnnotatedClasses(clsSet.toArray[Class[_]](Array[Class[_]]()): _*)
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

  protected def defineHibernateAnnotatedClasses(): util.Set[Class[_]]

  protected def defineHibernateProperties(props: Properties, config: Config): Unit = {

  }

  @Bean
  def transactionManager(sessionFactory: SessionFactory): PlatformTransactionManager = {
    val manager = new HibernateTransactionManager()
    manager.setSessionFactory(sessionFactory)
    manager
  }

}