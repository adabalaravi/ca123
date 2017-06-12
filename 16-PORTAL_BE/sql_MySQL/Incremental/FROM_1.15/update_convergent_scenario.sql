insert into channel_technical_pkg (package_id, channel_id) values (100, 101);

insert into channel_technical_pkg (package_id, channel_id) values (101, 100);

INSERT INTO channel_platform (channel_id, platform_name, is_published, video_url, trailer_url, logo_big, logo_medium, log_small, channel_group) VALUES (101, 'PCTV', 'Y', 'ondemand/pctv/Smooth_SD_ABIC.ism/manifest', 'ondemand/pctv/Smooth_SD_ABIC.ism/manifest', 'Live_default_big.png', 'Live_default_130x60.png', 'Live_default_small.png', 'Extended');

INSERT INTO channel_platform (channel_id, platform_name, is_published, video_url, trailer_url, logo_big, logo_medium, log_small, channel_group) VALUES (100, 'ANDROID', 'Y', 'ipad_adpative/ABIC.m3u8', 'ipad_adpative/ABIC.m3u8', 'Live_default_big.png', 'Live_default_130x60.png', 'Live_default_small.png', 'Extended');

INSERT INTO channel_platform (channel_id, platform_name, is_published, video_url, trailer_url, logo_big, logo_medium, log_small, channel_group) VALUES (100, 'IOS', 'Y', 'ipad_adpative/ABIC.m3u8', 'ipad_adpative/ABIC.m3u8', 'Live_default_big.png', 'Live_default_130x60.png', 'Live_default_small.png', 'Extended');
