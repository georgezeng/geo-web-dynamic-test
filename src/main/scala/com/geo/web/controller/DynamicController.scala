package com.geo.web.controller

import java.util
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet.{ServletConfig, ServletContext}

import com.geo.dynamic.spring.ServletContextHolder
import com.geo.dynamic.spring.classloader.DynamicSpringClassLoader
import com.geo.dynamic.spring.context.DynamicWebApplicationContext
import com.geo.dynamic.{DynamicClassLoader, DynamicEntryLoader}
import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping}
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

trait DynamicManager {
  def remove(ctx: DynamicWebApplicationContext)
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
  private val entryMappingLoader = new DynamicEntryLoader()

  @RequestMapping(path = Array("/{entry}/**"))
  def execute(@PathVariable entry: String, request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val originalClsLoader = Thread.currentThread().getContextClassLoader
    ServletContextHolder.CONTEXT.set(request.getServletContext)
    val entryClsName = getEntryClsName(entry)
    try {
      if (entryClsName == null) {
        throw new ClassNotFoundException("Entry not found.")
      }
      var servlet: DispatcherServlet = null
      this.synchronized {
        val ctx = getDynamicContext(entryClsName)
        Thread.currentThread().setContextClassLoader(ctx.getClassLoader)
        ctx.getClassLoader.loadClass(entryClsName)
        servlet = getEntryServlet(entryClsName, ctx)
      }
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
    entryMappingLoader.entryMapping(entryMappingCls).get(entry)
  }

  private def getEntryServlet(entry: String, ctx: WebApplicationContext): DispatcherServlet = {
    var servletOpt: Option[DispatcherServlet] = None
    this.synchronized {
      servletOpt = servletMapping.get(entry)
      if (servletOpt.isEmpty) {
        servletOpt = Some(createServlet(entry, ctx))
        servletMapping += (entry -> servletOpt.get)
      }
    }
    servletOpt.get
  }

  private def createServlet(entry: String, ctx: WebApplicationContext): DispatcherServlet = {
    val servlet = new DispatcherServlet(ctx)
    servlet.init(new DynamicServletConfig(entry))
    servlet
  }

  private def getDynamicContext(clsName: String): DynamicWebApplicationContext = {
    var ctxOpt: Option[DynamicWebApplicationContext] = None
    this.synchronized {
      ctxOpt = dynamicContextMapping.get(clsName)
      if(ctxOpt.isDefined) {
        ctxOpt = ctxOpt.get.getClassLoader.asInstanceOf[DynamicSpringClassLoader].detectDestroyIfNecessary()
      }
      if (ctxOpt.isEmpty) {
        ctxOpt = Some(createDynamicContext())
        dynamicContextMapping += (clsName -> ctxOpt.get)
      }
    }
    ctxOpt.get
  }

  private def createDynamicContext(): DynamicWebApplicationContext = {
    val ctx = new DynamicWebApplicationContext(this, appContext)
    ctx.register(ctx.getClassLoader.loadClass(config.getConfig("groovy").getString("basicConfig")))
    ctx
  }

  override def remove(ctx: DynamicWebApplicationContext): Unit = {
    def getEntry(): String = {
      for ((entry, context) <- dynamicContextMapping) {
        if (context == ctx) {
          return entry
        }
      }
      null
    }
    this.synchronized {
      val entry = getEntry()
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
