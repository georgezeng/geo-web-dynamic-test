package com.geo.test

import com.geo.entity.{RoleAuthority, UserRole, Role, User}
import org.hibernate.SessionFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by GeorgeZeng on 16/3/7.
  */
@Component
class DBSetup @Autowired()(sessionFactory: SessionFactory) extends InitializingBean {
  override def afterPropertiesSet(): Unit = {
    val session = sessionFactory.openStatelessSession()
    try {
      session.createSQLQuery(
        s"""
            CREATE TABLE IF NOT EXISTS `${classOf[User].getSimpleName}`  (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `username` varchar(100) NOT NULL,
              `password` varchar(100) NOT NULL,
              `email` varchar(100) NOT NULL,
              `birth` date DEFAULT NULL,
              `createdTime` datetime NOT NULL,
              `updatedTime` datetime,
              `createdBy` varchar(100) NOT NULL,
              `updatedBy` varchar(100) NOT NULL,
              `enabled` bit(1) NOT NULL,
              PRIMARY KEY (`id`),
              UNIQUE KEY `idx_username` (`username`),
              KEY `idx_createdBy` (`createdBy`),
              KEY `idx_email` (`email`),
              KEY `idx_updatedBy` (`updatedBy`)
            ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
       """).executeUpdate()

      session.createSQLQuery(
        s"""
            CREATE TABLE IF NOT EXISTS `${classOf[Role].getSimpleName}`  (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `name` varchar(100) NOT NULL,
              `createdTime` datetime NOT NULL,
              `updatedTime` datetime NOT NULL,
              `createdBy` varchar(100) NOT NULL,
              `updatedBy` varchar(100) NOT NULL,
              PRIMARY KEY (`id`),
              KEY `idx_name2` (`name`)
            )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
       """).executeUpdate()

      session.createSQLQuery(
        s"""
            CREATE TABLE IF NOT EXISTS `${classOf[UserRole].getSimpleName}`  (
              `user_id` bigint(20) NOT NULL,
              `role_id` bigint(20) NOT NULL,
              PRIMARY KEY (`user_id`,`role_id`),
              KEY `fk_role` (`role_id`),
              CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`),
              CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
            )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
       """).executeUpdate()

      session.createSQLQuery(
        s"""
            CREATE TABLE IF NOT EXISTS `${classOf[RoleAuthority].getSimpleName}`  (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `name` varchar(100) NOT NULL,
              `role_id` bigint(20) NOT NULL,
              `createdTime` datetime NOT NULL,
              `updatedTime` datetime NOT NULL,
              `createdBy` varchar(100) NOT NULL,
              `updatedBy` varchar(100) NOT NULL,
              PRIMARY KEY (`id`),
              KEY `idx_name` (`name`)
            )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
       """).executeUpdate()
    } finally {
      session.close()
    }
  }
}
