package com.geo.dynamic.spring.security

import java.util

import com.geo.dynamic.{DynamicClassLoader, DynamicMappingLoader}
import org.springframework.expression.ExpressionParser
import org.springframework.security.access.{ConfigAttribute, SecurityConfig}
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.util.AntPathMatcher

import scala.collection.JavaConversions._

/**
  * Created by GeorgeZeng on 16/3/6.
  */
class DynamicSecurityMetadataSource(mappingClsName: String, parser: ExpressionParser)
  extends FilterInvocationSecurityMetadataSource {
  private val mappingLoader = new DynamicMappingLoader
  private val clsLoader = new DynamicClassLoader(getClass.getClassLoader)
  private val matcher = new AntPathMatcher()

  override def getAttributes(obj: scala.Any): util.Collection[ConfigAttribute] = {
    val fi = obj.asInstanceOf[FilterInvocation]
    val uri = fi.getRequestUrl
    val mapping = mappingLoader.getMapping(clsLoader.loadClass(mappingClsName))
    for ((entry, expression) <- mapping) {
      if (matcher.`match`(entry, uri)) {
        val cls = Class.forName("org.springframework.security.web.access.expression.WebExpressionConfigAttribute")
        val constructor = cls.getDeclaredConstructors()(0)
        constructor.setAccessible(true)
        val webExpressionConfigAttribute = constructor.newInstance(parser.parseExpression(expression)).asInstanceOf[ConfigAttribute]
        val result = new util.ArrayList[ConfigAttribute]()
        result.add(webExpressionConfigAttribute)
        return result
      }
    }

    null
  }

  override def getAllConfigAttributes: util.Collection[ConfigAttribute] = {
    val set = new util.LinkedHashSet[ConfigAttribute]()
    val mapping = mappingLoader.getMapping(clsLoader.loadClass(mappingClsName))
    for ((entry, attributes) <- mapping) {
      set.addAll(SecurityConfig.createList(attributes.split(",\\s*"): _*))
    }
    set
  }

  override def supports(clazz: Class[_]): Boolean = {
    classOf[FilterInvocation].isAssignableFrom(clazz)
  }
}
