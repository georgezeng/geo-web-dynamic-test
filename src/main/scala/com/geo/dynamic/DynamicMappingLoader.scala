package com.geo.dynamic

/**
  * Created by GeorgeZeng on 16/2/21.
  */
class DynamicMappingLoader(mapName: String = "map") {
  private var currentCls: Class[_] = null
  private var currentInstance: Any = null

  def getMapping(entryCls: Class[_]): java.util.Map[String, String] = {
    if (currentCls != entryCls) {
      currentCls = entryCls
      currentInstance = currentCls.newInstance()
    }
    currentCls.getDeclaredField(mapName).get(currentInstance).asInstanceOf[java.util.Map[String, String]]
  }
}

