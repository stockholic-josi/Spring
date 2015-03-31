-- MySQL dump 10.13  Distrib 5.6.23, for Win32 (x86)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	5.6.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_master`
--

DROP TABLE IF EXISTS `auth_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_master` (
  `role_cd` varchar(10) NOT NULL COMMENT '권한코드',
  `role_nm` varchar(50) DEFAULT NULL COMMENT '권한설명',
  `role_stp` int(4) unsigned DEFAULT NULL COMMENT '순서',
  PRIMARY KEY (`role_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한마스터';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auth_member`
--

DROP TABLE IF EXISTS `auth_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_member` (
  `user_id` varchar(30) NOT NULL DEFAULT '' COMMENT '유저아이디',
  `passwd` varchar(50) NOT NULL COMMENT '패스워드',
  `user_nm` varchar(50) NOT NULL COMMENT '이름',
  `email` varchar(50) NOT NULL COMMENT '메일',
  `receive` enum('Y','N') DEFAULT 'Y' COMMENT '메일수신여부(Y:수신, N:미수신)',
  `use_yn` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '사용여부(Y:사용,N:비사용)',
  `reg_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_nm` (`user_nm`),
  KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회원정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auth_member_role`
--

DROP TABLE IF EXISTS `auth_member_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_member_role` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_id` varchar(30) DEFAULT NULL COMMENT '유저아이디',
  `role_cd` varchar(10) DEFAULT NULL COMMENT '권한코드',
  `modify_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`seq_no`),
  KEY `FK_auth_role_user_id` (`user_id`),
  KEY `FK_auth_role_role_cd` (`role_cd`),
  CONSTRAINT `FK_auth_role_role_cd` FOREIGN KEY (`role_cd`) REFERENCES `auth_master` (`role_cd`),
  CONSTRAINT `FK_auth_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_member` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='유저별 권한';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ch_room`
--

DROP TABLE IF EXISTS `ch_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ch_room` (
  `seq_no` int(10) unsigned NOT NULL COMMENT '일련번호',
  `name` varchar(400) NOT NULL COMMENT '방이름',
  `passwd` varchar(50) NOT NULL COMMENT '비밀번호',
  `room_type` char(1) DEFAULT NULL COMMENT '방 유형',
  `num` int(10) unsigned NOT NULL,
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='채팅방';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ch_room_user`
--

DROP TABLE IF EXISTS `ch_room_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ch_room_user` (
  `seq_no` int(10) unsigned NOT NULL COMMENT '일련번호',
  `user_id` varchar(30) DEFAULT NULL COMMENT '유저아이디',
  KEY `FK_ch_room_seq_no` (`seq_no`),
  CONSTRAINT `ch_room_user_ibfk_1` FOREIGN KEY (`seq_no`) REFERENCES `ch_room` (`seq_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='채팅방유저';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_analysis`
--

DROP TABLE IF EXISTS `sk_analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_analysis` (
  `code_cd` char(5) NOT NULL COMMENT '코드',
  `code_nm` varchar(100) NOT NULL COMMENT '코드명',
  `refer_cd` char(5) NOT NULL COMMENT '참조코드',
  `level` int(2) unsigned DEFAULT '0' COMMENT '레벨',
  `stp` int(2) unsigned DEFAULT '0' COMMENT '순서',
  `point` int(3) unsigned DEFAULT '0' COMMENT '점수',
  `weight` int(2) unsigned DEFAULT '0' COMMENT '가중치',
  `flag` varchar(10) DEFAULT NULL COMMENT '구분(01:시장,02:종목)',
  PRIMARY KEY (`code_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='기술적분석 항목';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_analysis_data`
--

DROP TABLE IF EXISTS `sk_analysis_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_analysis_data` (
  `seq_no` int(10) unsigned DEFAULT NULL COMMENT 'sk_trade 참조',
  `code_cd` char(5) DEFAULT NULL COMMENT 'sk_analysis 참조',
  `level` int(2) unsigned DEFAULT NULL COMMENT 'sk_analysis 참조',
  KEY `FK_sk_analysis_data_seq_no` (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_board`
--

DROP TABLE IF EXISTS `sk_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_board` (
  `no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `fid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '그룹아이디',
  `lev` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '깊이',
  `stp` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '순서',
  `user_id` varchar(30) NOT NULL COMMENT '아이디',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `content` text COMMENT '내용',
  `html` enum('Y','N') DEFAULT 'Y' COMMENT 'Y:사용, N:미사용',
  `ip` varchar(15) DEFAULT NULL COMMENT '아이피',
  `reg_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  `read_cnt` int(10) unsigned DEFAULT '0' COMMENT '조회수',
  `flag` varchar(20) NOT NULL COMMENT '구분',
  PRIMARY KEY (`no`),
  KEY `IDX_fid` (`fid`) USING BTREE,
  KEY `IDX_flag` (`flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='주식 & 시황정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_board_file`
--

DROP TABLE IF EXISTS `sk_board_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_board_file` (
  `idx` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `no` int(10) unsigned DEFAULT '0',
  `file_name` varchar(50) DEFAULT NULL,
  `file_real_name` varchar(100) DEFAULT NULL,
  `file_ext` varchar(10) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL COMMENT 'F:일반, M:이미지',
  PRIMARY KEY (`idx`),
  KEY `no` (`no`),
  CONSTRAINT `FK_st_board_file_no` FOREIGN KEY (`no`) REFERENCES `sk_board` (`no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=273 DEFAULT CHARSET=utf8 COMMENT='파일목록';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_common_reply`
--

DROP TABLE IF EXISTS `sk_common_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_common_reply` (
  `idx` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `seq_no` int(10) unsigned DEFAULT '0' COMMENT '참조번호',
  `user_id` varchar(30) DEFAULT NULL COMMENT '유저아이디',
  `content` text COMMENT '내용',
  `ip` varchar(15) DEFAULT NULL COMMENT '아이피',
  `flag` varchar(10) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (`idx`),
  KEY `user_id` (`user_id`),
  KEY `flag` (`flag`,`seq_no`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_kospi`
--

DROP TABLE IF EXISTS `sk_kospi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_kospi` (
  `code_cd` char(7) NOT NULL DEFAULT '' COMMENT '코드',
  `code_nm` varchar(40) DEFAULT NULL COMMENT '코드명',
  `price` float(10,2) unsigned DEFAULT '0.00',
  `fluctuate` varchar(10) DEFAULT NULL,
  `flag` char(2) DEFAULT NULL COMMENT '분류(01:코스피,02:코스닥)',
  `reg_date` datetime DEFAULT NULL,
  `rate` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`code_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='종목코드';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_kospi_info`
--

DROP TABLE IF EXISTS `sk_kospi_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_kospi_info` (
  `code_cd` char(7) NOT NULL DEFAULT '' COMMENT '코드',
  `info` text COMMENT '기업정보',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (`code_cd`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_memo`
--

DROP TABLE IF EXISTS `sk_memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_memo` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_id` varchar(30) NOT NULL COMMENT '아이디',
  `content` text COMMENT '내용',
  `reg_date` datetime NOT NULL COMMENT '등록일',
  PRIMARY KEY (`seq_no`),
  KEY `user_id` (`user_id`),
  FULLTEXT KEY `content` (`content`)
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news`
--

DROP TABLE IF EXISTS `sk_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news` (
  `seq_no` int(11) NOT NULL AUTO_INCREMENT,
  `item_group` varchar(20) DEFAULT NULL COMMENT '아이템그룸',
  `item_nm` varchar(20) DEFAULT NULL COMMENT '아이템명',
  `news_nm` varchar(200) DEFAULT NULL COMMENT '뉴스제공사명',
  `news_link` varchar(200) DEFAULT NULL COMMENT '뉴스링크',
  `charset` varchar(20) DEFAULT NULL COMMENT '인코딩',
  `stp` int(5) DEFAULT NULL COMMENT '순서',
  `use_yn` char(1) CHARACTER SET latin1 DEFAULT 'Y' COMMENT '사용여부',
  `flag` varchar(10) DEFAULT NULL COMMENT '뉴스사구분',
  PRIMARY KEY (`seq_no`),
  UNIQUE KEY `item_nm` (`item_nm`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_culture`
--

DROP TABLE IF EXISTS `sk_news_culture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_culture` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_economy`
--

DROP TABLE IF EXISTS `sk_news_economy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_economy` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_entertain`
--

DROP TABLE IF EXISTS `sk_news_entertain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_entertain` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_nation`
--

DROP TABLE IF EXISTS `sk_news_nation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_nation` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_society`
--

DROP TABLE IF EXISTS `sk_news_society`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_society` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_sports`
--

DROP TABLE IF EXISTS `sk_news_sports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_sports` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_news_stock`
--

DROP TABLE IF EXISTS `sk_news_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_news_stock` (
  `ref_no` int(10) NOT NULL DEFAULT '0' COMMENT '참조번호',
  `pub_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '발행일',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `link` varchar(200) DEFAULT NULL COMMENT 'RSS 링크',
  `description` varchar(4000) DEFAULT NULL COMMENT '요약',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  UNIQUE KEY `ref_no` (`ref_no`,`pub_date`),
  FULLTEXT KEY `title` (`title`,`description`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_notice`
--

DROP TABLE IF EXISTS `sk_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_notice` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `title` varchar(400) NOT NULL COMMENT '제목',
  `content` text COMMENT '내용',
  `reg_date` datetime NOT NULL COMMENT '등록일',
  PRIMARY KEY (`seq_no`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_poll`
--

DROP TABLE IF EXISTS `sk_poll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_poll` (
  `seq_no` int(10) unsigned NOT NULL COMMENT '일련번호',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `content` text COMMENT '내용',
  `start_date` datetime DEFAULT NULL COMMENT '시작일',
  `end_date` datetime DEFAULT NULL COMMENT '종료일',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_poll_data`
--

DROP TABLE IF EXISTS `sk_poll_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_poll_data` (
  `idx` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seq_no` int(10) unsigned DEFAULT '0',
  `question` varchar(200) DEFAULT NULL,
  `hit` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`idx`),
  KEY `sk_poll_data_ibfk_1` (`seq_no`),
  CONSTRAINT `sk_poll_data_ibfk_1` FOREIGN KEY (`seq_no`) REFERENCES `sk_poll` (`seq_no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_schedule`
--

DROP TABLE IF EXISTS `sk_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_schedule` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_id` varchar(30) NOT NULL DEFAULT '' COMMENT '아이디',
  `content` text COMMENT '내용',
  `sch_date` datetime NOT NULL COMMENT '스케즐날짜',
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  `trade_no` int(10) unsigned DEFAULT NULL COMMENT '매매번호',
  `trade_type` char(1) CHARACTER SET latin1 DEFAULT NULL COMMENT '매매구분(B:매수, S:매도)',
  PRIMARY KEY (`seq_no`),
  KEY `user_id` (`user_id`,`sch_date`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_tip`
--

DROP TABLE IF EXISTS `sk_tip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_tip` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_id` varchar(30) NOT NULL COMMENT '아이디',
  `title` varchar(200) DEFAULT NULL COMMENT '제목',
  `content` text COMMENT '내용',
  `ip` varchar(15) DEFAULT NULL COMMENT '아이피',
  `flag` varchar(10) DEFAULT NULL COMMENT '구분',
  `reg_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  `read_cnt` int(10) unsigned DEFAULT '0' COMMENT '조회수',
  PRIMARY KEY (`seq_no`),
  KEY `flag` (`flag`),
  FULLTEXT KEY `title` (`title`,`content`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_tip_file`
--

DROP TABLE IF EXISTS `sk_tip_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_tip_file` (
  `idx` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `no` int(10) unsigned DEFAULT '0',
  `file_name` varchar(50) DEFAULT NULL,
  `file_real_name` varchar(100) DEFAULT NULL,
  `file_ext` varchar(10) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL COMMENT 'F:일반, M:이미지',
  PRIMARY KEY (`idx`),
  KEY `seq_no` (`no`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_trade_buy`
--

DROP TABLE IF EXISTS `sk_trade_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_trade_buy` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `ref_no` int(10) unsigned DEFAULT NULL,
  `user_id` varchar(30) NOT NULL DEFAULT '' COMMENT '아이디',
  `code_cd` char(7) NOT NULL DEFAULT '0' COMMENT '종목코드',
  `price` int(11) DEFAULT NULL COMMENT '매매가격',
  `amount` int(5) DEFAULT NULL COMMENT '수량',
  `fee` float DEFAULT '0' COMMENT '수수료',
  `content` text,
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (`seq_no`),
  KEY `Index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8 COMMENT='매수현황';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_trade_fee`
--

DROP TABLE IF EXISTS `sk_trade_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_trade_fee` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `fee_nm` varchar(40) DEFAULT NULL,
  `fee_rate` float DEFAULT NULL,
  `tax_rate` float DEFAULT NULL,
  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_trade_file`
--

DROP TABLE IF EXISTS `sk_trade_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_trade_file` (
  `idx` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `no` int(10) unsigned DEFAULT '0',
  `file_name` varchar(50) DEFAULT NULL,
  `file_real_name` varchar(100) DEFAULT NULL,
  `file_ext` varchar(10) DEFAULT NULL,
  `file_size` varchar(10) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL COMMENT 'F:일반, M:이미지',
  PRIMARY KEY (`idx`),
  KEY `no` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8 COMMENT='파일목록';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sk_trade_sell`
--

DROP TABLE IF EXISTS `sk_trade_sell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sk_trade_sell` (
  `seq_no` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `ref_no` int(10) unsigned DEFAULT NULL,
  `user_id` varchar(30) NOT NULL DEFAULT '' COMMENT '아이디',
  `code_cd` char(7) NOT NULL DEFAULT '0' COMMENT '종목코드',
  `avg_price` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL COMMENT '매매가격',
  `amount` int(5) DEFAULT NULL COMMENT '수량',
  `total_fee` int(11) DEFAULT '0' COMMENT '수수료',
  `total_tax` int(11) DEFAULT '0' COMMENT '세금',
  `content` text,
  `reg_date` datetime DEFAULT NULL COMMENT '등록일',
  PRIMARY KEY (`seq_no`),
  KEY `Index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='매수현황';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-31 10:43:25
