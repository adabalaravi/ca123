
-- CREATE TABLE SCRIPT [user_concurrent_streaming]

 CREATE TABLE `user_concurrent_streaming` (
  `sno` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `crm_account_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `session_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `device_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_disabled` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT 'false',
  PRIMARY KEY (`sno`),
  UNIQUE KEY `myUniqueConstraint` (`session_id`)
) ENGINE=MyISAM AUTO_INCREMENT=88 DEFAULT CHARSET=utf8



-- Triggers script [user_concurrent_streaming]


CREATE TRIGGER avs_be.user_concurrent_streaming_insert  BEFORE INSERT ON avs_be.user_concurrent_streaming  FOR EACH ROW SET NEW.CREATION_DATE =  NOW() ,NEW.UPDATE_DATE =  NOW() ;
CREATE TRIGGER avs_be.user_concurrent_streaming_update  BEFORE UPDATE ON avs_be.user_concurrent_streaming  FOR EACH ROW SET NEW.update_date =  NOW() ;

-- Sys messages for user_concurrent_streaming API 

insert ignore into `sys_messages`(`message_key`,`type`,`language`,`message_text`,`message_code`) 
values('ERROR_BE_ACTION_4104_ACTION_USER_HAS_EXCEEDED_CONCURRENT_STREAMINGS','ERROR','en','USER_HAVE_EXCEEDED_CONCURRENT_STREAMINGS','ACN_4104'); 

commit;

insert ignore into sys_parameters(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`) values ('costpa','CONCURRENT_STREAMING_THRESHOLD_COUNT','2','CONCURRENT_STREAMING_THRESHOLD_COUNT','TDB',1,'CONCURRENT_STREAMING_THRESHOLD_COUNT','STRING'); 

commit;

insert ignore into sys_parameters(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`) values ('costpa','CONCURRENT_STREAMING_IN_PANIC','NO','CONCURRENT_STREAMING_IN_PANIC','TDB',1,'PANIC FLAG ON CONCURRENT STREAMING API','STRING'); 

commit;


-- BT integration sys parameters scripts

insert ignore into `sys_parameters`(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`) values ('costpa','PREFIX_RECEIPTID','BT_CA_','PREFIX_RECEIPTID','TDB',1,'Prefix of ReceiptID for BT PACKAGES ','STRING');

commit;

insert ignore into `sys_parameters`(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`) values ('costpa','BT_PACKAGE_IDS','45','BT_PACKAGE_IDS','TDB',1,'List of BT PACKAGES ','STRING');

commit;

-- Add column
alter table user_concurrent_streaming add column channel_id varchar(10);

-- user_concurrent_streaming_trigger table is used to track the total users on streaming
CREATE TABLE `user_concurrent_streaming_trigger` (
  `sno` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `crm_account_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `session_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `device_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_disabled` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT 'false',
  `channel_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- trigger
CREATE TRIGGER user_concurrent_streaming_trigger_insert AFTER INSERT ON user_concurrent_streaming
for each row 
INSERT INTO user_concurrent_streaming_trigger(creation_date,update_date,crm_account_id,session_id,device_type,is_disabled,channel_id) 
values(new.creation_date,new.update_date,new.crm_account_id,new.session_id,new.device_type,new.is_disabled,new.channel_id);

commit;

-- Allow free video streaming channels
insert ignore into sys_parameters(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`)
values ('costpa','CHANNELS_FOR_FREE_VIDEOS','0','CHANNELS_FOR_FREE_VIDEOS','TDB',1,'CHANNELS_FOR_FREE_VIDEOS','STRING'); 

insert ignore into sys_parameters(`param_group`,`param_name`,`param_value`,`param_label`,`param_platform`,`param_order`,`param_description`,`param_type`) 
values ('costpa','IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED','Y','IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED','TDB',1,'IS_ACTIVE_CHANNELS_VALIDATION_REQUIRED','STRING'); 

commit;
