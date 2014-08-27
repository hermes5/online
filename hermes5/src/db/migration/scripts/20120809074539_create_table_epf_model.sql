--// create table epf model
-- Migration SQL that makes the change goes here.
CREATE TABLE EPF_MODEL (
ID BIGINT NOT NULL auto_increment,	
IDENTIFIER VARCHAR(255) NOT NULL,
VERSION VARCHAR(255),
TITLE VARCHAR(255),
DELETED BOOLEAN NOT NULL,
PRIMARY KEY  (`ID`)
);


--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE EPF_MODEL;

