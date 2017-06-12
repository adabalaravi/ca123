-- Inserting Sys messages table for USER does not have Active chaanels.
insert ignore into `sys_messages`(`message_key`,`type`,`language`,`message_text`,`message_code`) 
values('ERROR_BE_ACTION_4103_ACTION_USER_DOESNOT_HAVE_ACTIVE_CHANNELS','ERROR','en','USER_DOESNOT_HAVE_ACTIVE_CHANNELS','ACN_4103'); 

commit;