#CREATE DATABASE microservicetest DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE if EXISTS user ;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(20),
  name VARCHAR(20),
  age int(3),
  balance DECIMAL(10,2),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;