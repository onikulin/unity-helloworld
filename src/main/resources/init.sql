USE helloworld;

CREATE TABLE IF NOT EXISTS WELCOME_MESSAGE (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  TEXT_MSG VARCHAR(100)
) engine=InnoDB;

DELETE FROM WELCOME_MESSAGE;

INSERT INTO WELCOME_MESSAGE (TEXT_MSG) VALUES ('Hello Unity1');