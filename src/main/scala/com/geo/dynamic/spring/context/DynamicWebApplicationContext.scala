package com.geo.dynamic.spring.context

import com.geo.dynamic.spring.classloader.DynamicSpringClassLoader
import com.geo.web.controller.DynamicManager
import org.springframework.context.ApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext

/**
  * Created by GeorgeZeng on 16/2/20.
  */
class DynamicWebApplicationContext(entry: String, dynamicManager: DynamicManager, parent: ApplicationContext) extends AnnotationConfigWebApplicationContext {
  setParent(parent)
  setClassLoader(new DynamicSpringClassLoader(getClass.getClassLoader))

  override def setClassLoader(classLoader: ClassLoader): Unit = {
    classLoader.asInstanceOf[DynamicSpringClassLoader].context = this
    super.setClassLoader(classLoader)
  }

  def reloadEntry(): Unit = {
    getClassLoader.loadClass(entry)
  }

  override def destroy(): Unit = {
    this.synchronized {
//      super.destroy()
      dynamicManager.remove(entry)
    }
  }

  def detectDestroyIfNecessary(force: Boolean = false): Option[DynamicWebApplicationContext] = {
    getClassLoader.asInstanceOf[DynamicSpringClassLoader].detectDestroyIfNecessary(force)
  }

  @volatile
  private var init = false

  override def refresh(): Unit = {
    this.synchronized {
      if (!init) {
        super.refresh()
        init = true
      }
    }
  }

}
