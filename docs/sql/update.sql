-- 2014/1/3
ALTER TABLE  `article` ADD  `grade` INT NOT NULL DEFAULT  '0' COMMENT  '内容等级' AFTER  `simple_content`;

-- 2014/1/4
ALTER TABLE `article` ADD `is_valid` TINYINT( 1 ) NOT NULL DEFAULT '0' COMMENT '是否有效' AFTER `article_ctime` ;
ALTER TABLE `article` DROP `viewpoint_id`; 
ALTER TABLE `article` CHANGE `article_ctime` `article_ctime` DATETIME NOT NULL COMMENT '文章发布时间';
ALTER TABLE `industry_article` CHANGE `content` `simple_content` VARCHAR( 250 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容';
ALTER TABLE `industry_article` ADD `pub_time` DATETIME NOT NULL COMMENT '文章发布时间' AFTER `media_id` ;
ALTER TABLE  `dict_car_brand` CHANGE  `disabled`  `is_valid` INT( 11 ) NULL DEFAULT  '0' COMMENT  '是否有效（1：有效）';


-- 2014/1/6
ALTER TABLE  `industry_article` ADD  `is_valid` TINYINT( 1 ) NOT NULL DEFAULT  '0' AFTER  `pub_time`;
ALTER TABLE  `keyword` CHANGE  `is_industry`  `type` INT( 1 ) NOT NULL DEFAULT  '0' COMMENT  '1:article 2:industry_article 3:属于两者';
ALTER TABLE  `article` CHANGE  `affection`  `affection` INT( 2 ) NOT NULL DEFAULT  '0' COMMENT  '情感说明 3:正面 2:中性 1:负面';
ALTER TABLE  `article_comment` CHANGE  `affection`  `affection` INT( 1 ) NULL DEFAULT  '0' COMMENT  '情感说明 3:正面 2:中性 1:负面';
ALTER TABLE  `viewpoint` CHANGE  `affection`  `affection` INT( 2 ) NOT NULL DEFAULT  '0' COMMENT  '情感说明 3:正面 2:中性 1:负面';


-- 2014/1/7
ALTER TABLE  `cloud` CHANGE  `hot_grade`  `hot_grade` INT( 11 ) NOT NULL COMMENT  '热度级别';

-- 2014/1/8
ALTER TABLE  `industry_comment` CHANGE  `ctime`  `ctime` DATETIME NOT NULL;
ALTER TABLE  `dict_province` CHANGE  `aera_id`  `area_id` INT( 11 ) NOT NULL COMMENT  '区域ID';
ALTER TABLE  `article` ADD  `viewpoint` TINYINT( 1 ) NOT NULL DEFAULT  '0' COMMENT  '是否有观点' AFTER  `has_comment`;


-- 2014/1/9
ALTER TABLE  `article` ADD  `article_date` DATE NOT NULL COMMENT  '文章创建时间' AFTER  `article_ctime`;
update `article` set `article_date`=left(`article_ctime`,10);


-- 2014/1/11
ALTER TABLE  `article` ADD  `status` INT NOT NULL DEFAULT  '0' COMMENT  '0(初始）1（有效）2（无效）';
ALTER TABLE  `industry_article` ADD  `status` INT NOT NULL DEFAULT  '0' COMMENT '0(初始）1（有效）2（无效）';
ALTER TABLE  `viewpoint` ADD  `status` INT NOT NULL DEFAULT  '0' COMMENT '0(初始）1（有效）2（无效）';


-- 2014/1/13
ALTER TABLE  `dict_car_brand` ADD  `order_no` INT NOT NULL COMMENT  '排序ID';
ALTER TABLE  `dict_car_serial` ADD  `order_no` INT NOT NULL COMMENT  '排序ID',ADD  `is_follow` TINYINT( 1 ) NOT NULL DEFAULT  '1' COMMENT  '是否关注';

ALTER TABLE  `article` DROP  `article_type`;
ALTER TABLE  `article` DROP  `is_valid`;
ALTER TABLE  `article` ADD  `original_id` BIGINT NOT NULL COMMENT  '原始文章ID' AFTER  `status`;
ALTER TABLE industry_article ADD  `original_id` BIGINT NOT NULL COMMENT  '原始文章ID' AFTER  `status`;

-- 2014/1/13 pm
ALTER TABLE  `dict_car_brand` CHANGE  `img`  `img` CHAR( 100 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT  '图片',CHANGE  `order_no`  `order_no` INT( 11 ) NULL COMMENT  '排充ID';
ALTER TABLE  `article` CHANGE  `provice_id`  `province_id` INT( 4 ) NULL DEFAULT NULL COMMENT  '省份ID';
ALTER TABLE  `dict_car_serial` CHANGE  `disabled`  `is_valid` TINYINT( 11 ) NULL DEFAULT  '0' COMMENT  '是否有效';
ALTER TABLE  `dict_city` CHANGE  `city_name`  `name` CHAR( 100 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;
ALTER TABLE  `viewpoint` ADD  `status` INT NOT NULL DEFAULT  '0' COMMENT  '状态';
ALTER TABLE  `user_info` ADD  `brand_id` INT NOT NULL ,ADD  `serial_id` INT NOT NULL;


-- 2014/1/13 设置索引
ALTER TABLE  `article_viewpoint` ADD INDEX (  `article_id` );
ALTER TABLE  `article_viewpoint` ADD INDEX (  `article_id` );
ALTER TABLE  `article_keyword` ADD INDEX (  `article_id` );
ALTER TABLE  `article_keyword` ADD INDEX (  `keyword_id` );
ALTER TABLE  `industry_cloud` ADD INDEX (  `industry_article_id` );
ALTER TABLE  `industry_cloud` ADD INDEX (  `cloud_id` );
ALTER TABLE  `industry_keyword` ADD INDEX (  `industry_article_id` );
ALTER TABLE  `industry_keyword` ADD INDEX (  `keyword_id` );
ALTER TABLE  `article_viewpoint` DROP INDEX  `article_id_2`;

ALTER TABLE  `article_viewpoint` ADD INDEX (  `viewpoint_id` );
ALTER TABLE  `article_comment` ADD INDEX (  `article_id` );
ALTER TABLE  `dict_car_serial` ADD INDEX (  `brand_id` );
ALTER TABLE  `media_info` ADD INDEX (  `media_type_id` );
ALTER TABLE  `user_info` ADD INDEX (  `serial_id` );
ALTER TABLE  `user_info` ADD INDEX (  `brand_id` );
ALTER TABLE  `industry_article` ADD INDEX (  `media_id` );
ALTER TABLE  `industry_article` ADD INDEX (  `original_id` );
ALTER TABLE  `industry_comment` ADD INDEX (  `industry_article_id` );

-- ALTER TABLE  `industry_article` DROP  `is_valid`
ALTER TABLE  `industry_article` DROP  `is_valid`;


-- 2014/1/16
INSERT INTO  `dict_area` (`id` ,`name` ,`order_num`)VALUES (NULL ,  '未知', NULL);


-- 2014/1/17
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` char(255) NOT NULL,
  `value` char(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

INSERT INTO `sys_config` (`id`, `key`, `value`) VALUES
(1, 'weixinPublicAccount.url', ' http://dev.weixin.otra.cn/weixin'),
(2, 'weixinPublicAccount.token', 'weixinAutoToken'),
(3, 'weixinPublicAccount.appId', 'wxb2bd45977c585979'),
(4, 'weixinPublicAccount.appSecret', 'ef45a0bea2de0ca88a165cfa8f3fa5cb');
