package com.geo.dynamic

/**
  * Created by GeorgeZeng on 16/2/21.
  */
class DynamicEntryLoader() {
  private var currentCls: Class[_] = null
  private var currentInstance: Any = null

  def entryMapping(entryCls: Class[_]): java.util.Map[String, String] = {
    if (currentCls != entryCls) {
      currentCls = entryCls
      currentInstance = currentCls.newInstance()
    }
    currentCls.getDeclaredField("map").get(currentInstance).asInstanceOf[java.util.Map[String, String]]
  }
}

