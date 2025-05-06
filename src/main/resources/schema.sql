CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accessibility` bit(1) DEFAULT 0,
  `budget` bit(1) DEFAULT 0,
  `ecologic` bit(1) DEFAULT 0,
  `hurry` bit(1) DEFAULT 0,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `transport` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `route` (
  `id` int NOT NULL AUTO_INCREMENT,
  `destination_name` varchar(255) NOT NULL,
  `distance` varchar(255) NOT NULL,
  `origin_name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `time_to_reach` varchar(255) NOT NULL,
  `total_cost` varchar(255) NOT NULL,
  `transport` varchar(255) NOT NULL,
  `available` bit(1) DEFAULT 1,
  `request_properties` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `requisition` (
  `id` int NOT NULL AUTO_INCREMENT,
  `times_requested` int(11) DEFAULT 1,
  `route_id` int NOT NULL,
  `user_id` int NOT NULL,

  KEY `user` (`user_id`),
  KEY `route` (`route_id`),
  FOREIGN KEY (`route_id`) REFERENCES `route` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),

  PRIMARY KEY(`id`)

);
