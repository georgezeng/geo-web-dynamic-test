package com.geo.config

import java.lang.reflect.Method

import com.geo.dynamic.spring.security.DynamicSecurityMetadataSource
import com.geo.enums.Environment
import com.geo.filter.StaticResourceFilter
import com.geo.security.UserSessionFilter
import com.typesafe.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@EnableWebSecurity
@ComponentScan(Array("com.geo.security"))
class SecurityConfig extends WebSecurityConfigurerAdapter {

  private var config: Config = _

  @Autowired
  def setAppConfig(config: Config): Unit = {
    this.config = config
  }

  private var userDetailsServiceImpl: UserDetailsService = _

  @Autowired
  def setUserDetailsServiceImpl(userDetailsServiceImpl: UserDetailsService): Unit = {
    this.userDetailsServiceImpl = userDetailsServiceImpl
  }

  private var userSessionFilter: UserSessionFilter = _

  @Autowired
  def setUserSessionFilter(userSessionFilter: UserSessionFilter): Unit = {
    this.userSessionFilter = userSessionFilter
  }

  private var staticResourceFilter: StaticResourceFilter = _

  @Autowired
  def setStaticResourceFilter(staticResourceFilter: StaticResourceFilter): Unit = {
    this.staticResourceFilter = staticResourceFilter
  }

  override protected def configure(http: HttpSecurity): Unit = {
    def getExpressionHandlerMethod(methods: Array[Method]): Method = {
      for (method <- methods) {
        if (method.getName == "getExpressionHandler") {
          return method
        }
      }
      return null
    }
    val registry = http
      .authorizeRequests()
      .anyRequest().authenticated()
    val configurer = http.getConfigurer[ExpressionUrlAuthorizationConfigurer[HttpSecurity]](classOf[ExpressionUrlAuthorizationConfigurer[HttpSecurity]])
    val methods = classOf[ExpressionUrlAuthorizationConfigurer[HttpSecurity]].getDeclaredMethods()
    val method = getExpressionHandlerMethod(methods)
    method.setAccessible(true)
    val handler = method.invoke(configurer, http).asInstanceOf[SecurityExpressionHandler[FilterInvocation]]
    registry.withObjectPostProcessor(new ObjectPostProcessor[FilterSecurityInterceptor]() {
      override def postProcess[O <: FilterSecurityInterceptor](fsi: O): O = {
        fsi.setSecurityMetadataSource(new DynamicSecurityMetadataSource(config.getConfig("groovy").getString("securityMapping"),
          handler.getExpressionParser))
        fsi
      }
    })

    val authenticationProvider = new DaoAuthenticationProvider
    authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder)
    authenticationProvider.setUserDetailsService(userDetailsServiceImpl)
    http
      .formLogin()
      .and()
      .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name(), false))
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterAfter(staticResourceFilter, classOf[FilterSecurityInterceptor])
      .addFilterAfter(userSessionFilter, classOf[StaticResourceFilter])

    if (Environment.isTest()) {
      http.csrf().disable()
    }
  }
}
