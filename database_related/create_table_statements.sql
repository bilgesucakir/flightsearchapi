USE `flight_search_db`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `flight` (
                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                          `departure_airport_id` INT,
                          `arrival_airport_id` INT,
                          `departure_datetime` DATETIME,
                          `arrival_datetime` DATETIME,
                          `price` DECIMAL(10, 2),
                          FOREIGN KEY (`departure_airport_id`) REFERENCES `airport` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                          FOREIGN KEY (`arrival_airport_id`) REFERENCES `airport` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `airport` (
                           `id` INT AUTO_INCREMENT PRIMARY KEY,
                           `city` VARCHAR(255)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;