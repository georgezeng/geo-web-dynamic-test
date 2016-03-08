package com.geo.dao

import com.geo.entity.User
import com.typesafe.config.Config
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@Repository
class UserDao @Autowired()(sessionFactory: SessionFactory, config: Config) extends BaseDao[User](sessionFactory) {


  def findByUsername(username: String): User = {
    findUniqueByKey(username, "username")
  }


}
