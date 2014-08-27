--// initial user data
-- Migration SQL that makes the change goes here.
INSERT INTO USERS values ('admin', 'ISMvKXpXpadDiUoOSoAfww==');
INSERT INTO USER_ROLES values ('admin', 'webpublisher');

--//@UNDO
-- SQL to undo the change goes here.
DELETE FROM USERS WHERE LOGIN = 'admin';
DELETE FROM USER_ROLES WHERE LOGIN = 'admin';

