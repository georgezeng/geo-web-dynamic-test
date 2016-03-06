package com.geo.entity

import java.util.Date
import javax.persistence.Entity

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@Entity
class User extends LongKeyEntity {
  private var username: String = _
  private var email: String = _
  private var password: String = _
  private var birth: Date = _

  def setUsername(username: String): Unit = {
    this.username = username
  }

  def setEmail(email: String): Unit = {
    this.email = email
  }

  def setPassword(password: String): Unit = {
    this.password = password
  }

  def setBirth(birth: Date): Unit = {
    this.birth = birth
  }

  def getUsername(): String = {
    username
  }
  def getEmail(): String = {
    email
  }
  def getPassword(): String = {
    password
  }
  def getBirth(): Date = {
    birth
  }

}
