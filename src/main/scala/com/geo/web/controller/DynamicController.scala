package com.geo.web.controller

import java.util
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet.{ServletConfig, ServletContext}

import com.geo.dynamic.spring.ServletContextHolder
import com.geo.dynamic.spring.context.DynamicWebApplicationContext
import com.geo.dynamic.{DynamicClassLoader, DynamicMappingLoader}
import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ResponseBody, PathVariable, RequestMapping}
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

trait DynamicManager {
  def remove(entry: String)
}

/**
  * Created by GeorgeZeng on 16/2/19.
  */
@Controller
class DynamicController @Autowired()(config: Config) extends ApplicationContextAware with LazyLogging with DynamicManager {
  private var appContext: ApplicationContext = _
  private val dynamicContextMapping = scala.collection.mutable.Map.empty[String, DynamicWebApplicationContext]
  private val servletMapping = scala.collection.mutable.Map.empty[String, DispatcherServlet]

  override def setApplicationContext(applicationContext: ApplicationContext): Unit = {
    appContext = applicationContext
  }

  private val groovyResourceClsLoader = new DynamicClassLoader(getClass.getClassLoader)
  private val entryMappingLoader = new DynamicMappingLoader

  @RequestMapping(path = Array("/refreshContexts"))
  @ResponseBody
  def refreshContexts(): String = {
    for ((clsName, ctx) <- dynamicContextMapping) {
      ctx.detectDestroyIfNecessary(true)
    }
    "Refresh all contexts success."
  }

  @RequestMapping(path = Array("/{entry}/**"))
  def execute(@PathVariable entry: String, request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val originalClsLoader = Thread.currentThread().getContextClassLoader
    ServletContextHolder.CONTEXT.set(request.getServletContext)
    val entryClsName = getEntryClsName(entry)
    try {
      if (entryClsName == null) {
        throw new ClassNotFoundException("Entry not found.")
      }
      val ctx = getDynamicContext(entryClsName)
      Thread.currentThread().setContextClassLoader(ctx.getClassLoader)
      ctx.reloadEntry()
      val servlet = getEntryServlet(entry, entryClsName, ctx)
      servlet.refresh()
      servlet.service(request, response)
    } catch {
      case ex: ClassNotFoundException => response.sendError(HttpStatus.NOT_FOUND.value())
    } finally {
      Thread.currentThread().setContextClassLoader(originalClsLoader)
    }
  }

  private def getEntryClsName(entry: String): String = {
    val entryMappingCls = groovyResourceClsLoader.loadClass(config.getConfig("groovy").getString("entryMapping"))
    entryMappingLoader.getMapping(entryMappingCls).get(entry)
  }

  private def getEntryServlet(entry: String, entryClsName: String, ctx: WebApplicationContext): DispatcherServlet = {
    def createServlet(): DispatcherServlet = {
      val servlet = new DispatcherServlet(ctx)
      servlet.init(new DynamicServletConfig(entry))
      servlet
    }

    var servletOpt: Option[DispatcherServlet] = servletMapping.get(entryClsName)
    if (servletOpt.isEmpty) {
      servletOpt = Some(createServlet())
      servletMapping += (entry -> servletOpt.get)
    }
    servletOpt.get
  }

  private def getDynamicContext(clsName: String): DynamicWebApplicationContext = {
    def createDynamicContext(): DynamicWebApplicationContext = {
      val ctx = new DynamicWebApplicationContext(clsName, this, appContext)
      ctx.register(ctx.getClassLoader.loadClass(config.getConfig("groovy").getString("basicConfig")))
      ctx
    }
    this.synchronized {
      var ctxOpt: Option[DynamicWebApplicationContext] = dynamicContextMapping.get(clsName)
      if (ctxOpt.isDefined) {
        ctxOpt = ctxOpt.get.detectDestroyIfNecessary()
      }
      if (ctxOpt.isEmpty) {
        ctxOpt = Some(createDynamicContext())
        dynamicContextMapping += (clsName -> ctxOpt.get)
      }
      ctxOpt.get
    }
  }

  override def remove(entry: String): Unit = {
    this.synchronized {
      dynamicContextMapping.remove(entry)
      servletMapping.remove(entry)
    }
  }
}

class DynamicServletConfig(name: String,
                           initParameters: util.Map[String, String] = new util.HashMap[String, String]) extends ServletConfig {
  override def getInitParameterNames: util.Enumeration[String] = {
    val vector = new util.Vector[String](initParameters.keySet())
    vector.elements()
  }

  override def getServletName: String = name

  override def getInitParameter(name: String): String = {
    initParameters.get(name)
  }

  override def getServletContext: ServletContext = ServletContextHolder.CONTEXT.get()
}
