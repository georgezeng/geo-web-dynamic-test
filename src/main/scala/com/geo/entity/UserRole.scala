package com.geo.entity

import javax.persistence.{Id, ManyToOne, Entity}

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Entity(name = "UserRole")
class UserRole extends java.io.Serializable {
  private var role: Role = _
  private var user: User = _

  @Id
  @ManyToOne
  def getRole(): Role = role

  def setRole(role: Role): Unit = {
    this.role = role
  }

  @Id
  @ManyToOne
  def getUser(): User = user

  def setUser(user: User): Unit = {
    this.user = user
  }
}
