use hermes5;

delete from DOCUMENT where model_identifier in (select identifier from EPF_MODEL where deleted=1);

delete from IMAGE where model_identifier in (select identifier from EPF_MODEL where deleted=1);

delete from TRANSLATEABLE_TEXT where model_identifier in (select identifier from EPF_MODEL where deleted=1);

delete from EPF_MODEL where deleted=1;

commit;