use avs_be;

delete from platform where platform_name not in ('PCTV','ANDROID');

select max(publisher_point_id)+1 , max(publisher_point_id)+2 into @pubidvod, @pubidlive
from publisher_point;

insert into platform 
( platform_name, event_geo_blocking, content_geo_blocking) VALUES
( 'IOS', 'N', 'N');

insert into publisher_point
(publisher_point_id, platform, name, description, is_active, base_url, selector, type, cdn_type) VALUES
(@pubidvod,  'IOS', concat('PP',@pubidvod) , NULL, 'Y', 'http://movies-has.avs-accenture.com/', 100, 'VOD', NULL), 
(@pubidlive ,  'IOS', concat('PP',@pubidlive) , NULL, 'Y', 'http://movies-has.avs-accenture.com/', 100, 'LIVE', NULL);

INSERT INTO avs_be.channel
( channel_id, name, type, video_url, order_id, external_id, is_adult, is_recordable) 
VALUES 
( 100, 'Live CA WEB', 'LIVE', 'video_url', 1, '1', 'N', 'N'),
( 101, 'Live CA APP', 'LIVE', 'video_url', 2, '1', 'N', 'N');


insert into channel_platform
(channel_id, platform_name, is_published, video_url, order_id, trailer_url, pictures_url, logo_big, logo_medium, log_small, channel_group, backgroundrgbr, backgroundrgbg, backgroundrgbb, channelcarouselthumbnail) VALUES
(100, 'PCTV', 'Y', 'ondemand/pctv/Smooth_SD_ABIC.ism/manifest', NULL, 'ondemand/pctv/Smooth_SD_ABIC.ism/manifest', null, 'Live_default_big.png', 'Live_default_130x60.png', 'Live_default_small.png', 'Extended', NULL, NULL, NULL, NULL),
(101, 'ANDROID', 'Y', 'ipad_adpative/ABIC.m3u8', NULL, 'ipad_adpative/ABIC.m3u8', null, 'Live_default_big.png', 'Live_default_130x60.png', 'Live_default_small.png', 'Extended', NULL, NULL, NULL, NULL),
(101, 'IOS', 'Y', 'ipad_adpative/ABIC.m3u8', NULL, 'ipad_adpative/ABIC.m3u8', null, 'Live_default_big.png', 'Live_default_130x60.png', 'Live_default_small.png', 'Extended', NULL, NULL, NULL, NULL);

insert into technical_package
(package_id, package_name, package_type, package_description, is_enabled,  external_id) VALUES
(100, 'CA Web Tech Pass', 'LIVE_SUB', '', 'Y',  '1'),
(101, 'CA App Tech Pass', 'LIVE_SUB', '', 'Y',  '2');

insert into channel_technical_pkg
(package_id,channel_id) values
(100,100),
(101,101);




USE CSMDB_TENANT_1;

INSERT INTO `sdp_offer` (`OFFER_ID`, `OFFER_NAME`, `OFFER_DESCRIPTION`, `STATUS_ID`, `SERVICE_VARIANT_ID`, `EXTERNAL_ID`, `OFFER_PROFILE`, `CREATED_BY_ID`, `CREATED_DATE`) VALUES
('1', 'CA Web Tech Pass', 'CA Web Tech Pass', '2', '1', 'PKG_100', 'type=LIVE_SUB;', 'Configurator', curdate()),
('2', 'CA App Tech Pass', 'CA App Tech Pass', '2', '1', 'PKG_101', 'type=LIVE_SUB;', 'Configurator', curdate());


INSERT INTO `sdp_solution_offer` (`SOLUTION_OFFER_ID`, `SOLUTION_ID`, `SOLUTION_OFFER_NAME`, `STATUS_ID`, `SOLUTION_OFFER_PROFILE`, `START_DATE`, `END_DATE`,
`CREATED_BY_ID`, `CREATED_DATE`, `IS_BASIC_PROFILE`, `DURATION`, `SOLUTION_OFFER_TYPE`) VALUES
('1', '1', 'Season Pass Web', '2', 'productType=SUBSCRIPTION;', '2013-10-01 00:00:00', '2014-10-01 00:00:00', 'Configurator', curdate(), '0', NULL, 'Default'),
('2', '1', 'Season Pass App', '2', 'productType=SUBSCRIPTION;', '2013-10-01 00:00:00', '2014-10-01 00:00:00', 'Configurator', curdate(), '0', NULL, 'Default'),
('3', '1', 'Season Pass Membership Web', '2', 'productType=SUBSCRIPTION;', '2013-10-01 00:00:00', '2014-10-01 00:00:00', 'Configurator', curdate(), '0', NULL, 'Membership'),
('4', '1', 'Daily  Pass Web', '2', 'productType=SUBSCRIPTION;', NULL, NULL, 'Configurator', curdate(), '0', '24', 'Default'),
('5', '1', 'Daily Pass App', '2', 'productType=SUBSCRIPTION;', NULL, NULL, 'Configurator', curdate(), '0', '24', 'Default'),
('6', '1', 'Daily Pass Membership Web', '2', 'productType=SUBSCRIPTION;', NULL, NULL, 'Configurator', curdate(), '0', '24', 'Membership');


INSERT INTO `lnk_solution_offer_party_group` (`SOLUTION_OFFER_ID`, `PARTY_GROUP_ID`) VALUES
('1', '1'),
('2', '1'),
('3', '1'),
('4', '1'),
('5', '1'),
('6', '1');


INSERT INTO `ref_price_catalog` (`PRICE_CATALOG_ID`, `PRICE`, `CREATED_BY_ID`, `CREATED_DATE`) VALUES
('2', '100.00', 'Configurator', curdate()),
('3', '10.00', 'Configurator', curdate());


INSERT INTO `sdp_package_price` (`PACKAGE_PRICE_ID`, `RC_FREQUENCY_TYPE_ID`, `RC_PRICE_CATALOG_ID`, `RC_FLAG_PRORATE`, `RC_IN_ADVANCE`, `NRC_PRICE_CATALOG_ID`,
`CURRENCY_TYPE_ID`, `CREATED_BY_ID`, `CREATED_DATE`) VALUES
('1', '1', '1', 'N', 'N', '2', '1', 'Configurator', curdate()),
('2', '1', '1', 'N', 'N', '1', '1', 'Configurator', curdate()),
('3', '1', '1', 'N', 'N', '2', '1', 'Configurator', curdate()),
('4', '1', '1', 'N', 'N', '3', '1', 'Configurator', curdate()),
('5', '1', '1', 'N', 'N', '1', '1', 'Configurator', curdate()),
('6', '1', '1', 'N', 'N', '3', '1', 'Configurator', curdate());


INSERT INTO `sdp_package` (`PACKAGE_ID`, `SOLUTION_OFFER_ID`, `OFFER_ID`, `PACKAGE_PRICE_ID`, `IS_MANDATORY`, `STATUS_ID`, `CREATED_BY_ID`, `CREATED_DATE`) VALUES
('1', '1', '1', '1', '1', '2', 'Configurator', curdate()),
('2', '2', '2', '2', '1', '2', 'Configurator', curdate()),
('3', '3', '1', '3', '1', '2', 'Configurator', curdate()),
('4', '4', '1', '4', '1', '2', 'Configurator', curdate()),
('5', '5', '2', '5', '1', '2', 'Configurator', curdate()),
('6', '6', '1', '6', '1', '2', 'Configurator', curdate());

