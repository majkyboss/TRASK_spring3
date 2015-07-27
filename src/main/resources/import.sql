INSERT INTO `spring3`.`Role` (`name`) VALUES ("admin");
INSERT INTO `spring3`.`Role` (`name`) VALUES ("agent");
INSERT INTO `spring3`.`Role` (`name`) VALUES ("manager");
SELECT * from spring3.Role;

INSERT INTO `spring3`.`User` (`birth_date`, `date_in`, `date_out`, `degree`, `lastname`, `name`, `role_id`, `password_hash`, `username`, `enabled`) VALUES ('1111118888', '1111-11-11', '2222-11-11', 'Bc', 'lastName1', 'user1', '3', '$2a$10$ekcUs5358Nq5XEHysqzEg.vXYGe7EXsIC85K/fusijfUR9oWafPH.', 'user1', true); 
INSERT INTO `spring3`.`User` (`birth_date`, `date_in`, `date_out`, `degree`, `lastname`, `name`, `role_id`, `password_hash`, `username`, `enabled`) VALUES ('2222228888', '2222-11-11', '3333-11-11', 'Bc', 'lastName2', 'user2', '2', '$2a$10$ekcUs5358Nq5XEHysqzEg.vXYGe7EXsIC85K/fusijfUR9oWafPH.', 'user2', true); 
SELECT * FROM spring3.User;

INSERT INTO `spring3`.`RegStatus` (`name`) VALUES ("new");
INSERT INTO `spring3`.`RegStatus` (`name`) VALUES ("approved");
INSERT INTO `spring3`.`RegStatus` (`name`) VALUES ("declimed");
SELECT * FROM spring3.RegStatus;

INSERT INTO `spring3`.`User` (`birth_date`, `date_in`, `degree`, `lastname`, `name`, `role_id`, `password_hash`, `username`, `enabled`) VALUES ('0606068888', '2015-07-01', 'Bc', 'Banik', 'Michal', '1', '$2a$10$ozlQerA9PY3.YNowgaKRF.TQU0M95qHk4MTVy008s2TDSlYSpMi1a', 'majky', true);
INSERT INTO `spring3`.`Branch` (`name`, `manager_id`) VALUES ('Zilina', '1');
