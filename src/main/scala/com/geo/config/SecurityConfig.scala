package com.geo.config

import java.lang.reflect.Method

import com.geo.dynamic.spring.security.DynamicSecurityMetadataSource
import com.typesafe.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private var config: Config = _

  @Autowired
  def configureGlobal(auth: AuthenticationManagerBuilder): Unit = {
    auth
      .inMemoryAuthentication()
      .withUser("user").password("111111").roles("USER")
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

    http
      .formLogin()
      .and()
      .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name(), false))
  }
}
