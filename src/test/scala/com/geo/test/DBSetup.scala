package com.geo.test

import java.io.{BufferedReader, InputStreamReader}
import javax.annotation.PostConstruct

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Component
class DBSetup @Autowired()(sessionFactory: SessionFactory) {

  @PostConstruct
  def create(): Unit = {
    val session = sessionFactory.openStatelessSession()
    try {
      val reader = new BufferedReader(new InputStreamReader(getClass.getResourceAsStream("/db.sql")))
      val sqlText = new StringBuilder
      var line = reader.readLine()
      while (line != null) {
        sqlText.append(line)
        line = reader.readLine()
      }
      val sqls = sqlText.toString().split(";\\s*")
      for (sql <- sqls) {
        session.createSQLQuery(sql).executeUpdate()
      }
    } finally {
      session.close()
    }
  }
}
