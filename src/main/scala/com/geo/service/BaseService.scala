package com.geo.service

import java.io.Serializable
import com.geo.dao.BaseDao
import com.geo.entity.BaseEntity
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.transaction.annotation.Transactional

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Transactional
abstract class BaseService[T <: BaseEntity[_ <: Serializable]] extends LazyLogging {
  def getDao(): BaseDao[T]

  @Transactional(readOnly = true)
  def load(id: Serializable): T = {
    getDao().load(id)
  }

  @Transactional(readOnly = true)
  def get(id: Serializable): T = {
    getDao().get(id)
  }

  def save(o: T): Unit = {
    getDao().save(o)
  }
}
