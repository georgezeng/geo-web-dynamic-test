package com.geo.dao

import com.geo.entity.User
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Created by GeorgeZeng on 16/3/6.
 */
@Repository
@Transactional
class UserDao extends com.geo.dao.EntityBasicOPDao<User> {
    @Autowired
    private SessionFactory sessionFactory

    SessionFactory getSessionFactory() {
        sessionFactory
    }
}