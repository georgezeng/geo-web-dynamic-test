package com.geo.security

import javax.servlet.http.HttpServletRequest
import javax.servlet.{FilterChain, ServletRequest, ServletResponse}

import com.geo.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.GenericFilterBean

/**
  * Created by GeorgeZeng on 16/3/6.
  */
//@Component
class UserSessionFilter extends GenericFilterBean {
  private var userDao: UserDao = _

  @Autowired
  def setUserDao(dao: UserDao): Unit = {
    this.userDao = dao
  }

  override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    val httpReq = request.asInstanceOf[HttpServletRequest]
    val session = httpReq.getSession()
    if (session.getAttribute(UserSessionConstant.USER_KEY) == null) {
      val ctx = SecurityContextHolder.getContext
      val user = userDao.findByUsername(ctx.getAuthentication.getPrincipal.asInstanceOf[User].getUsername())
      session.setAttribute(UserSessionConstant.USER_KEY, user)
    }
    chain.doFilter(request, response)
  }
}
