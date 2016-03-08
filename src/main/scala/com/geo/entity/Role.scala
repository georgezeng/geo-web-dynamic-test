package com.geo.entity

import java.util
import javax.persistence.{Entity, CascadeType, OneToMany}

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Entity(name = "Role")
class Role extends LongKeyEntity {
  private var name: String = null

  private var authorities: util.Set[RoleAuthority] = new util.LinkedHashSet[RoleAuthority]()

  private var userRoles: util.Set[UserRole] = new util.LinkedHashSet[UserRole]()

  def getName(): String = name

  def setName(name: String): Unit = {
    this.name = name
  }

  @OneToMany(mappedBy = "role", cascade = Array(CascadeType.ALL), orphanRemoval = true)
  def getAuthorities(): util.Set[RoleAuthority] = authorities

  private def setAuthorities(authorities: util.Set[RoleAuthority]): Unit = {
    this.authorities = authorities
  }

  def addAuthority(authority: RoleAuthority): Unit = {
    authorities.add(authority)
  }

  @OneToMany(mappedBy = "role", cascade = Array(CascadeType.ALL), orphanRemoval = true)
  def getUserRoles(): util.Set[UserRole] = userRoles

  private def setUserRoles(userRoles: util.Set[UserRole]): Unit = {
    this.userRoles = userRoles
  }

  def addUserRole(userRole: UserRole): Unit = {
    this.userRoles.add(userRole)
  }
}
