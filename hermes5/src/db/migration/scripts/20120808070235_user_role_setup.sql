--// user role setup
-- Migration SQL that makes the change goes here.

CREATE TABLE USERS (
		LOGIN VARCHAR(64) PRIMARY KEY,
		PASSWD VARCHAR(64)
	);
CREATE TABLE USER_ROLES (
		LOGIN VARCHAR(64),
		ROLE VARCHAR(32)
);

--//@UNDO
-- SQL to undo the change goes here.

DROP TABLE USERS;
DROP TABLE USER_ROLES;
