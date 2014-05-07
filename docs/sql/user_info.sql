-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2014 年 01 月 10 日 20:19
-- 服务器版本: 5.1.65
-- PHP 版本: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `ec_intelligence`
--

-- --------------------------------------------------------

--
-- 表的结构 `user_info`
--

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` char(50) NOT NULL COMMENT '登录表',
  `display_name` char(50) NOT NULL COMMENT '显示名称',
  `passwd` char(64) NOT NULL,
  `email` varchar(50) NOT NULL,
  `ctime` datetime NOT NULL,
  `brand_id` int(11) DEFAULT NULL COMMENT '厂牌id',
  `serial_id` int(11) DEFAULT NULL COMMENT '车系id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户表' AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `user_info`
--

INSERT INTO `user_info` (`id`, `login_name`, `display_name`, `passwd`, `email`, `ctime`, `brand_id`, `serial_id`) VALUES
(1, 'user1', 'user1', '10470c3b4b1fed12c3baac014be15fac67c6e815', '450296530@qq.com', '2014-01-23 18:20:37', 1, 1),
(2, 'user2', 'user2namexx', '10470c3b4b1fed12c3baac014be15fac67c6e815', '450296530@qq.com', '2014-01-22 18:20:59', 1, 1),
(5, 'user03', 'user03namexxxx', '10470c3b4b1fed12c3baac014be15fac67c6e815', '450296530@qq.com', '2014-01-07 14:27:15', 1, 1),
(6, 'user04', 'user04namexxx', '10470c3b4b1fed12c3baac014be15fac67c6e815', '450296530@qq.com', '2014-01-07 16:56:31', 1, 1),
(7, 'zhuml', '朱明亮', 'aecd82d7f8f28062c94e9682781155dc1f1f818f', 'zhuml@ecarinfo.com', '2014-01-08 10:09:18', 1, 1);
