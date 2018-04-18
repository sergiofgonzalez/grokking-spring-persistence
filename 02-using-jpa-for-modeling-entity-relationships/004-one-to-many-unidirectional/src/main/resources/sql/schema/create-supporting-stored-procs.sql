DROP PROCEDURE IF EXISTS `createEmail`//
CREATE PROCEDURE createEmail($email VARCHAR(50), $user_id BIGINT)
BEGIN
  INSERT INTO email (email, user_id) VALUES ($email, $user_id);
END//


DROP PROCEDURE IF EXISTS `createUser`//

CREATE PROCEDURE createUser($username VARCHAR(50), $password VARCHAR(50), out $id BIGINT)
BEGIN
  INSERT INTO user (username, password) VALUES ($username, $password);
  SET $id := last_insert_id();
END//
