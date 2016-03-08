package com.geo.entity

import java.util
import java.util.Date
import javax.persistence.{Transient, CascadeType, Entity, OneToMany}

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import scala.collection.JavaConversions._

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@Entity(name = "User")
class User extends UserDetails with LongKeyEntity {

  trait SimpleView {}

  private var username: String = null
  private var password: String = null
  private var email: String = null
  private var birth: Date = null
  private var userRoles: util.Set[UserRole] = new util.LinkedHashSet[UserRole]
  private var enabled: Boolean = true

  @JsonView(Array(classOf[SimpleView]))
  def getBirth(): Date = birth

  def setBirth(birth: Date): Unit = {
    this.birth = birth
  }

  override def isEnabled: Boolean = enabled

  def setEnabled(enabled: Boolean): Unit = {
    this.enabled = enabled
  }

  override def getPassword: String = password

  def setPassword(password: String): Unit = {
    this.password = password
  }

  @Transient
  override def isAccountNonExpired: Boolean = true

  private val authoritiesSet = new util.LinkedHashSet[GrantedAuthority]

  @Transient
  override def getAuthorities: util.Collection[_ <: GrantedAuthority] = {
    if (authoritiesSet.isEmpty) {
      for (userRole <- userRoles) {
        authoritiesSet.addAll(userRole.getRole().getAuthorities())
      }
    }
    authoritiesSet
  }

  @Transient
  override def isCredentialsNonExpired: Boolean = true

  @Transient
  override def isAccountNonLocked: Boolean = true

  @JsonView(Array(classOf[SimpleView]))
  override def getUsername: String = username

  def setUsername(username: String): Unit = {
    this.username = username
  }

  @JsonView(Array(classOf[SimpleView]))
  def getEmail(): String = email

  def setEmail(email: String): Unit = {
    this.email = email
  }

  @OneToMany(mappedBy = "role", cascade = Array(CascadeType.ALL), orphanRemoval = true)
  def getUserRoles(): util.Set[UserRole] = userRoles

  private def setUserRoles(userRoles: util.Set[UserRole]): Unit = {
    this.userRoles = userRoles
  }

  def addRole(role: Role): Unit = {
    val userRole = new UserRole
    userRole.setRole(role)
    userRole.setUser(this)
    this.userRoles.add(userRole)
    role.addUserRole(userRole)
  }
}
