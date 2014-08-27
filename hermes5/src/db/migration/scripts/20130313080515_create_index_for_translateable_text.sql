--// create_index_for_translateable_text
-- Migration SQL that makes the change goes here.

create index specific_translation_idx on TRANSLATEABLE_TEXT (MODEL_IDENTIFIER, element_identifier, text_identifier);


--//@UNDO
-- SQL to undo the change goes here.

drop index specific_translation_idx on TRANSLATEABLE_TEXT;
