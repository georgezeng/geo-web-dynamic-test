package com.geo.dynamic.spring.aop

import java.lang.reflect.Method

import com.geo.util.ReflectionUtil
import com.typesafe.scalalogging.slf4j.LazyLogging
import groovy.lang.{GroovyObject, Interceptor}
import org.springframework.aop.framework.{AdvisedSupport, ReflectiveMethodInvocation}

/**
  * Created by GeorgeZeng on 16/2/21.
  */
class DynamicAopProxyInterceptor(advised: AdvisedSupport) extends Interceptor with LazyLogging {
  override def doInvoke(): Boolean = false

  def getTarget(): GroovyObject = {
    advised.getTargetSource().getTarget().asInstanceOf[GroovyObject]
  }

  override def beforeInvoke(proxy: AnyRef, methodName: String, arguments: Array[AnyRef]): AnyRef = {
    val target = getTarget()
    try {
      var args = arguments
      val clazz = advised.getTargetClass()
      var retVal: AnyRef = null
      var method = ReflectionUtil.findMethod(clazz, methodName, arguments)
      if (method == null) {
        args = Array(methodName, arguments)
        method = ReflectionUtil.findMethod(clazz, "invokeMethod", args, true)
      }
      var invokeTargetMethod = false
      if (method != null) {
        val chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, clazz)
        if (chain != null && !chain.isEmpty) {
          val invocation = new DynamicMethodInvocation(proxy, target, method, args, clazz, chain)
          // Proceed to the joinpoint through the interceptor chain.
          retVal = invocation.proceed()
        } else {
          invokeTargetMethod = true
        }
      } else {
        invokeTargetMethod = true
      }
      if (invokeTargetMethod) {
        retVal = target.invokeMethod(methodName, arguments)
      }
      return retVal
    } finally {
      if (target != null && !advised.getTargetSource().isStatic()) {
        // Must have come from TargetSource.
        try {
          advised.getTargetSource().releaseTarget(target)
        } catch {
          case ex: Exception => logger.error(ex.getMessage, ex)
        }
      }
    }
  }

  override def afterInvoke(proxy: AnyRef, methodName: String, arguments: Array[AnyRef], result: AnyRef): AnyRef = {
    if (getTarget().equals(result)) {
      proxy
    } else result
  }


}

private class DynamicMethodInvocation(proxy: AnyRef, target: AnyRef, method: Method, arguments: Array[AnyRef],
                                      targetClass: Class[_], interceptorsAndDynamicMethodMatchers: java.util.List[AnyRef])
  extends ReflectiveMethodInvocation(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers)
