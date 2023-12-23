USE `flight_search_db`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `flights` (
                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                          `departure_airport_id` INT,
                          `arrival_airport_id` INT,
                          `departure_datetime` TIMESTAMP,
                          `arrival_datetime` TIMESTAMP,
                          `price` DECIMAL(10, 2),
                          FOREIGN KEY (`departure_airport_id`) REFERENCES `airport` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                          FOREIGN KEY (`arrival_airport_id`) REFERENCES `airport` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `airports` (
                           `id` INT AUTO_INCREMENT PRIMARY KEY,
                           `city` VARCHAR(255)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user`(
						`id` int auto_increment primary key,
                        `username` varchar(32) unique not null,
                        `password` varchar(255) not null
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE `role`(
						`id` int auto_increment primary key,
                        `name` varchar(32) not null

) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE `user_role` (
                           `user_id` int not null,
                           `role_id` int not null,	
                           PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT 	`constraint1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `constraint2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) 
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;