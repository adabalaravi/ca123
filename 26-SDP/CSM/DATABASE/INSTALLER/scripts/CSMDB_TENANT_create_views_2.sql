USE CSMDB_TENANTX;

-- AVS VIEWS
DROP view IF EXISTS AVS_Country_Lang_Code;
DROP view IF EXISTS AVS_Device_ID_Type;
DROP view IF EXISTS AVS_LNK_platform_device_id_type;
DROP view IF EXISTS AVS_Payment_Type;
DROP view IF EXISTS AVS_PC_Extended_Rating;
DROP view IF EXISTS AVS_PC_Level;
DROP view IF EXISTS AVS_Platform;
DROP view IF EXISTS AVS_Retailer_Domain;

DROP view IF EXISTS SDP_ACCOUNT_VIEW;
DROP view IF EXISTS SDP_CREDENTIAL_VIEW;
DROP view IF EXISTS SDP_PARTY_DATA_VIEW;
DROP view IF EXISTS SDP_PARTY_VIEW;
DROP view IF EXISTS SDP_SUBSCRIPTION_VIEW;
DROP view IF EXISTS SDP_SUBSCRIPTION_DETAILS_VIEW;

CREATE VIEW AVS_Country_Lang_Code AS
	select clc.`country_code` AS `country_code`,clc.`country` AS `country`
	from `AVS_BE`.`avs_country_lang_code` clc;
CREATE VIEW AVS_Device_ID_Type AS
	select dt.`type_id` AS `type_id`, dt.`value` AS `value`, dt.`type_description` AS `type_description`, dt.`creation_date` AS `creation_date`, dt.`update_date` AS `update_date`
    from `AVS_BE`.`device_id_type` dt;
CREATE VIEW AVS_LNK_platform_device_id_type AS
    select lnk.`creation_date` AS `creation_date`, lnk.`update_date` AS `update_date`, lnk.`platform_id` AS `platform_id`, lnk.`device_type_id` AS `device_type_id`
    from `AVS_BE`.`avs_platform_device_id_type` lnk;
CREATE VIEW AVS_Payment_Type AS
    select pt.`creation_date` AS `creation_date`, pt.`update_date` AS `update_date`, pt.`payment_type_id` AS `payment_type_id`, pt.`payment_method` AS `payment_method`
    from `AVS_BE`.`payment_type` pt;
CREATE VIEW AVS_PC_Extended_Rating AS
    select er.`creation_date` AS `creation_date`, er.`update_date` AS `update_date`, er.`pc_id` AS `pc_id`, er.`pc_value` AS `pc_value`, er.`pc_description` AS `pc_description`
    from `AVS_BE`.`pc_extended_rating` er;
CREATE VIEW AVS_PC_Level AS
    select l.`creation_date` AS `creation_date`, l.`update_date` AS `update_date`, l.`value` AS `value`, l.`description` AS `description`
    from `AVS_BE`.`avs_pc_level` l;
CREATE VIEW AVS_Platform AS 
    select p.`platform_id` AS `platform_id`, p.`platform_name` AS `platform_name`, p.`creation_date` AS `creation_date`, p.`update_date` AS `update_date`
    from `AVS_BE`.`avs_platform` p;
CREATE VIEW AVS_Retailer_Domain AS
    select rd.`retailer_id` AS `retailer_id`, rd.`host_domain` AS `host_domain`, rd.`description` AS `description`, rd.`creation_date` AS `creation_date`, rd.`update_date` AS `update_date`
    from `AVS_BE`.`avs_retailer_domain` rd;

CREATE VIEW SDP_ACCOUNT_VIEW AS
    select a.`ACCOUNT_ID` AS `ACCOUNT_ID`, a.`ACCOUNT_NAME` AS `ACCOUNT_NAME`, a.`ACCOUNT_DESCRIPTION` AS `ACCOUNT_DESCRIPTION`, a.`PARTY_ID` AS `PARTY_ID`,
    a.`USER_SITE_ID` AS `USER_SITE_ID`, a.`EXTERNAL_ID` AS `EXTERNAL_ID`, a.`STATUS_ID` AS `STATUS_ID`, a.`IS_DEFAULT_PARTY_ACCOUNT` AS `IS_DEFAULT_PARTY_ACCOUNT`,
    a.`ACCOUNT_PROFILE` AS `ACCOUNT_PROFILE`, a.`CREATED_BY_ID` AS `CREATED_BY_ID`, a.`CREATED_DATE` AS `CREATED_DATE`, a.`UPDATED_BY_ID` AS `UPDATED_BY_ID`,
    a.`UPDATED_DATE` AS `UPDATED_DATE`, a.`DELETED_BY_ID` AS `DELETED_BY_ID`, a.`DELETED_DATE` AS `DELETED_DATE`, a.`CHG_STATUS_BY_ID` AS `CHG_STATUS_BY_ID`, a.`CHG_STATUS_DATE` AS `CHG_STATUS_DATE`
    from `AVS_BE`.`sdp_account` a;
CREATE VIEW SDP_CREDENTIAL_VIEW AS
    select c.`EXTERNAL_ID` AS `USERNAME`, c.`PASSWORD` AS `PASSWORD`, c.`ROLE_NAME` AS `ROLE_NAME`, c.`PARTY_ID` AS `PARTY_ID`, c.`STATUS_ID` AS `STATUS_ID`,
    c.`EXTERNAL_ID` AS `EXTERNAL_ID`, c.`CREATED_BY_ID` AS `CREATED_BY_ID`, c.`CREATED_DATE` AS `CREATED_DATE`, c.`UPDATED_BY_ID` AS `UPDATED_BY_ID`, c.`UPDATED_DATE` AS `UPDATED_DATE`,
    c.`DELETED_BY_ID` AS `DELETED_BY_ID`, c.`DELETED_DATE` AS `DELETED_DATE`, c.`CHG_STATUS_BY_ID` AS `CHG_STATUS_BY_ID`, c.`CHG_STATUS_DATE` AS `CHG_STATUS_DATE`
    from `AVS_BE`.`sdp_credential` c;
CREATE VIEW SDP_PARTY_DATA_VIEW AS
    select pd.`PARTY_ID` AS `PARTY_ID`, pd.`FIRST_NAME` AS `FIRST_NAME`, pd.`LAST_NAME` AS `LAST_NAME`, pd.`USER_SITE_ID` AS `USER_SITE_ID`, pd.`STREET_ADDRESS` AS `STREET_ADDRESS`,
    pd.`CITY` AS `CITY`, pd.`BIRTH_DATE` AS `BIRTH_DATE`, pd.`BIRTH_PROVINCE` AS `BIRTH_PROVINCE`, pd.`BIRTH_COUNTRY` AS `BIRTH_COUNTRY`, pd.`BIRTH_CITY` AS `BIRTH_CITY`,
    pd.`GENDER` AS `GENDER`, pd.`ZIP_CODE` AS `ZIP_CODE`, pd.`PROVINCE` AS `PROVINCE`, pd.`EMAIL` AS `EMAIL`, pd.`CONTACT_TEL` AS `CONTACT_TEL`, pd.`CONTACT_FAX` AS `CONTACT_FAX`,
    pd.`NOTE` AS `NOTE`, pd.`COUNTRY` AS `COUNTRY`, pd.`CREATED_BY_ID` AS `CREATED_BY_ID`, pd.`CREATED_DATE` AS `CREATED_DATE`, pd.`UPDATED_BY_ID` AS `UPDATED_BY_ID`,
    pd.`UPDATED_DATE` AS `UPDATED_DATE`, pd.`DELETED_BY_ID` AS `DELETED_BY_ID`, pd.`DELETED_DATE` AS `DELETED_DATE`, pd.`CHG_STATUS_BY_ID` AS `CHG_STATUS_BY_ID`,
    pd.`CHG_STATUS_DATE` AS `CHG_STATUS_DATE`
    from `AVS_BE`.`sdp_party_data` pd;
CREATE VIEW SDP_PARTY_VIEW AS
    select p.`PARTY_ID` AS `PARTY_ID`, p.`PARTY_TYPE_ID` AS `PARTY_TYPE_ID`, p.`PARTY_NAME` AS `PARTY_NAME`, p.`PARTY_DESCRIPTION` AS `PARTY_DESCRIPTION`,
    p.`PARENT_PARTY_ID` AS `PARENT_PARTY_ID`, p.`STATUS_ID` AS `STATUS_ID`, p.`EXTERNAL_ID` AS `EXTERNAL_ID`, p.`PARTY_PROFILE` AS `PARTY_PROFILE`, p.`CREATED_BY_ID` AS `CREATED_BY_ID`,
    p.`CREATED_DATE` AS `CREATED_DATE`, p.`UPDATED_BY_ID` AS `UPDATED_BY_ID`, p.`UPDATED_DATE` AS `UPDATED_DATE`, p.`DELETED_BY_ID` AS `DELETED_BY_ID`, p.`DELETED_DATE` AS `DELETED_DATE`,
    p.`CHG_STATUS_BY_ID` AS `CHG_STATUS_BY_ID`, p.`CHG_STATUS_DATE` AS `CHG_STATUS_DATE`
    from `AVS_BE`.`sdp_party` p;
CREATE VIEW SDP_SUBSCRIPTION_DETAIL_VIEW AS
    select sd.`SUBSCRIPTION_ID` AS `SUBSCRIPTION_ID`, sd.`PACKAGE_ID` AS `PACKAGE_ID`, sd.`STATUS_ID` AS `STATUS_ID`, sd.`SUBSCRIPTION_OFFER_PROFILE` AS `SUBSCRIPTION_OFFER_PROFILE`,
    sd.`EXTERNAL_ID` AS `EXTERNAL_ID`, sd.`CREATED_BY_ID` AS `CREATED_BY_ID`, sd.`CREATED_DATE` AS `CREATED_DATE`, sd.`UPDATED_BY_ID` AS `UPDATED_BY_ID`, sd.`UPDATED_DATE` AS `UPDATED_DATE`,
    sd.`DELETED_BY_ID` AS `DELETED_BY_ID`, sd.`DELETED_DATE` AS `DELETED_DATE`, sd.`CHG_STATUS_BY_ID` AS `CHG_STATUS_BY_ID`, sd.`CHG_STATUS_DATE` AS `CHG_STATUS_DATE`
    from `AVS_BE`.`sdp_subscription_details` sd;
CREATE VIEW SDP_SUBSCRIPTION_VIEW AS
    select s.`SUBSCRIPTION_ID` AS `SUBSCRIPTION_ID`, s.`PARENT_SUBSCRIPTION_ID` AS `PARENT_SUBSCRIPTION_ID`, s.`SOLUTION_OFFER_ID` AS `SOLUTION_OFFER_ID`, s.`STATUS_ID` AS `STATUS_ID`,
    s.`PARTY_ID` AS `PARTY_ID`, s.`USERNAME` AS `USERNAME`, s.`ROLE_NAME` AS `ROLE_NAME`, s.`OWNER_ID` AS `OWNER_ID`, s.`PAYEE_ID` AS `PAYEE_ID`, s.`SITE_ID` AS `SITE_ID`,
    s.`EXTERNAL_ID` AS `EXTERNAL_ID`, s.`START_DATE` AS `START_DATE`, s.`END_DATE` AS `END_DATE`, s.`CREATED_BY_ID` AS `CREATED_BY_ID`, s.`CREATED_DATE` AS `CREATED_DATE`,
    s.`UPDATED_BY_ID` AS `UPDATED_BY_ID`, s.`UPDATED_DATE` AS `UPDATED_DATE`, s.`DELETED_BY_ID` AS `DELETED_BY_ID`, s.`DELETED_DATE` AS `DELETED_DATE`,
    s.`CHG_STATUS_BY_ID` AS `CHG_STATUS_BY_ID`, s.`CHG_STATUS_DATE` AS `CHG_STATUS_DATE`
    from `AVS_BE`.`sdp_subscription` s;

commit;