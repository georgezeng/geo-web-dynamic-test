CREATE TABLE IF NOT EXISTS `RoleAuthority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `createdTime` datetime NOT NULL,
  `updatedTime` datetime NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `updatedBy` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_authority_name` (`name`),
  KEY `idx_authority_createdBy` (`createdBy`),
  KEY `idx_authority_updatedBy` (`updatedBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `Role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `createdTime` datetime NOT NULL,
  `updatedTime` datetime NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `updatedBy` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_role_name` (`name`),
  KEY `idx_role_createdBy` (`createdBy`),
  KEY `idx_role_updatedBy` (`updatedBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `birth` date DEFAULT NULL,
  `createdTime` datetime NOT NULL,
  `updatedTime` datetime NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `updatedBy` varchar(100) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `accountNonExpired` bit(1) NOT NULL,
  `credentialsNonExpired` bit(1) NOT NULL,
  `accountNonLocked` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_username` (`username`),
  KEY `idx_user_email` (`email`),
  KEY `idx_user_createdBy` (`createdBy`),
  KEY `idx_user_updatedBy` (`updatedBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `UserRole` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


