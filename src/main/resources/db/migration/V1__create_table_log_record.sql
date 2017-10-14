CREATE TABLE IF NOT EXISTS `log_record` (
  `id` int(11) AUTO_INCREMENT NOT NULL,
  `ip` varchar(50) NOT NULL,
  `date` datetime NOT NULL,
  `request` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=myisam DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;