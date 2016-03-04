package com.geo.dynamic.spring.aop

import groovy.lang.GroovyObject
import org.springframework.aop.framework.{AopProxy, AdvisedSupport, AopProxyFactory}

/**
  * Created by GeorgeZeng on 16/2/21.
  */
class DynamicAopProxyFactory(delegate: AopProxyFactory) extends AopProxyFactory {
  override def createAopProxy(config: AdvisedSupport): AopProxy = {
    val targetClass = config.getTargetClass()
    if (classOf[GroovyObject].isAssignableFrom(targetClass)) {
      new DynamicAopProxy(config)
    } else {
      delegate.createAopProxy(config)
    }
  }
}
