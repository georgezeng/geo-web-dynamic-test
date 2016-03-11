package com.geo.util

import java.lang.reflect.{ParameterizedType, Method}

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

  def setFieldValue(cls: Class[_], fieldName: String, instance: AnyRef, value: Any): Unit = {
    val field = cls.getDeclaredField(fieldName)
    if (field != null) {
      field.setAccessible(true)
      field.set(instance, value)
    } else {
      throw new IllegalArgumentException(s"Field '${fieldName}' not found")
    }
  }

  def getTypeParameterType[T](cls: Class[_], pos: Int = 0): Class[T] = {
    val tas = cls.getGenericSuperclass.asInstanceOf[ParameterizedType].getActualTypeArguments
    if (tas != null && tas.length > 0) {
      return tas(pos).asInstanceOf[Class[T]]
    } else if (cls != classOf[Any]) {
      getTypeParameterType(cls.getSuperclass, pos)
    }
    null
  }

  def findMethod(cls: Class[_], methodName: String, arguments: Array[AnyRef], contain: Boolean = false): Method = {
    if (cls != classOf[AnyRef]) {
      cls.getDeclaredMethods.foreach { m =>
        if (!contain && m.getName == methodName && isMatchMethodTypes(m, arguments)
          || contain && m.getName.contains(methodName) && isMatchMethodTypes(m, arguments)) {
          return m
        }
      }
      findMethod(cls.getSuperclass, methodName, arguments)
    } else null
  }

  def invokeMethod(cls: Class[_], methodName: String, obj: AnyRef, arguments: Array[AnyRef]): AnyRef = {
    val method = findMethod(cls, methodName, arguments)
    method.setAccessible(true)
    method.invoke(obj, arguments)
  }
}
