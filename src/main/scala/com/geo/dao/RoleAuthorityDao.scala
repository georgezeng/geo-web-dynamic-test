package com.geo.dao

import com.geo.entity.RoleAuthority
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Repository
class RoleAuthorityDao @Autowired()(sessionFactory: SessionFactory) extends BaseDao[RoleAuthority](sessionFactory) {
  def findByName(name: String): RoleAuthority = {
    findUniqueByKey(name, "name")
  }
}
