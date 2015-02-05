CREATE TABLE `comics_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `intro` varchar(2000) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `lastuptimeex` varchar(255) DEFAULT NULL,
  `popular` int(11) DEFAULT NULL,
  `favor` int(11) DEFAULT NULL,
  `finish` varchar(5) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;