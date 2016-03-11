package com.geo.dynamic.spring.aop

import groovy.lang.{GroovySystem, ProxyMetaClass}
import org.springframework.aop.framework.AdvisedSupport

/**
  * Created by GeorgeZeng on 16/3/4.
  */
class DynamicAopPorxyMetaClass(advised: AdvisedSupport, targetClass: Class[_])
  extends ProxyMetaClass(GroovySystem.getMetaClassRegistry(), targetClass, GroovySystem.getMetaClassRegistry().getMetaClass(targetClass)) {

  setInterceptor(new DynamicAopProxyInterceptor(advised))

//  override def getProperty(sender: Class[_], obj: AnyRef, name: String, useSuper: Boolean, fromInsideClass: Boolean): AnyRef = {
//    super.getProperty(sender, getTarget(), name, useSuper, fromInsideClass)
//  }
//
//  override def setProperty(sender: Class[_], obj: AnyRef, name: String, newValue: AnyRef, useSuper: Boolean, fromInsideClass: Boolean) {
//    super.setProperty(sender, getTarget(), name, newValue, useSuper, fromInsideClass)
//  }
//
//  private def getTarget(): GroovyObject = {
//    advised.getTargetSource().getTarget().asInstanceOf[GroovyObject]
//  }

}

