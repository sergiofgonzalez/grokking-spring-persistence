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

CREATE TABLE `email_users` (
    `email_id` BIGINT(20) NOT NULL,
    `users_id` BIGINT(20) NOT NULL,
    UNIQUE INDEX `UKpavi7svedcqlbrq3npk0pm9ng` (`users_id`, `email_id`),
    INDEX `FKqh0goxhd3xtd7l6tgby6ih5wo` (`email_id`),
    CONSTRAINT `FKimrgtrmicxpqy3gsyw026k59j` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FKqh0goxhd3xtd7l6tgby6ih5wo` FOREIGN KEY (`email_id`) REFERENCES `email` (`id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
//


SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS//
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS//