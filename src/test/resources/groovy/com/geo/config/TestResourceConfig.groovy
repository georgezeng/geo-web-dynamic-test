package com.geo.config

import com.geo.entity.User
import com.typesafe.config.Config
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Configuration
class TestResourceConfig extends BasicResourceConfig {

    @Bean
    def LocalSessionFactoryBean sessionFactory(DataSource dataSource, Config config) {
        createSessionFactory(dataSource, config)
    }

    @Bean
    def PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        createTransactionManager(sessionFactory)
    }

    @Override
    def Class<?>[] defineHibernateAnnotatedClasses() {
        return [User.class]
    }

}
