--// add urlde to model
-- Migration SQL that makes the change goes here.
ALTER TABLE EPF_MODEL ADD (
	URL_DE VARCHAR(255) 
);


--//@UNDO
-- SQL to undo the change goes here.
ALTER TABLE EPF_MODEL DROP COLUMN  URL_DE;

