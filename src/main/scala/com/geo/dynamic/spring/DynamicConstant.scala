package com.geo.dynamic.spring

import java.lang.annotation.Annotation

import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.{Component, Controller, Repository, Service}
import org.springframework.web.bind.annotation.RestController

/**
  * Created by GeorgeZeng on 16/2/21.
  */
object DynamicConstant {
  val springConfigTypes = {
    val list = new java.util.ArrayList[Class[_ <: Annotation]]()
    list.add(classOf[Controller])
    list.add(classOf[RestController])
    list.add(classOf[Component])
    list.add(classOf[Service])
    list.add(classOf[Repository])
    list.add(classOf[Configuration])
    list.add(classOf[Configurable])
    list
  }
}