CREATE DATABASE delivery DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE USER 'java'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON delivery.* TO 'java'@'localhost';