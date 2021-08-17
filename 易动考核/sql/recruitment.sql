/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.62 : Database - recruitment
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`recruitment` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `recruitment`;

/*Table structure for table `interview_period` */

DROP TABLE IF EXISTS `interview_period`;

CREATE TABLE `interview_period` (
  `interview_period_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '面试时间段id',
  `recruitment_info_id` int(11) DEFAULT NULL COMMENT '面试信息id',
  `interview_date` date DEFAULT NULL COMMENT '面试日期',
  `interview_time_begin` time DEFAULT NULL COMMENT '面试开始时间',
  `interview_time_end` time DEFAULT NULL COMMENT '面试结束时间',
  `max_number` int(11) DEFAULT NULL COMMENT '排队最大人数',
  `current_number` int(11) DEFAULT NULL COMMENT '现在正在排队的人数',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`interview_period_id`),
  KEY `recruitment_info_id` (`recruitment_info_id`),
  CONSTRAINT `interview_period_ibfk_1` FOREIGN KEY (`recruitment_info_id`) REFERENCES `recruitment_info` (`recruitment_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `interview_period` */

insert  into `interview_period`(`interview_period_id`,`recruitment_info_id`,`interview_date`,`interview_time_begin`,`interview_time_end`,`max_number`,`current_number`,`version`) values (1,1,'2021-08-08','01:32:00','01:33:00',5,0,0),(2,1,'2021-08-09','01:32:00','01:33:00',5,0,0),(3,2,'2021-08-07','15:24:49','15:24:49',5,2,2),(4,2,'2021-08-08','15:24:49','15:24:49',5,1,1);

/*Table structure for table `interview_registration` */

DROP TABLE IF EXISTS `interview_registration`;

CREATE TABLE `interview_registration` (
  `interview_registration_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `interview_period_id` int(11) DEFAULT NULL COMMENT '面试时间段id',
  `regiatration_info_id` int(11) DEFAULT NULL COMMENT '报名信息id',
  PRIMARY KEY (`interview_registration_id`),
  KEY `interview_period_id` (`interview_period_id`),
  KEY `regiatration_info_id` (`regiatration_info_id`),
  CONSTRAINT `interview_registration_ibfk_1` FOREIGN KEY (`interview_period_id`) REFERENCES `interview_period` (`interview_period_id`),
  CONSTRAINT `interview_registration_ibfk_2` FOREIGN KEY (`regiatration_info_id`) REFERENCES `registration_info` (`registration_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `interview_registration` */

insert  into `interview_registration`(`interview_registration_id`,`interview_period_id`,`regiatration_info_id`) values (2,1,1),(3,3,15),(4,3,16),(5,4,22);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '信息id',
  `from_user_id` int(11) DEFAULT NULL COMMENT '发送信息用户id',
  `to_room_id` int(11) DEFAULT NULL COMMENT '接受信息聊天室id',
  `message` varchar(200) DEFAULT NULL COMMENT '信息',
  `is_system` tinyint(1) DEFAULT NULL COMMENT '是否为系统消息',
  PRIMARY KEY (`message_id`),
  KEY `to_room_id` (`to_room_id`),
  KEY `from_user_id` (`from_user_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`to_room_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`message_id`,`from_user_id`,`to_room_id`,`message`,`is_system`) values (38,6,2,'消息测试',0),(39,8,2,'消息测试',0),(40,8,2,'消息测试',0),(41,6,2,'消息测试',0);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名',
  PRIMARY KEY (`permission_id`,`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`permission_id`,`permission_name`) values (3,'user:updateUser'),(4,'room:createprivate'),(5,'room:createinterviewer'),(7,'recruitment:showmyrecruitment'),(8,'recruitment:publish'),(10,'recruitment:interviewerdetail'),(11,'registration:signup'),(12,'registration:tomine'),(13,'registration:showapplicant'),(14,'registration:pass'),(15,'registration:passout'),(16,'registration:tochoosedate'),(17,'registration:choosedate'),(18,'registration:lineup'),(19,'interview:chooseperiod'),(20,'interview:start'),(21,'interview:next'),(23,'registration:cancel');

/*Table structure for table `permission_role` */

DROP TABLE IF EXISTS `permission_role`;

CREATE TABLE `permission_role` (
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `role_id` int(11) DEFAULT NULL COMMENT '身份id',
  KEY `permission_id` (`permission_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `permission_role_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`),
  CONSTRAINT `permission_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission_role` */

insert  into `permission_role`(`permission_id`,`role_id`) values (3,3),(4,3),(4,2),(5,2),(7,2),(8,2),(10,2),(11,3),(12,3),(13,2),(14,2),(15,2),(16,2),(16,3),(17,2),(17,3),(18,3),(19,3),(19,2),(20,2),(21,2),(23,3);

/*Table structure for table `recruitment_info` */

DROP TABLE IF EXISTS `recruitment_info`;

CREATE TABLE `recruitment_info` (
  `recruitment_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '招聘信息id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `occupation` varchar(20) DEFAULT NULL COMMENT '招聘职业',
  `monthly_pay` int(11) DEFAULT NULL COMMENT '月薪',
  `introduction` varchar(150) DEFAULT NULL COMMENT '工作简介',
  `work_city` varchar(20) DEFAULT NULL COMMENT '工作城市',
  `company` varchar(20) DEFAULT NULL COMMENT '公司名称',
  `interview_date_begin` date DEFAULT NULL COMMENT '面试开始日期',
  `interview_date_end` date DEFAULT NULL COMMENT '面试结束日期',
  `interview_time_begin` time DEFAULT NULL COMMENT '一天面试开始时间',
  `interview_time_end` time DEFAULT NULL COMMENT '一天面试结束时间',
  `max_number` int(11) DEFAULT NULL COMMENT '时间段最多面试人数',
  PRIMARY KEY (`recruitment_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `recruitment_info` */

insert  into `recruitment_info`(`recruitment_info_id`,`user_id`,`occupation`,`monthly_pay`,`introduction`,`work_city`,`company`,`interview_date_begin`,`interview_date_end`,`interview_time_begin`,`interview_time_end`,`max_number`) values (1,6,'COPIER',20,'haha','广州','阿里之家','2021-08-08','2021-08-09','01:32:00','01:33:00',5),(2,6,'CASTER',16,'haha','北京','字节跳动','2021-08-07','2021-08-08','15:24:49','15:24:49',5),(3,6,'LANCER',20,'haha','上海','腾讯','2021-08-07','2021-08-09','15:26:56','15:26:56',5),(11,6,'超级复制粘贴师',100,'这是一个简介','宇宙中心','中芯宇宙','2021-08-08','2021-08-09','15:26:56','15:26:56',5);

/*Table structure for table `registration_info` */

DROP TABLE IF EXISTS `registration_info`;

CREATE TABLE `registration_info` (
  `registration_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报名信息id',
  `recruitment_info_id` int(11) DEFAULT NULL COMMENT '招聘信息id',
  `user_id` int(11) DEFAULT NULL COMMENT '报名者用户id',
  `is_registration_pass` tinyint(1) DEFAULT NULL COMMENT '是否通过报名',
  `is_interview_pass` tinyint(1) DEFAULT NULL COMMENT '是否通过面试',
  `interview_date` date DEFAULT NULL COMMENT '报名面试日期',
  `interview_time_begin` time DEFAULT NULL COMMENT '面试时间段开始时间',
  `interview_time_end` time DEFAULT NULL COMMENT '面试时间段结束时间',
  PRIMARY KEY (`registration_info_id`),
  KEY `registration_info_ibfk_1` (`recruitment_info_id`),
  CONSTRAINT `registration_info_ibfk_1` FOREIGN KEY (`recruitment_info_id`) REFERENCES `recruitment_info` (`recruitment_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `registration_info` */

insert  into `registration_info`(`registration_info_id`,`recruitment_info_id`,`user_id`,`is_registration_pass`,`is_interview_pass`,`interview_date`,`interview_time_begin`,`interview_time_end`) values (1,1,6,1,0,'2021-08-08','01:32:00','01:33:00'),(15,2,6,1,NULL,NULL,NULL,NULL),(16,2,7,1,NULL,NULL,NULL,NULL),(17,11,6,1,NULL,NULL,NULL,NULL),(18,11,7,1,NULL,NULL,NULL,NULL),(19,1,7,1,NULL,NULL,NULL,NULL),(20,3,7,1,NULL,NULL,NULL,NULL),(22,2,8,1,NULL,NULL,NULL,NULL);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '身份id',
  `role_name` varchar(20) DEFAULT NULL COMMENT '身份名称',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`) values (1,'admin'),(2,'interviewer'),(3,'applicant');

/*Table structure for table `role_user` */

DROP TABLE IF EXISTS `role_user`;

CREATE TABLE `role_user` (
  `role_id` int(11) DEFAULT NULL COMMENT '身份id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `role_user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `role_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_user` */

insert  into `role_user`(`role_id`,`user_id`) values (2,6),(3,6),(3,8),(1,6);

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天室id',
  `room_name` varchar(20) DEFAULT NULL COMMENT '房间名称',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建聊天室用户id',
  `recruitment_info_id` int(11) DEFAULT NULL COMMENT '聊天室对应的招聘信息',
  `is_private_chat` tinyint(1) DEFAULT NULL COMMENT '是否为私人聊天',
  `received_user_id` int(11) DEFAULT NULL COMMENT '私人聊天另一方用户id',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `room` */

insert  into `room`(`room_id`,`room_name`,`creator_id`,`recruitment_info_id`,`is_private_chat`,`received_user_id`) values (1,'COPIER面试者们',6,1,0,NULL),(2,'CASTER面试者们',6,2,0,NULL),(4,'超级复制粘贴师面试者们',6,11,0,NULL),(5,'LANCER面试者们',6,3,0,NULL),(7,NULL,6,NULL,1,7),(8,NULL,6,NULL,1,8);

/*Table structure for table `room_user` */

DROP TABLE IF EXISTS `room_user`;

CREATE TABLE `room_user` (
  `room_id` int(11) DEFAULT NULL COMMENT '聊天室id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  KEY `room_id` (`room_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `room_user_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `room_user` */

insert  into `room_user`(`room_id`,`user_id`) values (1,6),(1,7),(2,7),(2,6),(4,6),(4,7),(5,7),(5,6),(7,6),(7,7),(2,8),(8,6),(8,8);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `username` varchar(40) DEFAULT NULL COMMENT '用户名',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `gender` varchar(8) DEFAULT NULL COMMENT '性别',
  `city` varchar(20) DEFAULT NULL COMMENT '居住城市',
  `brief_introduction` varchar(40) DEFAULT NULL COMMENT '简短介绍',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `education` varchar(10) DEFAULT NULL COMMENT '学历',
  `profile` varchar(100) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`email`,`username`,`password`,`gender`,`city`,`brief_introduction`,`age`,`education`,`profile`) values (1,NULL,'2','123','女',NULL,NULL,NULL,NULL,NULL),(6,NULL,'6','960b4ab3047fec6c06956602363a0ef0','男','广东','这是一个简介',19,'本科','f6ee2d00-9a11-4f9b-980d-127ffe5dbf55.png'),(7,NULL,'233','e165421110ba03099a1c0393373c5b43','女','惠州','这也是一个简介',18,'本科',NULL),(8,NULL,'666','952d5f79023ff59885b75042b990850c','男','广东','这是一个简介',18,'本科','afee0f54-2244-43ff-bbbb-2e4dbed7ee71.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
