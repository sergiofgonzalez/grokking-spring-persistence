DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `hibernate_sequence`;

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