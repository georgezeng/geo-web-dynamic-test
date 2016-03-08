package com.geo.dao

import com.geo.entity.Role
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Repository
class RoleDao @Autowired()(sessionFactory: SessionFactory) extends BaseDao[Role](sessionFactory) {

  def findByName(name: String): Role = {
    findUniqueByKey(name, "name")
  }
}
