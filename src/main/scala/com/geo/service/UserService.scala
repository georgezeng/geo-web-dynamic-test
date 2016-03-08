package com.geo.service

import java.text.SimpleDateFormat

import com.geo.dao.{BaseDao, UserDao}
import com.geo.entity.{Role, User}
import com.geo.util.ReflectionUtil
import com.typesafe.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Service
@Transactional
class UserService @Autowired()(dao: UserDao,
                               roleService: RoleService,
                               config: Config) extends BaseService[User] {

  override def getDao(): BaseDao[User] = dao

  def createAdminIfNecessary(adminRole: Role): User = {
    val adminConfig = config.getConfig("admin")
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    var admin = get(adminConfig.getLong("id"))
    if (admin == null) {
      admin = new User
      admin.setUsername(adminConfig.getString("username"))
      admin.setPassword(adminConfig.getString("password"))
      admin.setEmail(adminConfig.getString("email"))
      admin.setBirth(sdf.parse(adminConfig.getString("birth")))
      admin.setCreatedBy(admin.getUsername)
      save(admin)
      val theAdminRole = roleService.load(adminRole.getId())
      admin.addRole(theAdminRole)
      save(admin)
    } else {
      var isUpdate = false
      if (admin.getUsername != adminConfig.getString("username")) {
        throw new IllegalArgumentException(s"Can not modify admin username '${admin.getUsername}' to '${adminConfig.getString("username")}'")
      }
      if (admin.getPassword != adminConfig.getString("password")) {
        isUpdate = true
        ReflectionUtil.setFieldValue(admin.getClass.getSuperclass, "password", admin, adminConfig.getString("password"))
      }
      if (admin.getEmail() != adminConfig.getString("email")) {
        isUpdate = true
        admin.setEmail(adminConfig.getString("email"))
      }
      if (sdf.format(admin.getBirth()) != adminConfig.getString("birth")) {
        isUpdate = true
        admin.setBirth(sdf.parse(adminConfig.getString("birth")))
      }
      if (isUpdate) {
        admin.setUpdatedBy(admin.getUsername)
        save(admin)
      }
    }
    admin
  }
}
