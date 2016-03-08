package com.geo.entity

import javax.persistence.{Transient, ManyToOne, Entity}

import org.springframework.security.core.GrantedAuthority

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Entity(name = "RoleAuthority")
class RoleAuthority extends LongKeyEntity with GrantedAuthority {

  private var name: String = null

  private var role: Role = _

  @Transient
  override def getAuthority: String = name

  def getName(): String = name

  def setName(name: String): Unit = {
    this.name = name
  }

  @ManyToOne
  def getRole(): Role = role

  def setRole(role: Role): Unit = {
    this.role = role
  }

}

