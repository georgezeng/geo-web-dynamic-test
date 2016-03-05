package com.geo.dao

import com.geo.entity.TestUser
import org.hibernate.SessionFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Created by GeorgeZeng on 16/3/3.
 */
@Repository
@Transactional
class TestUserDAO implements InitializingBean {
    @Autowired
    SessionFactory sessionFactory

    def list() {
        sessionFactory.getCurrentSession().createCriteria(TestUser.class).list()
    }

    def save(user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user)
    }

    void afterPropertiesSet() throws Exception {
        def session = sessionFactory.openStatelessSession()
        try {
            session.createSQLQuery("""
            CREATE TABLE IF NOT EXISTS `${TestUser.class.simpleName}`  (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `name` varchar(255) DEFAULT NULL,
              PRIMARY KEY (`id`),
            ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
        """).executeUpdate()
        } finally {
            session.close()
        }
    }
}