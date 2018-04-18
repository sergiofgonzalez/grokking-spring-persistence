CALL createUser('user1', 'pass1', @user1)//
CALL createUser('user2', 'pass2', @user2)//
CALL createUser('user3', 'pass3', @user3)//

CALL createEmail('user1@example.com', @user1, @email1)//
CALL createEmail('user21@example.com', @user2, @email21)//
CALL createEmail('user22@example.com', @user2, @email22)//
CALL createEmail('user31@example.com', @user3, @email31)//
CALL createEmail('user32@example.com', @user3, @email32)//
CALL createEmail('user33@example.com', @user3, @email33)//

CALL assignEmailToUser(@email1, @user1)// 
CALL assignEmailToUser(@email21, @user2)//
CALL assignEmailToUser(@email22, @user2)//
CALL assignEmailToUser(@email31, @user3)//
CALL assignEmailToUser(@email32, @user3)//
CALL assignEmailToUser(@email33, @user3)//