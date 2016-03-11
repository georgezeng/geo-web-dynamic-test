package com.geo.service

import com.geo.dao.RoleAuthorityDao
import com.geo.entity.RoleAuthority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Service
@Transactional
class RoleAuthorityService @Autowired()(dao: RoleAuthorityDao) extends BaseService[RoleAuthority](dao) {

  @Transactional(readOnly = true)
  def findByName(name: String): RoleAuthority = {
    dao.findByName(name)
  }
}
