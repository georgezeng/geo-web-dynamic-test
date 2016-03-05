package com.geo.util

import java.lang.reflect.Method

import org.springframework.util.ReflectionUtils

/**
  * Created by GeorgeZeng on 16/2/21.
  */
object ReflectionUtil extends ReflectionUtils {
  def isMatchMethodTypes(method: Method, arguments: Array[AnyRef]): Boolean = {
    val types = method.getParameterTypes()
    if ((types == null || types.length == 0) && (arguments == null || arguments.length == 0)) {
      return true
    } else if (types != null && arguments != null && types.length == arguments.length) {
      var count = 0
      for (i <- 0 until types.length) {
        if (arguments(i) == null || arguments(i) != null && types(i).isAssignableFrom(arguments(i).getClass)) {
          count += 1
        }
      }
      if (count == types.length) {
        return true
      }
    }
    return false
  }

  def initiate[T](clazz: Class[T], args: Array[AnyRef], argTypes: Array[Class[_]]): T = {
    val c = clazz.getDeclaredConstructor(argTypes: _*)
    if (c == null) {
      throw new RuntimeException("Cant not find constructor for " + clazz)
    }
    c.setAccessible(true)
    c.newInstance(args: _*)
  }
}
