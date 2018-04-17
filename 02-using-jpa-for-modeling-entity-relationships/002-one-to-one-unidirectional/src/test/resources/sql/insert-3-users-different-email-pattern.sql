CALL createEmail('user1@google.com', @email1);
CALL createEmail('user2@github.com', @email2);
CALL createEmail('user3@example.com', @email3);

CALL createUser('googleuser', 'googlepass', @email1);
CALL createUser('githubuser', 'githubpass', @email2);
CALL createUser('exampleuser', 'examplepass', @email3);