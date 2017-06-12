-- param_value should be IDS of 365 packages

insert into  sys_parameters (param_group , param_name , param_value , param_label , param_platform , param_order , param_description , update_date , creation_date , param_type ) values  
('costpa','CONDITIONAL_PACKAGE_IDS_GETPRODUCTLIST','','CONDITIONAL_PACKAGE_IDS_GETPRODUCTLIST','TDB',1,'365 PACKAGE IDS TO FILTER IN GETPRODUCTLIST',now(),now(),'STRING');



-- param_value can be any of the package id that should be shown irrespective of any of the conditions
insert into  sys_parameters (param_group , param_name , param_value , param_label , param_platform , param_order , param_description , update_date , creation_date , param_type ) values  
('costpa','PACKAGES_TOBE_SHOWN_GETPRODUCTLIST','','PACKAGES_TOBE_SHOWN_GETPRODUCTLIST','TDB',1,'PACKAGES TO BE SHOWN IDS IN GETPRODUCTLIST',now(),now(),'STRING');


commit;

