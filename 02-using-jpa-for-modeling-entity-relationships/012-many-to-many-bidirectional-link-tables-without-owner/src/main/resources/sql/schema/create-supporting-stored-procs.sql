DROP PROCEDURE IF EXISTS `createEmail`//
CREATE PROCEDURE createEmail($email VARCHAR(50), out $id BIGINT)
BEGIN
  INSERT INTO email (email) VALUES ($email);
  SET $id := last_insert_id();
END//


DROP PROCEDURE IF EXISTS `createUser`//

CREATE PROCEDURE createUser($username VARCHAR(50), $password VARCHAR(50), out $id BIGINT)
BEGIN
  INSERT INTO user (username, password) VALUES ($username, $password);
  SET $id := last_insert_id();
END//

DROP PROCEDURE IF EXISTS `assignEmailToUser`//

CREATE PROCEDURE assignEmailToUser($email_id BIGINT, $user_id BIGINT)
BEGIN
  INSERT INTO user_emails (user_id, emails_id) VALUES ($user_id, $email_id);
  INSERT INTO email_users (email_id, users_id) VALUES ($email_id, $user_id);
END//
