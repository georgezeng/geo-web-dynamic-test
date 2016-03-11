package com.geo.security

import javax.servlet.{FilterChain, ServletRequest, ServletResponse}

import com.geo.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@Component
class UserSessionFilter extends GenericFilterBean {

  override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    UserSession.CONTEXT.set(SecurityContextHolder.getContext.getAuthentication.getPrincipal.asInstanceOf[User])
    try {
      chain.doFilter(request, response)
    } finally {
      UserSession.CONTEXT.remove()
    }
  }
}
