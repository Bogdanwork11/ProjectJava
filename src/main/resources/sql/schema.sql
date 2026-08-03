DROP TABLE IF EXISTS TODO;
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS ( -- to remove entity suffix/already done)
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             LOGIN VARCHAR(255) NOT NULL,
                             PASSWORD VARCHAR(255) NOT NULL,
                             ROLE VARCHAR(20) NOT NULL, -- check (admin | user)
                             IS_ACTIVE BOOLEAN NOT NULL
);

CREATE TABLE TODO (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             USER_ID INT NOT NULL,
                             TITLE VARCHAR(255) NOT NULL,
                             COMPLETED BOOLEAN NOT NULL
);