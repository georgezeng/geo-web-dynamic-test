package com.geo.dao

import com.geo.entity.User
import org.hibernate.SessionFactory
import org.hibernate.criterion.Restrictions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@Repository
@Transactional
class UserDAO {
  private var sessionFactory: SessionFactory = _

  @Autowired
  def setSessionFactory(sessionFactory: SessionFactory): Unit = {
    this.sessionFactory = sessionFactory
  }

  def findByUsername(username: String): User = {
    sessionFactory.getCurrentSession
      .createCriteria(classOf[User])
      .add(Restrictions.eq("username", username))
      .uniqueResult().asInstanceOf[User]
  }
}
