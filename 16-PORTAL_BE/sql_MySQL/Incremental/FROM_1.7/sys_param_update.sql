UPDATE `sys_parameters` SET `param_value`='Y' WHERE `param_name`='RESET_CONFIRM_MAIL';
UPDATE `sys_parameters` SET `param_value`='N' WHERE `param_name`='KEEPALIVE_CHECKRIGHTS_ACTIVE';
UPDATE `sys_parameters` SET `param_value`='N' WHERE `param_name`='KEEPALIVE_SESSION_CHECK_ACTIVE';
UPDATE `sys_parameters` SET `param_value`='https://cricket-uat.avs-accenture.com/AVS/besc?action=ChangePassword&' WHERE `param_name`='RESET_URL';
