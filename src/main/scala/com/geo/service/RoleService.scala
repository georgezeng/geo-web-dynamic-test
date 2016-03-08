package com.geo.service

import com.geo.dao.{BaseDao, RoleDao}
import com.geo.entity.{RoleAuthority, Role}
import com.typesafe.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Service
@Transactional
class RoleService @Autowired()(config: Config, dao: RoleDao, authorityService: RoleAuthorityService) extends BaseService[Role] {
  override def getDao(): BaseDao[Role] = dao

  @Transactional(readOnly = true)
  def findByName(name: String): Role = {
    dao.findByName(name)
  }

  def createAdminRoleIfNecessary(): Role = {
    val adminConfig = config.getConfig("admin")
    val name = adminConfig.getString("title")
    var adminRole = findByName(name)
    val updatedBy = adminConfig.getString("username")
    if(adminRole == null) {
      var authority = authorityService.findByName(adminConfig.getString("authority"))
      if(authority == null) {
        authority = new RoleAuthority
        authority.setName(adminConfig.getString("authority"))
      }
      adminRole = new Role
      adminRole.setName(name)
      if(authority.getRole() == null) {
        authority.setRole(adminRole)
        authority.setCreatedBy(updatedBy)
        authority.setUpdatedBy(updatedBy)
      }
      adminRole.addAuthority(authority)
      adminRole.setCreatedBy(updatedBy)
      adminRole.setUpdatedBy(updatedBy)
      save(adminRole)
    }
    adminRole
  }
}


