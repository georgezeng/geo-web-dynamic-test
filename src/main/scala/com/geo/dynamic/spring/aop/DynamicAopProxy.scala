package com.geo.dynamic.spring.aop

import com.geo.util.ReflectionUtil
import com.typesafe.scalalogging.slf4j.LazyLogging
import groovy.lang.GroovyObject
import org.springframework.aop.framework.{AdvisedSupport, AopConfigException, AopProxy}


/**
  * Created by GeorgeZeng on 16/2/21.
  */
class DynamicAopProxy(advised: AdvisedSupport) extends AopProxy with Serializable with LazyLogging {
  assertNotEmptyAdvised()

  private def assertNotEmptyAdvised(): Unit = {
    if (advised.getAdvisors().length == 0 && advised.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE) {
      throw new AopConfigException("No advisors and no TargetSource specified");
    }
  }

  private var constructorArgs: Array[AnyRef] = _
  private var constructorArgTypes: Array[Class[_]] = _

  def setConstructorArguments(constructorArgs: Array[AnyRef], constructorArgTypes: Array[Class[_]]): Unit = {
    if (constructorArgs == null || constructorArgTypes == null) {
      throw new IllegalArgumentException("Both 'constructorArgs' and 'constructorArgTypes' need to be specified")
    }
    if (constructorArgs.length != constructorArgTypes.length) {
      throw new IllegalArgumentException("Number of 'constructorArgs' (" + constructorArgs.length
        + ") must match number of 'constructorArgTypes' (" + constructorArgTypes.length + ")")
    }
    this.constructorArgs = constructorArgs
    this.constructorArgTypes = constructorArgTypes
  }

  override def getProxy: AnyRef = {
    getProxy(advised.getTargetClass.getClassLoader)
  }

  override def getProxy(classLoader: ClassLoader): AnyRef = {
    logger.debug("Creating groovy proxy: target source is " + this.advised.getTargetSource())
    try {
      val rootClass = this.advised.getTargetClass.asInstanceOf[Class[GroovyObject]]
      val metaClass = new DynamicAopPorxyMetaClass(advised, rootClass)
      val proxy = ReflectionUtil.initiate[GroovyObject](rootClass, constructorArgs, constructorArgTypes)
      proxy.setMetaClass(metaClass)
      proxy
    } catch {
      case ex: Exception => throw new AopConfigException("Unexpected AOP exception: " + ex.getMessage, ex)
    }
  }
}
