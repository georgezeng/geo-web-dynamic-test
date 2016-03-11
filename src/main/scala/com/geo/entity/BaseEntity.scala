package com.geo.entity

import java.util.Date
import javax.persistence.{Transient, Id, GeneratedValue, MappedSuperclass}
import java.lang.{Long => jLong}

import org.hibernate.annotations.GenericGenerator

/**
  * Created by GeorgeZeng on 16/3/6.
  */
@MappedSuperclass
abstract class BaseEntity[Key <: java.io.Serializable] extends java.io.Serializable {

  private var id: Key = _
  private var createdTime: Date = _
  private var updatedTime: Date = _
  private var createdBy: String = _
  private var updatedBy: String = _

  @Transient
  def getId(): Key = id

  private def setId(id: Key): Unit = {
    this.id = id
  }

  def getCreatedTime(): Date = {
    createdTime
  }

  def getUpdatedTime(): Date = {
    updatedTime
  }

  def getCreatedBy(): String = {
    createdBy
  }

  def getUpdatedBy(): String = {
    updatedBy
  }

  def setCreatedTime(time: Date): Unit = {
    createdTime = time
  }

  def setUpdatedTime(time: Date): Unit = {
    updatedTime = time
  }

  def setCreatedBy(username: String): Unit = {
    createdBy = username
  }

  def setUpdatedBy(username: String): Unit = {
    updatedBy = username
  }

}

@MappedSuperclass
abstract class LongKeyEntity extends BaseEntity[jLong] {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  override def getId(): jLong = super.getId()
}