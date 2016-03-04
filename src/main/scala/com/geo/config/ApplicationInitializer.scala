package com.geo.config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer


/**
  * Created by GeorgeZeng on 16/2/19.
  */

class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  override def getRootConfigClasses: Array[Class[_]] = {
    Array[Class[_]](classOf[ResourceConfig])
  }

  override def getServletConfigClasses: Array[Class[_]] = Array[Class[_]](classOf[WebConfig])

  override def getServletMappings: Array[String] = Array[String]("/")

//  override def getServletFilters(): Array[Filter] = {
//    Array[Filter](new StaticResourceFilter)
//  }

//  override protected def createRootApplicationContext(): WebApplicationContext = {
//    new DynamicRefreshableWebApplicationContext(super.createRootApplicationContext)
//  }
}
