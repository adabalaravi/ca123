select category_id INTO @lcat from  category where name = 'HIDDEN';
   
insert into live_content(playlist_published_date,series_id,is_geo_blocked,channel_id,category_id,start_time,end_time,duration,title,short_description,long_description,pc_level,is_hd,external_content_id,subscription_id,avs_content_id) values
('2013-10-04 00:00:00',null,'N',100,@lcat,'2013-10-03 20:30:00','2015-10-03 20:45:00',900,'test 100','no_blackout','Programa en el que se ofrecen demostraciones de productos, que se pueden adquirir llamando por teléfono.','1','N','114764',1,null);
insert into live_content(playlist_published_date,series_id,is_geo_blocked,channel_id,category_id,start_time,end_time,duration,title,short_description,long_description,pc_level,is_hd,external_content_id,subscription_id,avs_content_id) values 
('2013-10-04 00:00:00',null,'N',101,@lcat,'2013-10-03 20:45:00','2015-10-04 00:00:00',11700,'test 101','no_blackout','Emisión de videoclips y piezas musicales.','1','N','2851',1,null);
