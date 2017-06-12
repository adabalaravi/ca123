-- Inserting sys parameters and Sys messages.

insert ignore into ‘sys_parameters`(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`) values('costpa','IS_CAPTCHA_VALIDATION_REQUIRED','N','IS_CAPTCHA_VALIDATION_REQUIRED','TDB',1,'IS_CAPTCHA_VALIDATION_REQUIRED','STRING'); 

insert ignore into `sys_messages`(`message_key`,`type`,`language`,`message_text`,`message_code`) values('ERROR_BE_ACTION_4102_ACTION_VOUCHER_EXPIRED','ERROR','en','VOUCHER EXPIRED','ACN_4102'); 

-- Adding unique constraint for the column 'ipn_tnx_id' as part of OPTUS implementation.
alter table purchase_transaction add unique(ipn_tnx_id);

-- Updating message code as part of OPTUS implementation.
update sys_messages set message_code = 'ACN_3164' where message_key = 'ERROR_BE_ACTION_3164_VOUCHER_ID_ALREADY_USED';

commit;