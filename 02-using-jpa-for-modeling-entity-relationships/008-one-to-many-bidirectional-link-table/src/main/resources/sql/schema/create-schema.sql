SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0//
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0//

DROP DATABASE IF EXISTS `spring_persistence`
//

CREATE DATABASE `spring_persistence` /*!40100 COLLATE 'utf8_bin' */
//

USE `spring_persistence`//

CREATE TABLE `email` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    `user_id` BIGINT(20) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK4qxwfk0jqc0au545318wfiqxx` (`user_id`),
    CONSTRAINT `FK4qxwfk0jqc0au545318wfiqxx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//
//



CREATE TABLE `user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `password` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    `username` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    PRIMARY KEY (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//

CREATE TABLE `user_emails` (
    `user_id` BIGINT(20) NOT NULL,
    `emails_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`user_id`, `emails_id`),
    UNIQUE INDEX `UK_qdskv7hhxfhjf9nutbp7nsgko` (`emails_id`),
    CONSTRAINT `FKp3b5gwvk5a9jhmkuej5u3h0bv` FOREIGN KEY (`emails_id`) REFERENCES `email` (`id`),
    CONSTRAINT `FKqvm7ku0qgn9c09ldhfb0tdtya` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS//
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS//