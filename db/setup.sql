create database db_springmvc; -- Create the new database
create user 'user'@'localhost' identified by 'password'; -- Creates the user
grant all on db_springmvc.* to 'user'@'localhost'; -- Gives all the privileges to the new user on the newly created database

-- Create the tables that will hold the users
CREATE TABLE `db_springmvc`.`users`
(
  `username`  VARCHAR(45) NOT NULL,
  `password`  VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`)
);