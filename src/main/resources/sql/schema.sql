DROP TABLE IF EXISTS TODO_ENTITY;
DROP TABLE IF EXISTS USER_ENTITY;

CREATE TABLE USER_ENTITY (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             LOGIN VARCHAR(255) NOT NULL,
                             PASSWORD VARCHAR(255) NOT NULL,
                             ROLE VARCHAR(20) NOT NULL,
                             IS_ACTIVE BOOLEAN NOT NULL
);

CREATE TABLE TODO_ENTITY (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             USER_ID INT NOT NULL,
                             TITLE VARCHAR(255) NOT NULL,
                             COMPLETED BOOLEAN NOT NULL
);