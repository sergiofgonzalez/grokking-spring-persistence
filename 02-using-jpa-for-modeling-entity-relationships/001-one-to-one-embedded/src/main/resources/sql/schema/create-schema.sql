SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `spring_persistence`;

CREATE DATABASE `spring_persistence` /*!40100 COLLATE 'utf8_bin' */
;

USE `spring_persistence`;

CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
  `password` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
  `username` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
  PRIMARY KEY (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;