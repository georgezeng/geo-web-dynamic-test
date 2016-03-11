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
abstract class BaseService[T <: BaseEntity[_ <: Serializable]](protected val dao: BaseDao[T]) extends LazyLogging {

  @Transactional(readOnly = true)
  def load(id: Serializable): T = {
    dao.load(id)
  }

  @Transactional(readOnly = true)
  def get(id: Serializable): T = {
    dao.get(id)
  }

  def save(o: T): Unit = {
    dao.save(o)
  }
}
