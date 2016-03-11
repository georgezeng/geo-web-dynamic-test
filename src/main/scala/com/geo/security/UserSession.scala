package com.geo.security

import com.geo.entity.User

/**
  * Created by GeorgeZeng on 16/3/6.
  */
object UserSession {
  val CONTEXT = new ThreadLocal[User]
}
