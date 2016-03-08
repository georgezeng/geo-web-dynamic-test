package com.geo.test

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
  * Created by GeorgeZeng on 16/3/7.
  */
object BCryptPasswordTest extends App {
  println(new BCryptPasswordEncoder().encode("admin"))
}
