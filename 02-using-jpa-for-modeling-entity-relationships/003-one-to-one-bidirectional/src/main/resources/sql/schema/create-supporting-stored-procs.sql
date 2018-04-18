DROP PROCEDURE IF EXISTS `createEmail`//
CREATE PROCEDURE createEmail($email VARCHAR(50), out $id BIGINT)
BEGIN
  INSERT INTO email(email) VALUES ($email);
  SET $id := last_insert_id();
END//


DROP PROCEDURE IF EXISTS `createUser`//

CREATE PROCEDURE createUser($username VARCHAR(50), $password VARCHAR(50), $email_id BIGINT)
BEGIN
  INSERT INTO user (username, password, email_id) VALUES ($username, $password, $email_id);
END//
