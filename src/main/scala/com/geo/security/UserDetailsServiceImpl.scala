package com.geo.security

import com.geo.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.{AuthenticationServiceException, BadCredentialsException}
import org.springframework.security.core.userdetails.{UserDetails, UserDetailsService}
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Component
@Transactional
class UserDetailsServiceImpl @Autowired()(userDao: UserDao) extends UserDetailsService {
  override def loadUserByUsername(username: String): UserDetails = {
    val user = userDao.findByUsername(username)
    if(user == null) {
      throw new BadCredentialsException("Username or password is incorrect")
    }
    if(!user.isEnabled) {
      throw new AuthenticationServiceException("User is invalid")
    }
    // load authorities
    user.getAuthorities
    user
  }
}
