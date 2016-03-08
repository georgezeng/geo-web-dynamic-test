package com.geo.spring

import org.springframework.aop.framework.ProxyConfig
import org.springframework.beans.factory.config.{ConfigurableListableBeanFactory, BeanFactoryPostProcessor}
import org.springframework.stereotype.Component
import scala.collection.JavaConversions._
/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Component
class ExposeAopProxyProcessor extends BeanFactoryPostProcessor {
  def postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory): Unit = {
    val map = beanFactory.getBeansOfType(classOf[ProxyConfig])
    if (map != null) {
      for (config <- map.values()) {
        config.setExposeProxy(true)
      }
    }
  }
}

