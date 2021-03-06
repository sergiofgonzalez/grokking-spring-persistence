SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0//
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0//

DROP DATABASE IF EXISTS `spring_persistence`
//

CREATE DATABASE `spring_persistence` /*!40100 COLLATE 'utf8_bin' */
//

USE `spring_persistence`//

CREATE TABLE `user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `password` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    `username` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    PRIMARY KEY (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//

CREATE TABLE `email` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
    PRIMARY KEY (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//

CREATE TABLE `user_emails` (
    `users_id` BIGINT(20) NOT NULL,
    `emails_id` BIGINT(20) NOT NULL,
    PRIMARY KEY (`users_id`, `emails_id`),
    INDEX `FKp3b5gwvk5a9jhmkuej5u3h0bv` (`emails_id`),
    CONSTRAINT `FKksa1m6tl6s8981p2a345dedio` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKp3b5gwvk5a9jhmkuej5u3h0bv` FOREIGN KEY (`emails_id`) REFERENCES `email` (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//


SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS//
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS//