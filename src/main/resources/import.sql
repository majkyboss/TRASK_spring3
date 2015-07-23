INSERT INTO `spring3`.`Role` (`name`) VALUES ("admin");
INSERT INTO `spring3`.`Role` (`name`) VALUES ("agent");
INSERT INTO `spring3`.`Role` (`name`) VALUES ("manager");
SELECT * from spring3.Role;

INSERT INTO `spring3`.`User` (`birth_date`, `date_in`, `date_out`, `degree`, `lastname`, `name`, `role_id`) VALUES ('1111118888', '1111-11-11', '2222-11-11', 'Bc', 'lastName1', 'user1', '1'); 
INSERT INTO `spring3`.`User` (`birth_date`, `date_in`, `date_out`, `degree`, `lastname`, `name`, `role_id`) VALUES ('2222228888', '2222-11-11', '3333-11-11', 'Bc', 'lastName2', 'user2', '2'); 
SELECT * FROM spring3.User;

INSERT INTO `spring3`.`RegStatus` (`name`) VALUES ("new");
INSERT INTO `spring3`.`RegStatus` (`name`) VALUES ("approved");
INSERT INTO `spring3`.`RegStatus` (`name`) VALUES ("declimed");
SELECT * FROM spring3.RegStatus;