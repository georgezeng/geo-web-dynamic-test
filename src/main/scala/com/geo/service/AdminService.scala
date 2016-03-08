package com.geo.service

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Service
@Transactional
class AdminService @Autowired()(userService: UserService,
                                roleService: RoleService) extends InitializingBean {

  override def afterPropertiesSet(): Unit = {
    val adminRole = roleService.createAdminRoleIfNecessary
    userService.createAdminIfNecessary(adminRole)
  }
}
