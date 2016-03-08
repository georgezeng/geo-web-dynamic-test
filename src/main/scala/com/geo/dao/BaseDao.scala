package com.geo.dao

import java.io.Serializable
import java.lang.reflect.ParameterizedType
import java.util.Date

import com.geo.entity.BaseEntity
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.hibernate.criterion.Restrictions
import org.hibernate.{Criteria, SessionFactory}

/**
  * Created by GeorgeZeng on 16/3/7.
  */
abstract class BaseDao[T <: BaseEntity[_ <: Serializable]](sessionFactory: SessionFactory) extends LazyLogging {
  private val entityCls = getEntityClass()

  private def getEntityClass(): Class[T] = {
    def getEntityClassFromSuperClass(cls: Class[_]): Class[T] = {
      val tas = cls.getGenericSuperclass.asInstanceOf[ParameterizedType].getActualTypeArguments
      if (tas != null && tas.length > 0) {
        return tas(0).asInstanceOf[Class[T]]
      } else if (cls != classOf[Any]) {
        getEntityClassFromSuperClass(cls.getSuperclass)
      } else {
        throw new IllegalArgumentException("Can not find any entity class")
      }
    }
    getEntityClassFromSuperClass(getClass)
  }

  protected def findUniqueByKey(key: Serializable, fieldName: String): T = {
    createCriteria()
      .add(Restrictions.eq(fieldName, key))
      .uniqueResult().asInstanceOf[T]
  }

  def load(id: Serializable): T = {
    sessionFactory.getCurrentSession.load(entityCls, id)
  }

  def get(id: Serializable): T = {
    sessionFactory.getCurrentSession.get(entityCls, id)
  }

  def save(o: T) {
    val time = new Date
    if (o.getCreatedTime() == null) {
      o.setCreatedTime(time)
    }
    o.setUpdatedTime(time)
    sessionFactory.getCurrentSession.saveOrUpdate(o)
  }

  protected def createCriteria(): Criteria = {
    sessionFactory.getCurrentSession
      .createCriteria(entityCls)
  }
}
