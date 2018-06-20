INSERT INTO customer(first_name, last_name, email, birthday) VALUES('友梨奈', '平手', 'yhirate@keyaki.com', '2001-06-25');
INSERT INTO customer(first_name, last_name, email, birthday) VALUES('京子', '齊藤', 'ksaitoh@hiragana.com', '1997-09-05');
INSERT INTO customer(first_name, last_name, email, birthday) VALUES('友香', '菅井', 'ysugai@keyaki.com', '1995-11-29');
INSERT INTO customer(first_name, last_name, email, birthday) VALUES('佑美', '若月', 'ywakatsuki@nogi.com', '1994-06-27');
INSERT INTO customer(first_name, last_name, email, birthday) VALUES('玲香', '桜井', 'rsakurai@nogi.com', '1994-05-16');

-- password = "user" (Encoded with BCrypt)
INSERT INTO account(id, name, email, password) VALUES(1, 'user', 'user@example.com', '$2a$10$wdVyuUaOrZTawx4Z7LvqeOUlY2k4NzhPyjHmbMEEaIePCgyUnUaPG');
-- password = "admin" (Encoded with BCrypt)
INSERT INTO account(id, name, email, password) VALUES(2, 'admin', 'admin@example.com', '$2a$10$ztH0ZXBexpjNLe9oBzmbzuow8siaSdEIK9dKk9pfPgHcRHXpcyjpi');
-- password = "actuator" (Encoded with BCrypt), used in 03-boot2
INSERT INTO account(id, name, email, password) VALUES(3, 'actuator', 'actuator', '$2a$10$VTpRi7sdggXzaq8XuGEYrey/TkDYlr1vh05IQjJNzHy/XccmUUL/O');

INSERT INTO account_authority(account_id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO account_authority(account_id, authority_name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO account_authority(account_id, authority_name) VALUES (3, 'ROLE_ACTUATOR');