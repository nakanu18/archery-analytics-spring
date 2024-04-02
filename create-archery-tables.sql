CREATE DATABASE IF NOT EXISTS archery_analytics;
USE archery_analytics;

--
-- Table structure for table archer
--

DROP TABLE IF EXISTS archer;

CREATE TABLE archer (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table archer
--

INSERT INTO `archer` VALUES
	(1,'Leslie'),
	(2,'Emma'),
	(3,'Avani'),
	(4,'Yuri'),
	(5,'Juan');

