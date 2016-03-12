package com.geo.dynamic

import java.io.File
import java.net.URL

import com.geo.enums.Environment
import com.typesafe.scalalogging.slf4j.LazyLogging
import groovy.lang.GroovyClassLoader

import scala.collection.JavaConversions._

class DynamicClassLoader(parent: ClassLoader) extends GroovyClassLoader(parent) with LazyLogging {
  protected override def isRecompilable(cls: Class[_]): Boolean = {
    if (!Environment.isDev()) {
      return super.isRecompilable(cls)
    }
    if (cls == null) {
      return true
    }
    val resource = getResource(cls.getName.replaceAll("\\.", "/") + ".groovy")
    return resource != null && isSourceNewer(resource, cls)
  }

  protected override def loadClass(name: String, resolve: Boolean): Class[_] = {
    logger.trace("Loading class [" + name + "]")
    loadClass(name, true, !Environment.isDev(), resolve)
  }

  override def getResource(name: String): URL = {
    val url = super.getResource(name)
    if (url == null) {
      Environment.GroovyRootPaths.foreach { rootPath: String =>
        val path = rootPath + "/" + name
        logger.trace("Loading groovy resource: " + path)
        val file = new File(path)
        if (file.exists()) {
          return file.toURI.toURL
        }
      }
    }
    url
  }

}