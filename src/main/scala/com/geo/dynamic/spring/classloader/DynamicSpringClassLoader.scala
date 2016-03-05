package com.geo.dynamic.spring.classloader

import com.geo.dynamic.DynamicClassLoader
import com.geo.dynamic.spring.DynamicConstant
import com.geo.dynamic.spring.context.DynamicWebApplicationContext
import com.geo.enums.Environment
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.core.annotation.AnnotationUtils
import scala.collection.JavaConversions._

class DynamicSpringClassLoader(parent: ClassLoader) extends DynamicClassLoader(parent) with LazyLogging {
  var context: DynamicWebApplicationContext = _

  override protected def setClassCacheEntry(cls: Class[_]): Unit = {
    if (AnnotationUtils.findAnnotationDeclaringClassForTypes(DynamicConstant.springConfigTypes, cls) != null) {
      logger.debug("Register class [" + cls.getName + "] to spring context")
      context.register(cls)
    }
    logger.trace("set groovy class [" + cls.getName + "] to cache")
    super.setClassCacheEntry(cls)
  }

  def detectDestroyIfNecessary(force: Boolean = false): Option[DynamicWebApplicationContext] = {
    if (Environment.isDevOrTest() || force) {
      classCache.synchronized {
        if (!classCache.isEmpty) {
          for ((clsName, cls) <- classCache) {
            if (isRecompilable(cls)) {
              context.destroy()
              return None
            }
          }
        }
      }
    }
    return Some(context)
  }

}

