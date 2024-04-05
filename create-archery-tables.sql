CREATE DATABASE IF NOT EXISTS `archery_analytics`;
USE `archery_analytics`;

--
-- Table structure for table archer
--

DROP TABLE IF EXISTS `archer`;

CREATE TABLE `archer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `round` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `archer_id` INT NOT NULL,
  `date` DATE NOT NULL,  -- Stores the date the round was shot  
  FOREIGN KEY (`archer_id`) REFERENCES `archer`(`id`),  -- Links round to archer table
  PRIMARY KEY (`id`)
);

CREATE TABLE `end` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `round_id` INT NOT NULL,
  `number` INT NOT NULL,  -- End number within the round (e.g., End 1, End 2)
  `value` VARCHAR(255),  -- Stores end value as string with delimiter (e.g., "10 9 8")
  FOREIGN KEY (`round_id`) REFERENCES `round`(`id`),
  PRIMARY KEY (`id`)
);


--
-- Data for table archer
--

INSERT INTO `archer` VALUES
  (1,'Leslie'),
  (2,'Emma'),
  (3,'Avani'),
  (4,'Yuri'),
  (5,'Juan');
