--// add published field to model table
-- Migration SQL that makes the change goes here.
ALTER TABLE EPF_MODEL ADD PUBLISHED BOOLEAN NOT NULL;


--//@UNDO
-- SQL to undo the change goes here.
ALTER TABLE EPF_MODEL DROP COLUMN PUBLISHED;

