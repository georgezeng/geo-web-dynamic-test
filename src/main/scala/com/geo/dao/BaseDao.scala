package com.geo.dao

import java.io.Serializable
import java.util.Date

import com.geo.entity.BaseEntity
import com.geo.util.ReflectionUtil
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.hibernate.criterion.Restrictions
import org.hibernate.{Criteria, SessionFactory}

/**
  * Created by GeorgeZeng on 16/3/7.
  */
abstract class BaseDao[T <: BaseEntity[_ <: Serializable]](protected val sessionFactory: SessionFactory) extends LazyLogging {
  protected val entityClass = getEntityClass()

  private def getEntityClass(): Class[T] = {
    val cls = ReflectionUtil.getTypeParameterType[T](getClass)
    if (cls == null) {
      throw new IllegalArgumentException("Can not find any entity class")
    }
    cls
  }

  protected def findUniqueByKey(key: Serializable, fieldName: String): T = {
    createCriteria()
      .add(Restrictions.eq(fieldName, key))
      .uniqueResult().asInstanceOf[T]
  }

  def load(id: Serializable): T = {
    sessionFactory.getCurrentSession.load(entityClass, id)
  }

  def get(id: Serializable): T = {
    sessionFactory.getCurrentSession.get(entityClass, id)
  }

  def save(o: T) {
    val time = new Date
    if (o.getCreatedTime() == null) {
      o.setCreatedTime(time)
    }
    o.setUpdatedTime(time)
    sessionFactory.getCurrentSession.saveOrUpdate(o)
  }

  def delete(o: T): Unit = {
    sessionFactory.getCurrentSession.delete(o)
  }

  protected def createCriteria(): Criteria = {
    sessionFactory.getCurrentSession
      .createCriteria(entityClass)
  }
}
