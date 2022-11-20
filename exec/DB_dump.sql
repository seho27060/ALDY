-- --------------------------------------------------------
-- 호스트:                          k7c206.p.ssafy.io
-- 서버 버전:                        10.9.3-MariaDB-1:10.9.3+maria~ubu2204 - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- test 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `test`;

-- 테이블 test.BATCH_JOB_EXECUTION 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime(6) NOT NULL,
  `START_TIME` datetime(6) DEFAULT NULL,
  `END_TIME` datetime(6) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime(6) DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_JOB_EXECUTION:~45 rows (대략적) 내보내기
INSERT INTO `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`, `VERSION`, `JOB_INSTANCE_ID`, `CREATE_TIME`, `START_TIME`, `END_TIME`, `STATUS`, `EXIT_CODE`, `EXIT_MESSAGE`, `LAST_UPDATED`, `JOB_CONFIGURATION_LOCATION`) VALUES
	(1, 2, 1, '2022-11-17 11:07:00.207000', '2022-11-17 11:07:00.377000', '2022-11-17 11:07:01.147000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:07:01.158000', NULL),
	(2, 2, 2, '2022-11-17 11:08:00.172000', '2022-11-17 11:08:00.290000', '2022-11-17 11:08:00.914000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:08:00.926000', NULL),
	(3, 2, 3, '2022-11-17 11:09:00.176000', '2022-11-17 11:09:00.287000', '2022-11-17 11:09:00.883000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:09:00.898000', NULL),
	(4, 2, 4, '2022-11-17 11:10:00.177000', '2022-11-17 11:10:00.284000', '2022-11-17 11:10:00.926000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:10:00.938000', NULL),
	(5, 2, 5, '2022-11-17 11:11:00.174000', '2022-11-17 11:11:00.291000', '2022-11-17 11:11:00.918000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:11:00.927000', NULL),
	(6, 2, 6, '2022-11-17 11:12:00.183000', '2022-11-17 11:12:00.303000', '2022-11-17 11:12:00.987000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:12:00.998000', NULL),
	(7, 2, 7, '2022-11-17 11:13:00.168000', '2022-11-17 11:13:00.281000', '2022-11-17 11:13:00.919000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:13:00.929000', NULL),
	(8, 2, 8, '2022-11-17 11:14:00.199000', '2022-11-17 11:14:00.345000', '2022-11-17 11:14:00.985000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:14:00.995000', NULL),
	(9, 2, 9, '2022-11-17 11:15:00.174000', '2022-11-17 11:15:00.287000', '2022-11-17 11:15:00.911000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:15:00.922000', NULL),
	(10, 2, 10, '2022-11-17 11:16:00.154000', '2022-11-17 11:16:00.265000', '2022-11-17 11:16:00.958000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:16:00.968000', NULL),
	(11, 2, 11, '2022-11-17 11:17:00.216000', '2022-11-17 11:17:00.334000', '2022-11-17 11:17:01.092000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:17:01.104000', NULL),
	(12, 2, 12, '2022-11-17 11:18:00.185000', '2022-11-17 11:18:00.305000', '2022-11-17 11:18:01.072000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:18:01.084000', NULL),
	(13, 2, 13, '2022-11-17 11:19:00.172000', '2022-11-17 11:19:00.286000', '2022-11-17 11:19:01.015000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:19:01.027000', NULL),
	(14, 2, 14, '2022-11-17 11:20:00.188000', '2022-11-17 11:20:00.309000', '2022-11-17 11:20:01.274000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:20:01.287000', NULL),
	(15, 2, 15, '2022-11-17 11:21:00.199000', '2022-11-17 11:21:00.332000', '2022-11-17 11:21:01.359000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:21:01.369000', NULL),
	(16, 2, 16, '2022-11-17 11:22:00.199000', '2022-11-17 11:22:00.316000', '2022-11-17 11:22:01.394000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:22:01.404000', NULL),
	(17, 2, 17, '2022-11-17 11:23:00.187000', '2022-11-17 11:23:00.309000', '2022-11-17 11:23:01.524000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:23:01.533000', NULL),
	(18, 2, 18, '2022-11-17 11:24:00.195000', '2022-11-17 11:24:00.321000', '2022-11-17 11:24:01.540000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 11:24:01.561000', NULL),
	(19, 2, 19, '2022-11-17 13:23:00.226000', '2022-11-17 13:23:00.398000', '2022-11-17 13:23:01.186000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:23:01.198000', NULL),
	(20, 2, 20, '2022-11-17 13:24:00.207000', '2022-11-17 13:24:00.329000', '2022-11-17 13:24:01.026000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:24:01.038000', NULL),
	(21, 2, 21, '2022-11-17 13:25:00.196000', '2022-11-17 13:25:00.319000', '2022-11-17 13:25:01.019000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:25:01.033000', NULL),
	(22, 2, 22, '2022-11-17 13:52:00.265000', '2022-11-17 13:52:00.415000', '2022-11-17 13:52:01.874000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:52:01.885000', NULL),
	(23, 2, 23, '2022-11-17 13:54:00.234000', '2022-11-17 13:54:00.449000', '2022-11-17 13:54:01.927000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:54:01.938000', NULL),
	(24, 2, 24, '2022-11-17 13:55:00.276000', '2022-11-17 13:55:00.437000', '2022-11-17 13:55:01.897000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:55:01.909000', NULL),
	(25, 2, 25, '2022-11-17 13:56:00.206000', '2022-11-17 13:56:00.327000', '2022-11-17 13:56:01.663000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:56:01.674000', NULL),
	(26, 2, 26, '2022-11-17 13:57:00.225000', '2022-11-17 13:57:00.375000', '2022-11-17 13:57:01.452000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:57:01.463000', NULL),
	(27, 2, 27, '2022-11-17 13:58:00.208000', '2022-11-17 13:58:00.328000', '2022-11-17 13:58:01.678000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:58:01.689000', NULL),
	(28, 2, 28, '2022-11-17 13:59:00.172000', '2022-11-17 13:59:00.283000', '2022-11-17 13:59:01.359000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 13:59:01.370000', NULL),
	(29, 2, 29, '2022-11-17 14:00:00.171000', '2022-11-17 14:00:00.292000', '2022-11-17 14:00:01.428000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:00:01.437000', NULL),
	(30, 2, 30, '2022-11-17 14:01:00.196000', '2022-11-17 14:01:00.323000', '2022-11-17 14:01:01.631000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:01:01.645000', NULL),
	(31, 2, 31, '2022-11-17 14:02:00.182000', '2022-11-17 14:02:00.309000', '2022-11-17 14:02:01.599000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:02:01.610000', NULL),
	(32, 2, 32, '2022-11-17 14:03:00.183000', '2022-11-17 14:03:00.310000', '2022-11-17 14:03:01.470000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:03:01.480000', NULL),
	(33, 2, 33, '2022-11-17 14:04:00.180000', '2022-11-17 14:04:00.289000', '2022-11-17 14:04:01.298000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:04:01.308000', NULL),
	(34, 2, 34, '2022-11-17 14:05:00.156000', '2022-11-17 14:05:00.262000', '2022-11-17 14:05:01.226000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:05:01.239000', NULL),
	(35, 2, 35, '2022-11-17 14:06:00.159000', '2022-11-17 14:06:00.266000', '2022-11-17 14:06:01.229000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:06:01.238000', NULL),
	(36, 2, 36, '2022-11-17 14:07:00.172000', '2022-11-17 14:07:00.279000', '2022-11-17 14:07:01.232000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:07:01.244000', NULL),
	(37, 2, 37, '2022-11-17 14:08:00.234000', '2022-11-17 14:08:00.365000', '2022-11-17 14:08:01.491000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:08:01.504000', NULL),
	(38, 2, 38, '2022-11-17 14:09:00.179000', '2022-11-17 14:09:00.289000', '2022-11-17 14:09:01.230000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:09:01.240000', NULL),
	(39, 2, 39, '2022-11-17 14:10:00.193000', '2022-11-17 14:10:00.302000', '2022-11-17 14:10:01.345000', 'COMPLETED', 'COMPLETED', '', '2022-11-17 14:10:01.360000', NULL),
	(40, 2, 40, '2022-11-18 00:00:00.024000', '2022-11-18 00:00:00.069000', '2022-11-18 00:00:00.228000', 'COMPLETED', 'COMPLETED', '', '2022-11-18 00:00:00.229000', NULL),
	(41, 2, 41, '2022-11-18 00:00:00.243000', '2022-11-18 00:00:00.253000', '2022-11-18 00:00:00.710000', 'COMPLETED', 'COMPLETED', '', '2022-11-18 00:00:00.711000', NULL),
	(42, 2, 42, '2022-11-19 00:00:00.011000', '2022-11-19 00:00:00.018000', '2022-11-19 00:00:00.110000', 'COMPLETED', 'COMPLETED', '', '2022-11-19 00:00:00.111000', NULL),
	(43, 2, 43, '2022-11-19 00:00:00.123000', '2022-11-19 00:00:00.131000', '2022-11-19 00:00:00.434000', 'COMPLETED', 'COMPLETED', '', '2022-11-19 00:00:00.435000', NULL),
	(44, 2, 44, '2022-11-20 00:00:00.021000', '2022-11-20 00:00:00.067000', '2022-11-20 00:00:00.214000', 'COMPLETED', 'COMPLETED', '', '2022-11-20 00:00:00.215000', NULL),
	(45, 2, 45, '2022-11-20 00:00:00.228000', '2022-11-20 00:00:00.239000', '2022-11-20 00:00:00.512000', 'COMPLETED', 'COMPLETED', '', '2022-11-20 00:00:00.513000', NULL);

-- 테이블 test.BATCH_JOB_EXECUTION_CONTEXT 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_JOB_EXECUTION_CONTEXT:~45 rows (대략적) 내보내기
INSERT INTO `BATCH_JOB_EXECUTION_CONTEXT` (`JOB_EXECUTION_ID`, `SHORT_CONTEXT`, `SERIALIZED_CONTEXT`) VALUES
	(1, '{"@class":"java.util.HashMap"}', NULL),
	(2, '{"@class":"java.util.HashMap"}', NULL),
	(3, '{"@class":"java.util.HashMap"}', NULL),
	(4, '{"@class":"java.util.HashMap"}', NULL),
	(5, '{"@class":"java.util.HashMap"}', NULL),
	(6, '{"@class":"java.util.HashMap"}', NULL),
	(7, '{"@class":"java.util.HashMap"}', NULL),
	(8, '{"@class":"java.util.HashMap"}', NULL),
	(9, '{"@class":"java.util.HashMap"}', NULL),
	(10, '{"@class":"java.util.HashMap"}', NULL),
	(11, '{"@class":"java.util.HashMap"}', NULL),
	(12, '{"@class":"java.util.HashMap"}', NULL),
	(13, '{"@class":"java.util.HashMap"}', NULL),
	(14, '{"@class":"java.util.HashMap"}', NULL),
	(15, '{"@class":"java.util.HashMap"}', NULL),
	(16, '{"@class":"java.util.HashMap"}', NULL),
	(17, '{"@class":"java.util.HashMap"}', NULL),
	(18, '{"@class":"java.util.HashMap"}', NULL),
	(19, '{"@class":"java.util.HashMap"}', NULL),
	(20, '{"@class":"java.util.HashMap"}', NULL),
	(21, '{"@class":"java.util.HashMap"}', NULL),
	(22, '{"@class":"java.util.HashMap"}', NULL),
	(23, '{"@class":"java.util.HashMap"}', NULL),
	(24, '{"@class":"java.util.HashMap"}', NULL),
	(25, '{"@class":"java.util.HashMap"}', NULL),
	(26, '{"@class":"java.util.HashMap"}', NULL),
	(27, '{"@class":"java.util.HashMap"}', NULL),
	(28, '{"@class":"java.util.HashMap"}', NULL),
	(29, '{"@class":"java.util.HashMap"}', NULL),
	(30, '{"@class":"java.util.HashMap"}', NULL),
	(31, '{"@class":"java.util.HashMap"}', NULL),
	(32, '{"@class":"java.util.HashMap"}', NULL),
	(33, '{"@class":"java.util.HashMap"}', NULL),
	(34, '{"@class":"java.util.HashMap"}', NULL),
	(35, '{"@class":"java.util.HashMap"}', NULL),
	(36, '{"@class":"java.util.HashMap"}', NULL),
	(37, '{"@class":"java.util.HashMap"}', NULL),
	(38, '{"@class":"java.util.HashMap"}', NULL),
	(39, '{"@class":"java.util.HashMap"}', NULL),
	(40, '{"@class":"java.util.HashMap"}', NULL),
	(41, '{"@class":"java.util.HashMap"}', NULL),
	(42, '{"@class":"java.util.HashMap"}', NULL),
	(43, '{"@class":"java.util.HashMap"}', NULL),
	(44, '{"@class":"java.util.HashMap"}', NULL),
	(45, '{"@class":"java.util.HashMap"}', NULL);

-- 테이블 test.BATCH_JOB_EXECUTION_PARAMS 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION_PARAMS` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime(6) DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL,
  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_JOB_EXECUTION_PARAMS:~45 rows (대략적) 내보내기
INSERT INTO `BATCH_JOB_EXECUTION_PARAMS` (`JOB_EXECUTION_ID`, `TYPE_CD`, `KEY_NAME`, `STRING_VAL`, `DATE_VAL`, `LONG_VAL`, `DOUBLE_VAL`, `IDENTIFYING`) VALUES
	(1, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668650820011, 0, 'Y'),
	(2, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668650880001, 0, 'Y'),
	(3, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668650940016, 0, 'Y'),
	(4, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651000016, 0, 'Y'),
	(5, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651060014, 0, 'Y'),
	(6, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651120007, 0, 'Y'),
	(7, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651180013, 0, 'Y'),
	(8, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651240008, 0, 'Y'),
	(9, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651300008, 0, 'Y'),
	(10, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651360006, 0, 'Y'),
	(11, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651420005, 0, 'Y'),
	(12, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651480010, 0, 'Y'),
	(13, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651540002, 0, 'Y'),
	(14, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651600012, 0, 'Y'),
	(15, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651660014, 0, 'Y'),
	(16, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651720009, 0, 'Y'),
	(17, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651780003, 0, 'Y'),
	(18, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668651840014, 0, 'Y'),
	(19, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668658980002, 0, 'Y'),
	(20, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668659040011, 0, 'Y'),
	(21, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668659100009, 0, 'Y'),
	(22, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668660720013, 0, 'Y'),
	(23, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668660840006, 0, 'Y'),
	(24, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668660900015, 0, 'Y'),
	(25, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668660960011, 0, 'Y'),
	(26, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661020002, 0, 'Y'),
	(27, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661080014, 0, 'Y'),
	(28, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661140012, 0, 'Y'),
	(29, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661200009, 0, 'Y'),
	(30, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661260007, 0, 'Y'),
	(31, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661320004, 0, 'Y'),
	(32, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661380004, 0, 'Y'),
	(33, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661440009, 0, 'Y'),
	(34, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661500004, 0, 'Y'),
	(35, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661560007, 0, 'Y'),
	(36, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661620007, 0, 'Y'),
	(37, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661680001, 0, 'Y'),
	(38, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661740014, 0, 'Y'),
	(39, 'LONG', 'time', '', '1970-01-01 09:00:00.000000', 1668661800015, 0, 'Y'),
	(40, 'LONG', 'time', '', '1970-01-01 00:00:00.000000', 1668729600001, 0, 'Y'),
	(41, 'LONG', 'time', '', '1970-01-01 00:00:00.000000', 1668729600234, 0, 'Y'),
	(42, 'LONG', 'time', '', '1970-01-01 00:00:00.000000', 1668816000000, 0, 'Y'),
	(43, 'LONG', 'time', '', '1970-01-01 00:00:00.000000', 1668816000115, 0, 'Y'),
	(44, 'LONG', 'time', '', '1970-01-01 00:00:00.000000', 1668902400000, 0, 'Y'),
	(45, 'LONG', 'time', '', '1970-01-01 00:00:00.000000', 1668902400219, 0, 'Y');

-- 테이블 test.BATCH_JOB_EXECUTION_SEQ 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_JOB_EXECUTION_SEQ:~1 rows (대략적) 내보내기
INSERT INTO `BATCH_JOB_EXECUTION_SEQ` (`ID`, `UNIQUE_KEY`) VALUES
	(45, '0');

-- 테이블 test.BATCH_JOB_INSTANCE 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_JOB_INSTANCE:~45 rows (대략적) 내보내기
INSERT INTO `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`, `VERSION`, `JOB_NAME`, `JOB_KEY`) VALUES
	(1, 0, 'countAlertJob', '38ee7ea5cfd4539283963959111d3621'),
	(2, 0, 'countAlertJob', '12399acb06e341042134e5a902d364bf'),
	(3, 0, 'countAlertJob', '6b035f9c744de8e3438bcbf383c77b82'),
	(4, 0, 'countAlertJob', 'd0ef9526908218bcf9a1a19e5d00d961'),
	(5, 0, 'countAlertJob', 'bda5cee3417ebdc848c1dad21f0bf659'),
	(6, 0, 'countAlertJob', '15eb2957340945b1a841a5809045a41e'),
	(7, 0, 'countAlertJob', 'd979feb4790697bb4a0a368f80615b50'),
	(8, 0, 'countAlertJob', '71695cd8c319e7d1de1539ef26aa9f35'),
	(9, 0, 'countAlertJob', '17cafe87be1bc643277ef6a1a0c1daf5'),
	(10, 0, 'countAlertJob', '5d2aada30d43be0eda2b2f2110adbfa2'),
	(11, 0, 'countAlertJob', 'd2f6a7b376429807e6fa77925e0967c0'),
	(12, 0, 'countAlertJob', 'f24a1fe9798d3f30d89d0696a90e4620'),
	(13, 0, 'countAlertJob', '7c5ceea962089e51c87d51bb01871c16'),
	(14, 0, 'countAlertJob', '6de184fa9ed252c8ea850c756e5bbb1a'),
	(15, 0, 'countAlertJob', '5813eaa83b1fa14616289f6e20339420'),
	(16, 0, 'countAlertJob', 'f83182652c3cd863b340862eb51e4de6'),
	(17, 0, 'countAlertJob', 'a6fa757e724c8b1289e85904f8a51496'),
	(18, 0, 'countAlertJob', '23f07f6a949e581db0fcbc97b6e26a58'),
	(19, 0, 'countAlertJob', 'bbef1e570851ee95b999acc4e6f46dab'),
	(20, 0, 'countAlertJob', '26ae894fd120979c2993d1526efd2106'),
	(21, 0, 'countAlertJob', '09e1e7d9ced9ab146cdb0a817574e424'),
	(22, 0, 'countAlertJob', '69149d77974668b4f448317bcebb031d'),
	(23, 0, 'countAlertJob', 'adaa095b56df49747fc810ad28708936'),
	(24, 0, 'countAlertJob', 'f806c60dd6286eefe2e759ed87e8582c'),
	(25, 0, 'countAlertJob', '073a9d5f63bd4739250c87bb2105f0e5'),
	(26, 0, 'countAlertJob', '768d19935b639178af9eee13b356c60c'),
	(27, 0, 'countAlertJob', '864675c315a5135a89de9e3dccabb934'),
	(28, 0, 'countAlertJob', '0f4abaa5f991da71aa64ad5c8d7bc282'),
	(29, 0, 'countAlertJob', 'd35746d4c2b2a44652426e94b37adf9e'),
	(30, 0, 'countAlertJob', 'c346952f9e7b48505172be92d80975af'),
	(31, 0, 'countAlertJob', '1f3d27a9b4721f19dc784da48f4aeb60'),
	(32, 0, 'countAlertJob', 'a352c3564d304e1239ee60e64eb37413'),
	(33, 0, 'countAlertJob', '2bf5f6e4e9b4e3035c714b66bb3ed095'),
	(34, 0, 'countAlertJob', '0f02d0787ba3a30665f1850b40c7b84b'),
	(35, 0, 'countAlertJob', '3a169ea92de68d4085715fc30ec7f61f'),
	(36, 0, 'countAlertJob', 'ee2e83c24a89c4717766377da155099c'),
	(37, 0, 'countAlertJob', 'd66bef05446da2d2e5f6f4736c0effbf'),
	(38, 0, 'countAlertJob', 'bb61c54c2d0148dec79912f6c2641cbc'),
	(39, 0, 'countAlertJob', '152ded2e9f24e73baeea93a0027e7c15'),
	(40, 0, 'checkLevelJob', '478aad2b436d80bc2ddbd01949d67269'),
	(41, 0, 'countAlertJob', 'b18a0d64c49d090b1f2e20801264555f'),
	(42, 0, 'checkLevelJob', 'b9473a5e4d4ac6cfb466819e6698d8d9'),
	(43, 0, 'countAlertJob', 'b56d2ead02affb887146158c70c3df12'),
	(44, 0, 'checkLevelJob', '3005b5e0e2159fc5de7707612801bde5'),
	(45, 0, 'countAlertJob', 'd266ef9d1abe7b2d9ae3b4020252b7bd');

-- 테이블 test.BATCH_JOB_SEQ 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_JOB_SEQ:~1 rows (대략적) 내보내기
INSERT INTO `BATCH_JOB_SEQ` (`ID`, `UNIQUE_KEY`) VALUES
	(45, '0');

-- 테이블 test.BATCH_STEP_EXECUTION 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_STEP_EXECUTION` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime(6) NOT NULL,
  `END_TIME` datetime(6) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_STEP_EXECUTION:~45 rows (대략적) 내보내기
INSERT INTO `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`, `VERSION`, `STEP_NAME`, `JOB_EXECUTION_ID`, `START_TIME`, `END_TIME`, `STATUS`, `COMMIT_COUNT`, `READ_COUNT`, `FILTER_COUNT`, `WRITE_COUNT`, `READ_SKIP_COUNT`, `WRITE_SKIP_COUNT`, `PROCESS_SKIP_COUNT`, `ROLLBACK_COUNT`, `EXIT_CODE`, `EXIT_MESSAGE`, `LAST_UPDATED`) VALUES
	(1, 3, 'countAlertStep', 1, '2022-11-17 11:07:00.654000', '2022-11-17 11:07:01.031000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:07:01.042000'),
	(2, 3, 'countAlertStep', 2, '2022-11-17 11:08:00.553000', '2022-11-17 11:08:00.813000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:08:00.822000'),
	(3, 3, 'countAlertStep', 3, '2022-11-17 11:09:00.534000', '2022-11-17 11:09:00.791000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:09:00.800000'),
	(4, 3, 'countAlertStep', 4, '2022-11-17 11:10:00.536000', '2022-11-17 11:10:00.794000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:10:00.809000'),
	(5, 3, 'countAlertStep', 5, '2022-11-17 11:11:00.546000', '2022-11-17 11:11:00.804000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:11:00.816000'),
	(6, 3, 'countAlertStep', 6, '2022-11-17 11:12:00.593000', '2022-11-17 11:12:00.872000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:12:00.885000'),
	(7, 3, 'countAlertStep', 7, '2022-11-17 11:13:00.544000', '2022-11-17 11:13:00.815000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:13:00.831000'),
	(8, 3, 'countAlertStep', 8, '2022-11-17 11:14:00.611000', '2022-11-17 11:14:00.875000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:14:00.886000'),
	(9, 3, 'countAlertStep', 9, '2022-11-17 11:15:00.545000', '2022-11-17 11:15:00.809000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:15:00.821000'),
	(10, 3, 'countAlertStep', 10, '2022-11-17 11:16:00.530000', '2022-11-17 11:16:00.860000', 'COMPLETED', 1, 1, 0, 1, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:16:00.870000'),
	(11, 3, 'countAlertStep', 11, '2022-11-17 11:17:00.635000', '2022-11-17 11:17:00.974000', 'COMPLETED', 1, 2, 0, 2, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:17:00.986000'),
	(12, 3, 'countAlertStep', 12, '2022-11-17 11:18:00.602000', '2022-11-17 11:18:00.962000', 'COMPLETED', 1, 2, 0, 2, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:18:00.974000'),
	(13, 3, 'countAlertStep', 13, '2022-11-17 11:19:00.556000', '2022-11-17 11:19:00.893000', 'COMPLETED', 1, 3, 0, 3, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:19:00.904000'),
	(14, 4, 'countAlertStep', 14, '2022-11-17 11:20:00.591000', '2022-11-17 11:20:01.162000', 'COMPLETED', 2, 5, 0, 5, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:20:01.175000'),
	(15, 4, 'countAlertStep', 15, '2022-11-17 11:21:00.615000', '2022-11-17 11:21:01.244000', 'COMPLETED', 2, 8, 0, 8, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:21:01.256000'),
	(16, 4, 'countAlertStep', 16, '2022-11-17 11:22:00.610000', '2022-11-17 11:22:01.289000', 'COMPLETED', 2, 8, 0, 8, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:22:01.301000'),
	(17, 5, 'countAlertStep', 17, '2022-11-17 11:23:00.607000', '2022-11-17 11:23:01.418000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:23:01.428000'),
	(18, 5, 'countAlertStep', 18, '2022-11-17 11:24:00.622000', '2022-11-17 11:24:01.421000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 11:24:01.434000'),
	(19, 3, 'countAlertStep', 19, '2022-11-17 13:23:00.703000', '2022-11-17 13:23:01.078000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:23:01.091000'),
	(20, 3, 'countAlertStep', 20, '2022-11-17 13:24:00.626000', '2022-11-17 13:24:00.915000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:24:00.928000'),
	(21, 3, 'countAlertStep', 21, '2022-11-17 13:25:00.624000', '2022-11-17 13:25:00.914000', 'COMPLETED', 1, 0, 0, 0, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:25:00.924000'),
	(22, 5, 'countAlertStep', 22, '2022-11-17 13:52:00.686000', '2022-11-17 13:52:01.749000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:52:01.762000'),
	(23, 5, 'countAlertStep', 23, '2022-11-17 13:54:00.802000', '2022-11-17 13:54:01.817000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:54:01.829000'),
	(24, 5, 'countAlertStep', 24, '2022-11-17 13:55:00.772000', '2022-11-17 13:55:01.769000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:55:01.779000'),
	(25, 5, 'countAlertStep', 25, '2022-11-17 13:56:00.653000', '2022-11-17 13:56:01.545000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:56:01.559000'),
	(26, 5, 'countAlertStep', 26, '2022-11-17 13:57:00.639000', '2022-11-17 13:57:01.358000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:57:01.367000'),
	(27, 5, 'countAlertStep', 27, '2022-11-17 13:58:00.688000', '2022-11-17 13:58:01.568000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:58:01.579000'),
	(28, 5, 'countAlertStep', 28, '2022-11-17 13:59:00.533000', '2022-11-17 13:59:01.259000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 13:59:01.269000'),
	(29, 5, 'countAlertStep', 29, '2022-11-17 14:00:00.569000', '2022-11-17 14:00:01.331000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:00:01.342000'),
	(30, 5, 'countAlertStep', 30, '2022-11-17 14:01:00.623000', '2022-11-17 14:01:01.522000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:01:01.533000'),
	(31, 5, 'countAlertStep', 31, '2022-11-17 14:02:00.626000', '2022-11-17 14:02:01.484000', 'COMPLETED', 3, 10, 0, 10, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:02:01.497000'),
	(32, 5, 'countAlertStep', 32, '2022-11-17 14:03:00.581000', '2022-11-17 14:03:01.370000', 'COMPLETED', 3, 11, 0, 11, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:03:01.379000'),
	(33, 4, 'countAlertStep', 33, '2022-11-17 14:04:00.561000', '2022-11-17 14:04:01.196000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:04:01.207000'),
	(34, 4, 'countAlertStep', 34, '2022-11-17 14:05:00.507000', '2022-11-17 14:05:01.130000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:05:01.140000'),
	(35, 4, 'countAlertStep', 35, '2022-11-17 14:06:00.531000', '2022-11-17 14:06:01.137000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:06:01.148000'),
	(36, 4, 'countAlertStep', 36, '2022-11-17 14:07:00.541000', '2022-11-17 14:07:01.127000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:07:01.136000'),
	(37, 4, 'countAlertStep', 37, '2022-11-17 14:08:00.631000', '2022-11-17 14:08:01.347000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:08:01.358000'),
	(38, 4, 'countAlertStep', 38, '2022-11-17 14:09:00.548000', '2022-11-17 14:09:01.127000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:09:01.136000'),
	(39, 4, 'countAlertStep', 39, '2022-11-17 14:10:00.578000', '2022-11-17 14:10:01.238000', 'COMPLETED', 2, 9, 0, 9, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-17 14:10:01.249000'),
	(40, 5, 'checkLevelStep', 40, '2022-11-18 00:00:00.095000', '2022-11-18 00:00:00.213000', 'COMPLETED', 3, 14, 0, 14, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-18 00:00:00.215000'),
	(41, 5, 'countAlertStep', 41, '2022-11-18 00:00:00.273000', '2022-11-18 00:00:00.703000', 'COMPLETED', 3, 14, 0, 14, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-18 00:00:00.704000'),
	(42, 6, 'checkLevelStep', 42, '2022-11-19 00:00:00.038000', '2022-11-19 00:00:00.104000', 'COMPLETED', 4, 18, 0, 18, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-19 00:00:00.105000'),
	(43, 6, 'countAlertStep', 43, '2022-11-19 00:00:00.148000', '2022-11-19 00:00:00.426000', 'COMPLETED', 4, 18, 0, 18, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-19 00:00:00.427000'),
	(44, 6, 'checkLevelStep', 44, '2022-11-20 00:00:00.088000', '2022-11-20 00:00:00.207000', 'COMPLETED', 4, 19, 0, 19, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-20 00:00:00.208000'),
	(45, 6, 'countAlertStep', 45, '2022-11-20 00:00:00.257000', '2022-11-20 00:00:00.506000', 'COMPLETED', 4, 19, 0, 19, 0, 0, 0, 0, 'COMPLETED', '', '2022-11-20 00:00:00.507000');

-- 테이블 test.BATCH_STEP_EXECUTION_CONTEXT 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_STEP_EXECUTION_CONTEXT:~45 rows (대략적) 내보내기
INSERT INTO `BATCH_STEP_EXECUTION_CONTEXT` (`STEP_EXECUTION_ID`, `SHORT_CONTEXT`, `SERIALIZED_CONTEXT`) VALUES
	(1, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(2, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(3, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(4, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(5, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(6, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(7, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(8, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(9, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(10, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":2,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(11, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":3,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(12, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":3,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(13, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":4,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(14, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":6,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(15, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":9,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(16, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":9,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(17, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(18, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(19, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(20, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(21, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":1,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(22, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(23, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(24, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(25, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(26, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(27, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(28, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(29, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(30, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(31, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":11,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(32, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":12,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(33, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(34, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(35, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(36, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(37, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(38, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(39, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":10,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(40, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","checkLevelItemReader.read.count":15,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(41, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":15,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(42, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","checkLevelItemReader.read.count":19,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(43, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":19,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(44, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","checkLevelItemReader.read.count":20,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL),
	(45, '{"@class":"java.util.HashMap","batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet","countAlertItemReader.read.count":20,"batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}', NULL);

-- 테이블 test.BATCH_STEP_EXECUTION_SEQ 구조 내보내기
CREATE TABLE IF NOT EXISTS `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.BATCH_STEP_EXECUTION_SEQ:~1 rows (대략적) 내보내기
INSERT INTO `BATCH_STEP_EXECUTION_SEQ` (`ID`, `UNIQUE_KEY`) VALUES
	(45, '0');

-- 테이블 test.calendar 구조 내보내기
CREATE TABLE IF NOT EXISTS `calendar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calendar_month` int(11) NOT NULL,
  `calendar_year` int(11) NOT NULL,
  `study_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKca6ink7109osvmwjrvbcb0861` (`study_id`),
  CONSTRAINT `FKca6ink7109osvmwjrvbcb0861` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.calendar:~9 rows (대략적) 내보내기
INSERT INTO `calendar` (`id`, `calendar_month`, `calendar_year`, `study_id`) VALUES
	(1, 11, 2022, 1),
	(2, 11, 2022, 4),
	(4, 11, 2022, 14),
	(5, 11, 2022, 13),
	(6, 12, 2022, 13),
	(8, 11, 2022, 18),
	(9, 11, 2022, 20),
	(10, 11, 2022, 16),
	(11, 11, 2022, 24);

-- 테이블 test.code 구조 내보내기
CREATE TABLE IF NOT EXISTS `code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(10000) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `process` int(11) NOT NULL,
  `problem_id` bigint(20) DEFAULT NULL,
  `study_id` bigint(20) DEFAULT NULL,
  `writer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcjdrir5h5u9eu25unh4ddkoas` (`problem_id`),
  KEY `FK8qn2bs9m13c36xb2v6xyootfx` (`study_id`),
  KEY `FKbe7dhknmdjcay3065c3pwr5wc` (`writer_id`),
  CONSTRAINT `FK8qn2bs9m13c36xb2v6xyootfx` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`),
  CONSTRAINT `FKbe7dhknmdjcay3065c3pwr5wc` FOREIGN KEY (`writer_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKcjdrir5h5u9eu25unh4ddkoas` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.code:~76 rows (대략적) 내보내기
INSERT INTO `code` (`id`, `code`, `created_date`, `process`, `problem_id`, `study_id`, `writer_id`) VALUES
	(2, 'g=', '2022-11-17 05:50:46.405669', 1, 13, 4, 3),
	(3, 'g=\r\n#kn', '2022-11-17 05:51:01.633798', 2, 13, 4, 3),
	(4, 'ㅇㅀㄴㅀ', '2022-11-17 06:34:21.647689', 1, 16, 4, 3),
	(5, 'ㅇㅀㄴㅀ\r\n### 주석석\r\n# 주서기서기기', '2022-11-17 06:34:38.399320', 2, 16, 4, 3),
	(6, 'e', '2022-11-17 07:01:14.655520', 1, 11, 13, 4),
	(7, 'eee', '2022-11-17 07:01:19.270420', 2, 11, 13, 4),
	(8, '# 복붙', '2022-11-17 07:02:32.477130', 1, 23, 4, 3),
	(9, '\r\n# 주석\r\n\r\n# 주석\r\n\r\n# 이곳을 리뷰해주세요!', '2022-11-17 07:02:45.993336', 2, 23, 4, 3),
	(10, NULL, '2022-11-17 07:04:56.946313', 3, 11, 13, 4),
	(11, 'g=\r\n#kn\r\n\r\n## asdfasdf ', '2022-11-17 07:06:27.826051', 3, 13, 4, 3),
	(12, 'sdf', '2022-11-17 07:07:38.824856', 1, 11, 13, 2),
	(13, 'ㅁsdf11', '2022-11-17 07:07:45.257108', 2, 11, 13, 2),
	(14, '12341234', '2022-11-17 07:52:20.901771', 1, 12, 13, 4),
	(15, '1234   1234 1234', '2022-11-17 07:52:37.550225', 2, 12, 13, 4),
	(16, 'eee', '2022-11-17 07:52:59.689083', 3, 12, 13, 4),
	(17, '1232134', '2022-11-17 08:05:07.163021', 1, 12, 13, 2),
	(18, '1232134233', '2022-11-17 08:05:12.697159', 2, 12, 13, 2),
	(21, '\r\n# 주석\r\n\r\n# 주석\r\n\r\n# 이곳을 리뷰해주세요!\r\n\r\n#sdfasdf', '2022-11-18 03:05:41.873589', 3, 23, 4, 3),
	(22, 'ㅇㅀㄴㅀ\r\n### 주석석\r\n# 주서기서기기sdfsdf', '2022-11-18 03:05:46.573386', 3, 16, 4, 3),
	(23, '1', '2022-11-18 03:07:09.969922', 1, 42, 4, 3),
	(24, '##1 ', '2022-11-18 03:07:18.762839', 2, 42, 4, 3),
	(25, '**[17](https://lab.ssafy.com/s07-final/S07P31C206/-/wikis/1117-Daily-Scrum)** ', '2022-11-18 05:32:55.236811', 1, 51, 4, 3),
	(26, '**[17](https://lab.ssafy.com/s07-final/S07P31C206/-/wikis/1117-Daily-Scrum)** \r\n## sadf', '2022-11-18 05:33:06.011030', 2, 51, 4, 3),
	(27, 'import asdf', '2022-11-18 05:55:22.383177', 1, 53, 4, 3),
	(28, 'import asdf\r\n### asdf ', '2022-11-18 05:55:37.868942', 2, 53, 4, 3),
	(29, 'sdfg', '2022-11-18 05:57:52.264245', 1, 10, 14, 3),
	(31, 'asdfsdf', '2022-11-18 05:59:51.910770', 1, 56, 16, 3),
	(32, 'asdfsdfasdfsdf', '2022-11-18 05:59:56.025138', 2, 56, 16, 3),
	(33, '123', '2022-11-18 06:14:19.978970', 1, 45, 20, 9),
	(34, '123123', '2022-11-18 06:14:24.522362', 2, 45, 20, 9),
	(35, '123123', '2022-11-18 06:17:14.611866', 3, 45, 20, 9),
	(36, 'asdf', '2022-11-18 06:17:44.653239', 1, 44, 20, 3),
	(37, 'asdfasdfasdf', '2022-11-18 06:17:50.759689', 2, 44, 20, 3),
	(38, 'qwerasdf', '2022-11-18 06:18:04.340841', 1, 44, 20, 6),
	(39, 'qwerasdf\r\n//', '2022-11-18 06:18:14.395209', 2, 44, 20, 6),
	(40, '# 복붙', '2022-11-18 06:36:26.485705', 1, 57, 4, 3),
	(41, '# 복붙\r\n\r\n# 설명추가\r\n# 이곳을 첨삭해주세여!', '2022-11-18 06:36:59.479856', 2, 57, 4, 3),
	(42, 'asdfsdfasdfsdf', '2022-11-18 06:37:56.218848', 3, 56, 16, 3),
	(43, '## asdf', '2022-11-18 06:49:44.791078', 1, 59, 4, 3),
	(44, '## asdf\r\n## asdf ', '2022-11-18 06:49:51.721032', 2, 59, 4, 3),
	(45, '\r\ninput = sys.stdin.readline\r\n\r\nn = int(input())\r\nk = int(input())\r\nlst = sorted(map(int,input().split()))\r\nlst2 = []\r\nif n > k:\r\n    for idx in range(1, n):\r\n        lst2.append(lst[idx] - lst[idx - 1])\r\n    lst2.sort(reverse=True)\r\n    for _ in range(k - 1):\r\n        lst2.pop(0)\r\n    print(sum(lst2))\r\nelse:\r\n    print(0)', '2022-11-18 07:01:37.696659', 1, 61, 18, 3),
	(46, '\r\ninput = sys.stdin.readline\r\n\r\nn = int(input())\r\nk = int(input())\r\nlst = sorted(map(int,input().split()))\r\nlst2 = []\r\nif n > k:\r\n    for idx in range(1, n):\r\n        lst2.append(lst[idx] - lst[idx - 1])\r\n    lst2.sort(reverse=True)\r\n    for _ in range(k - 1):\r\n        lst2.pop(0)\r\n    print(sum(lst2))\r\nelse:\r\n    print(0)\r\n\r\n    \r\n# 좀 더 최적화 할 방법이 있을까요?', '2022-11-18 07:02:42.197789', 2, 61, 18, 3),
	(47, 'asdfasdf', '2022-11-18 07:04:14.085982', 1, 16, 4, 2),
	(48, '# 딕셔너리 DP\r\ndef dfs(cur, idx1, idx2):\r\n    if cur == len(result):\r\n        return True\r\n\r\n    if dp.get(f\'{idx1},{idx2}\') is not None:\r\n        return dp[f\'{idx1},{idx2}\']\r\n\r\n    ans = 0\r\n    if idx1 < len(string1) and result[cur] == string1[idx1]:\r\n        if dfs(cur + 1, idx1 + 1, idx2):\r\n            ans = 1\r\n\r\n    if idx2 < len(string2) and result[cur] == string2[idx2]:\r\n        if dfs(cur + 1, idx1, idx2 + 1):\r\n            ans = 1\r\n\r\n    dp[f\'{idx1},{idx2}\'] = ans\r\n    return ans\r\n\r\n\r\nn = int(input())\r\n\r\nfor tc in range(1, n + 1):\r\n    string1, string2, result = input().split()\r\n    dp = {}\r\n    print(f"Data set {tc}:", \'yes\' if dfs(0, 0, 0) else \'no\')', '2022-11-18 07:23:14.358753', 1, 63, 24, 7),
	(49, 'import sys\r\ndef solve(str1:str,str2:str,target:str)->bool:\r\n    stack = [(0,0,0)]\r\n    check = {}\r\n    while stack:\r\n        idx_str1,idx_str2,idx_target = stack.pop()\r\n        if idx_target == len(target):\r\n            return True\r\n        if idx_str1 < len(str1) and target[idx_target] == str1[idx_str1] and not check.get((idx_str1+1,idx_str2)):\r\n            stack.append((idx_str1+1,idx_str2,idx_target+1))\r\n            check[(idx_str1+1,idx_str2)] = True\r\n        if idx_str2 < len(str2) and target[idx_target] == str2[idx_str2] and not check.get((idx_str1,idx_str2+1)):\r\n            stack.append((idx_str1,idx_str2+1,idx_target+1))\r\n            check[(idx_str1, idx_str2+1)] = True\r\n    return False\r\n\r\nn = int(sys.stdin.readline())\r\nfor tc in range(1,n+1):\r\n    first,second,target = sys.stdin.readline().split()\r\n    ans = solve(first,second,target)\r\n    if ans:\r\n        print(f\'Data set {tc}: yes\')\r\n    else:\r\n        print(f\'Data set {tc}: no\')', '2022-11-18 07:23:55.736867', 1, 63, 24, 4),
	(50, 'import sys\r\ndef solve(str1:str,str2:str,target:str)->bool:\r\n    stack = [(0,0,0)]\r\n    check = {}\r\n    while stack:\r\n        idx_str1,idx_str2,idx_target = stack.pop()\r\n        if idx_target == len(target):\r\n            return True\r\n        if idx_str1 < len(str1) and target[idx_target] == str1[idx_str1] and not check.get((idx_str1+1,idx_str2)):\r\n            stack.append((idx_str1+1,idx_str2,idx_target+1))\r\n            check[(idx_str1+1,idx_str2)] = True\r\n        # if idx_str2 < len(str2) and target[idx_target] == str2[idx_str2] and not check.get((idx_str1,idx_str2+1)):\r\n        #     stack.append((idx_str1,idx_str2+1,idx_target+1))\r\n        #     check[(idx_str1, idx_str2+1)] = True\r\n    return False\r\n\r\nn = int(sys.stdin.readline())\r\nfor tc in range(1,n+1):\r\n    first,second,target = sys.stdin.readline().split()\r\n    ans = solve(first,second,target)\r\n    # if ans:\r\n    #     print(f\'Data set {tc}: yes\')\r\n    # else:\r\n    #     print(f\'Data set {tc}: no\')\r\n\r\n    # 더 빠르게 풀고싶어요', '2022-11-18 07:24:40.313475', 2, 63, 24, 4),
	(51, '# 결혼식\r\n\r\n- 문서희\r\n    \r\n    ```python\r\n    n = int(input())\r\n    students = {}\r\n    ans = []\r\n    for key in range(1,n+1): # 딕셔너리 만들기\r\n        students[str(key)] = []\r\n    \r\n    for i in range(int(input())): # 딕셔너리 안에 친구 관계 정리\r\n        a, b = map(int, input().split())\r\n        students[str(a)].append(b)\r\n        students[str(b)].append(a)\r\n    \r\n    if students[\'1\'] != []: # 상근이가 친구가 있으면\r\n        friends = students[\'1\'] \r\n        ans = friends # 정답 리스트에 상근이의 친구 추가\r\n        for i in range(len(friends)): # 상근이 친구의 친구 추가\r\n            ffriends = students[str(friends[i])]\r\n            for j in range(len(ffriends)):\r\n                if ffriends[j] not in ans and ffriends[j] != 1:\r\n                    ans.append(ffriends[j])\r\n    \r\n    print(len(ans))\r\n    ```', '2022-11-18 07:29:39.572227', 1, 63, 24, 3),
	(52, '# 결혼식\r\n\r\n- 문서희\r\n    \r\n    ```python\r\n    n = int(input())\r\n    students = {}\r\n    ans = []\r\n    for key in range(1,n+1): # 딕셔너리 만들기\r\n        students[str(key)] = []\r\n    \r\n    for i in range(int(input())): # 딕셔너리 안에 친구 관계 정리\r\n        a, b = map(int, input().split())\r\n        students[str(a)].append(b)\r\n        students[str(b)].append(a)\r\n    \r\n    if students[\'1\'] != []: # 상근이가 친구가 있으면\r\n        friends = students[\'1\'] \r\n        ans = friends # 정답 리스트에 상근이의 친구 추가\r\n        for i in range(len(friends)): # 상근이 친구의 친구 추가\r\n    #         ffriends = students[str(friends[i])]\r\n    #         for j in range(len(ffriends)):\r\n    #             if ffriends[j] not in ans and ffriends[j] != 1:\r\n    #                 ans.append(ffriends[j])\r\n    \r\n    # print(len(ans))\r\n    # ```\r\n\r\n    # 다른 접근 방법이 있을까요?', '2022-11-18 07:30:06.127048', 2, 63, 24, 3),
	(53, 'def pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n', '2022-11-18 07:42:00.808573', 1, 64, 24, 4),
	(54, 'def pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n# //저는 이렇게 접근했습니다.asdf', '2022-11-18 07:42:17.683670', 2, 64, 24, 4),
	(55, 'import sys\r\ndef solve(str1:str,str2:str,target:str)->bool:\r\n    stack = [(0,0,0)]\r\n    check = {}\r\n    while stack:\r\n        idx_str1,idx_str2,idx_target = stack.pop()\r\n        if idx_target == len(target):\r\n            return True\r\n        if idx_str1 < len(str1) and target[idx_target] == str1[idx_str1] and not check.get((idx_str1+1,idx_str2)):\r\n            stack.append((idx_str1+1,idx_str2,idx_target+1))\r\n            check[(idx_str1+1,idx_str2)] = True\r\n        # if idx_str2 < len(str2) and target[idx_target] == str2[idx_str2] and not check.get((idx_str1,idx_str2+1)):\r\n        #     stack.append((idx_str1,idx_str2+1,idx_target+1))\r\n        #     check[(idx_str1, idx_str2+1)] = True\r\n    return False\r\n\r\nn = int(sys.stdin.readline())\r\nfor tc in range(1,n+1):\r\n    first,second,target = sys.stdin.readline().split()\r\n    ans = solve(first,second,target)\r\n    # if ans:\r\n    #     print(f\'Data set {tc}: yes\')\r\n    # else:\r\n    #     print(f\'Data set {tc}: no\')\r\n\r\n    # 더 빠르게 풀고싶어요\r\n    pwdic = {}\r\nfor i in range(N):\r\n    site, password = map(str, input().split())\r\n    pwdic[site] = password\r\n\r\nfor j in range(M):\r\n    j = input()\r\n    print(pwdic[j])\r\n\r\n    # 저는 이렇게 접근해서 풀어보았습니다.\r\n', '2022-11-18 07:44:08.280667', 3, 63, 24, 4),
	(56, '코드 제출', '2022-11-18 14:54:51.912322', 1, 45, 20, 6),
	(57, '코드 제출\r\n// 주석 처리', '2022-11-18 14:55:36.122573', 2, 45, 20, 6),
	(58, '코드 제출\r\n// 주석 처리', '2022-11-18 15:03:02.794864', 3, 45, 20, 6),
	(59, '#include<iostream>\r\n\r\nusing namespace std;\r\n\r\nint getGCD(int num1, int num2);\r\nint getLCM(int num1, int num2);\r\nint main () {\r\n    \r\n    int num1 = 0;\r\n    int num2 = 0;\r\n    \r\n    cin >> num1 >> num2;\r\n    \r\n    cout << getGCD(num1, num2) << endl;\r\n    cout << getLCM(num1, num2) << endl;\r\n    \r\n    return 0;\r\n}\r\n\r\nint getGCD(int num1, int num2) {\r\n    int max = (num1 > num2) ? num1 : num2;\r\n    int min = (num1 < num2) ? num1 : num2;\r\n    int tmp = max % min;\r\n    \r\n    while(tmp != 0) {\r\n        max = min;\r\n        min = tmp;\r\n        tmp = max % min;\r\n    }\r\n    \r\n    return min;\r\n}\r\n\r\nint getLCM(int num1, int num2) {\r\n    return (num1 * num2) / getGCD(num1, num2);\r\n}', '2022-11-18 17:09:00.749632', 1, 49, 20, 6),
	(60, '#include<iostream>\r\n\r\nusing namespace std;\r\n\r\nint getGCD(int num1, int num2);\r\nint getLCM(int num1, int num2);\r\nint main () {\r\n    \r\n    int num1 = 0;\r\n    int num2 = 0;\r\n    \r\n    cin >> num1 >> num2;\r\n    \r\n    // 최대공약수 구하기\r\n    cout << getGCD(num1, num2) << endl;\r\n    // 최대공배수 구하기\r\n    cout << getLCM(num1, num2) << endl;\r\n    \r\n    return 0;\r\n}\r\n\r\n// 최대공약수 구하기\r\n// 유클리드 호제법\r\nint getGCD(int num1, int num2) {\r\n    int max = (num1 > num2) ? num1 : num2;\r\n    int min = (num1 < num2) ? num1 : num2;\r\n    int tmp = max % min;\r\n    \r\n    while(tmp != 0) {\r\n        max = min;\r\n        min = tmp;\r\n        tmp = max % min;\r\n    }\r\n    \r\n    return min;\r\n}\r\n\r\n// 최소공배수 구하기\r\n// num1 * num2 = 최대공약수 * 최소공배수\r\nint getLCM(int num1, int num2) {\r\n    return (num1 * num2) / getGCD(num1, num2);\r\n}', '2022-11-18 17:10:32.828890', 2, 49, 20, 6),
	(61, '#include<iostream>\r\n\r\nusing namespace std;\r\n\r\nint getGCD(int num1, int num2);\r\nint getLCM(int num1, int num2);\r\nint main () {\r\n    \r\n    int num1 = 0;\r\n    int num2 = 0;\r\n    \r\n    cin >> num1 >> num2;\r\n    \r\n    // 최대공약수 구하기\r\n    cout << getGCD(num1, num2) << endl;\r\n    // 최대공배수 구하기\r\n    cout << getLCM(num1, num2) << endl;\r\n    \r\n    return 0;\r\n}\r\n\r\n// 유클리드 호제법\r\nint getGCD(int num1, int num2) {\r\n    int max = (num1 > num2) ? num1 : num2;\r\n    int min = (num1 < num2) ? num1 : num2;\r\n    int tmp = max % min;\r\n    \r\n    while(tmp != 0) {\r\n        max = min;\r\n        min = tmp;\r\n        tmp = max % min;\r\n    }\r\n    \r\n    return min;\r\n}\r\n\r\n// num1 * num2 = 최대공약수 * 최소공배수\r\nint getLCM(int num1, int num2) {\r\n    return (num1 * num2) / getGCD(num1, num2);\r\n}', '2022-11-18 17:12:28.265645', 3, 49, 20, 6),
	(62, 'dfsg', '2022-11-19 10:43:13.641385', 1, 67, 24, 4),
	(63, 'dfsgsdfgsdfg', '2022-11-19 10:43:18.514739', 2, 67, 24, 4),
	(64, 'ㄴㅇㄹ', '2022-11-19 10:54:44.567678', 1, 68, 24, 4),
	(65, 'ㄴㅇㄹㄴㅇㄹㄴㅇㄹ', '2022-11-19 10:54:48.960727', 2, 68, 24, 4),
	(66, 'sdf', '2022-11-19 11:09:18.532324', 1, 69, 4, 3),
	(67, 'sdfdlapdlf', '2022-11-19 11:09:25.759840', 2, 69, 4, 3),
	(68, 'def pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n\r\n    block = arr[x][y]       # 현재 블록 값을 적어준다.\r\n', '2022-11-19 13:36:27.001153', 1, 70, 24, 4),
	(69, 'def pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n\r\n    block = arr[x][y]       # 현재 블록 값을 적어준다.\r\n\r\n# 좀 더 최적화 할 방법이 있을까요?', '2022-11-19 13:36:53.319659', 2, 70, 24, 4),
	(70, 'def pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n# //저는 이렇게 접근했습니다.asdf\r\n\r\ndef pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False', '2022-11-19 13:38:21.101308', 3, 64, 24, 4),
	(71, '# 결혼식\r\n\r\n- 문서희\r\n    \r\n    ```python\r\n    n = int(input())\r\n    students = {}\r\n    ans = []\r\n    for key in range(1,n+1): # 딕셔너리 만들기\r\n        students[str(key)] = []\r\n    asdf\r\n    for i in range(int(input())): # 딕셔너리 안에 친구 관계 정리\r\n        a, b = map(int, input().split())\r\n        students[str(a)].append(b)\r\n        students[str(b)].append(a)\r\n    \r\n    if students[\'1\'] != []: # 상근이가 친구가 있으면\r\n        friends = students[\'1\'] \r\n        ans = friends # 정답 리스트에 상근이의 친구 추가\r\n        for i in range(len(friends)): # 상근이 친구의 친구 추가\r\n    #         ffriends = students[str(friends[i])]\r\n    #         for j in range(len(ffriends)):\r\n    #             if ffriends[j] not in ans and ffriends[j] != 1:\r\n    #                 ans.append(ffriends[j])\r\n    \r\n    # print(len(ans))\r\n    # ```\r\n\r\n    # 다른 접근 방법이 있을까요?', '2022-11-20 07:40:33.202854', 3, 63, 24, 3),
	(72, 'sdf', '2022-11-20 09:08:01.642673', 1, 70, 24, 7),
	(73, 'asd', '2022-11-20 09:08:39.040048', 1, 70, 24, 3),
	(74, 'asdasd', '2022-11-20 09:08:43.944705', 2, 70, 24, 3),
	(75, 'def pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n\r\n    block = arr[x][y]       # 현재 블록 값을 적어준다.\r\n\r\n# 좀 더 최적화 할 방법이 있을까요?asdf', '2022-11-20 09:09:55.385394', 3, 70, 24, 4),
	(77, 'asdf', '2022-11-20 12:31:40.041912', 1, 65, 24, 3),
	(78, 'import sys\r\n\r\nnum_ = int(sys.stdin.readline())\r\n\r\nval = [0,1]\r\nmemo =[(1,0),(0,1)]\r\ndef fibo(n):\r\n    if n == 0:\r\n        return print(" ".join([str(memo[0][0]),str(memo[0][1])]))\r\n    elif n == 1:\r\n        return print(" ".join([str(memo[1][0]),str(memo[1][1])]))\r\n    else:\r\n        for i in range(2, n+1):\r\n            memo.append((memo[i-1][0]+memo[i-2][0],\r\n                         memo[i-1][1]+memo[i-2][1]))\r\n        return print(" ".join([str(memo[-1][0]),str(memo[-1][1])]))\r\n\r\n\r\nfor i in range(num_):\r\n    memo = [(1, 0), (0, 1)]\r\n    fibo(int(sys.stdin.readline()))\r\n\r\n# 코드리뷰 부탁드립니다!', '2022-11-20 12:31:43.536667', 2, 65, 24, 3),
	(82, 'import sys\r\n\r\nnum_ = int(sys.stdin.readline())\r\n\r\nval = [0,1]\r\nmemo =[(1,0),(0,1)]\r\ndef fibo(n):\r\n    if n == 0:\r\n        return print(" ".join([str(memo[0][0]),str(memo[0][1])]))\r\n    elif n == 1:\r\n        return print(" ".join([str(memo[1][0]),str(memo[1][1])]))\r\n    else:\r\n        for i in range(2, n+1):\r\n            memo.append((memo[i-1][0]+memo[i-2][0],\r\n                         memo[i-1][1]+memo[i-2][1]))\r\n        return print(" ".join([str(memo[-1][0]),str(memo[-1][1])]))\r\n\r\n\r\nfor i in range(num_):\r\n    memo = [(1, 0), (0, 1)]\r\n    fibo(int(sys.stdin.readline()))\r\n\r\n', '2022-11-20 14:00:35.730777', 1, 64, 24, 3),
	(83, 'import sys\r\n\r\nnum_ = int(sys.stdin.readline())\r\n\r\nval = [0,1]\r\nmemo =[(1,0),(0,1)]\r\ndef fibo(n):\r\n    if n == 0:\r\n        return print(" ".join([str(memo[0][0]),str(memo[0][1])]))\r\n    elif n == 1:\r\n        return print(" ".join([str(memo[1][0]),str(memo[1][1])]))\r\n    else:\r\n        for i in range(2, n+1):\r\n            memo.append((memo[i-1][0]+memo[i-2][0],\r\n                         memo[i-1][1]+memo[i-2][1]))\r\n        return print(" ".join([str(memo[-1][0]),str(memo[-1][1])]))\r\n\r\n\r\nfor i in range(num_):\r\n    memo = [(1, 0), (0, 1)]\r\n    fibo(int(sys.stdin.readline()))\r\n# 코드리뷰 부탁드립니다!', '2022-11-20 14:00:49.219201', 2, 64, 24, 3),
	(84, 'dfsgsdfgsdfgasdf', '2022-11-20 14:26:08.273423', 3, 67, 24, 4);

-- 테이블 test.edited_code 구조 내보내기
CREATE TABLE IF NOT EXISTS `edited_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `edited_date` datetime(6) DEFAULT NULL,
  `text` varchar(10000) DEFAULT NULL,
  `code_id` bigint(20) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcsubr763cyf0xxaf7m5xg6b30` (`code_id`),
  KEY `FKfddpfia30qq17dl4ho67nsii1` (`receiver_id`),
  KEY `FK6qlrqd0smcqrqeidahfg7q066` (`sender_id`),
  CONSTRAINT `FK6qlrqd0smcqrqeidahfg7q066` FOREIGN KEY (`sender_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKcsubr763cyf0xxaf7m5xg6b30` FOREIGN KEY (`code_id`) REFERENCES `code` (`id`),
  CONSTRAINT `FKfddpfia30qq17dl4ho67nsii1` FOREIGN KEY (`receiver_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.edited_code:~16 rows (대략적) 내보내기
INSERT INTO `edited_code` (`id`, `edited_date`, `text`, `code_id`, `receiver_id`, `sender_id`) VALUES
	(2, '2022-11-17 07:03:24.293785', 'ㅇㅀㄴㅀ\r\n### 주석석\r\n# 주서기서기기\r\n11', 5, 3, 2),
	(3, '2022-11-17 07:03:34.764444', '# sfdf\r\n# sdfwntjr\r\n11', 9, 3, 2),
	(4, '2022-11-17 07:03:39.881228', 'g=\r\n#kn\r\n11', 3, 3, 2),
	(5, '2022-11-17 07:45:40.522254', '', 13, 2, 4),
	(6, '2022-11-17 08:07:07.894424', '', 18, 2, 4),
	(7, '2022-11-18 06:17:01.674791', '123123\r\nabcabc', 34, 9, 3),
	(8, '2022-11-18 06:17:06.299284', '123123\r\n456456', 34, 9, 6),
	(9, '2022-11-18 06:17:13.514108', 'asdfsdfasdfsdf\r\nqwerqwerqwer', 32, 3, 6),
	(10, '2022-11-18 06:17:32.076520', '123123\r\nimport sys\r\n\r\ninput = sys.stdin.readline\r\n\r\nn = int(input())\r\nk = int(input())\r\nlst = sorted(map(int,input().split()))\r\nlst2 = []\r\nif n > k:\r\n    for idx in range(1, n):\r\n        lst2.append(lst[idx] - lst[idx - 1])\r\n\r\n    lst2.sort(reverse=True)\r\n    for _ in range(k - 1):\r\n        lst2.pop(0)\r\n\r\n    print(sum(lst2))\r\nelse:\r\n    print(0)', 34, 9, 4),
	(12, '2022-11-18 07:43:33.772100', '# 결혼식\r\n\r\n- 문서희\r\n    \r\n    ```python\r\n    n = int(input())\r\n    students = {}\r\n    ans = []\r\n    for key in range(1,n+1): # 딕셔너리 만들기\r\n        students[str(key)] = []\r\n    \r\n    for i in range(int(input())): # 딕셔너리 안에 친구 관계 정리\r\n        a, b = map(int, input().split())\r\n        students[str(a)].append(b)\r\n        students[str(b)].append(a)\r\n    \r\n    if students[\'1\'] != []: # 상근이가 친구가 있으면\r\n        friends = students[\'1\'] \r\n        ans = friends # 정답 리스트에 상근이의 친구 추가\r\n        for i in range(len(friends)): # 상근이 친구의 친구 추가\r\n    #         ffriends = students[str(friends[i])]\r\n    #         for j in range(len(ffriends)):\r\n    #             if ffriends[j] not in ans and ffriends[j] != 1:\r\n    #                 ans.append(ffriends[j])\r\n    \r\n    # print(len(ans))\r\n    # ```\r\n\r\n    # 다른 접근 방법이 있을까요?\r\n    \r\n# for i in range(r):\r\n#     for j in range(c):\r\n#         if arr[i][j] == \'.\':\r\n#             for block in blocks:        # 7가지 블록을 넣어가며 확인\r\n#                 visited = [[0] * c for _ in range(r)]   # 다 방문했는지 확인\r\n#                 arr[i][j] = block       # 블록을 바꿔준다.\r\n#                 if pipe(*m, -1):        # 완성한 경우\r\n#                     print(i + 1, j + 1, block)      # 출력\r\n#                     exit()\r\n', 52, 3, 4),
	(13, '2022-11-18 14:59:45.743824', '코드 제출\r\n// 주석 처리\r\n// 확인 완료', 57, 6, 3),
	(14, '2022-11-18 17:11:56.395683', '#include<iostream>\r\n\r\nusing namespace std;\r\n\r\nint getGCD(int num1, int num2);\r\nint getLCM(int num1, int num2);\r\nint main () {\r\n    \r\n    int num1 = 0;\r\n    int num2 = 0;\r\n    \r\n    cin >> num1 >> num2;\r\n    \r\n    // 최대공약수 구하기\r\n    cout << getGCD(num1, num2) << endl;\r\n    // 최대공배수 구하기\r\n    cout << getLCM(num1, num2) << endl;\r\n    \r\n    return 0;\r\n}\r\n\r\n// 최대공약수 구하기\r\n// 유클리드 호제법\r\nint getGCD(int num1, int num2) {\r\n    int max = (num1 > num2) ? num1 : num2;\r\n    int min = (num1 < num2) ? num1 : num2;\r\n    int tmp = max % min;\r\n    \r\n    while(tmp != 0) {\r\n        max = min;\r\n        min = tmp;\r\n        tmp = max % min;\r\n    }\r\n    \r\n    return min;\r\n}\r\n\r\n// 최소공배수 구하기\r\n// num1 * num2 = 최대공약수 * 최소공배수\r\nint getLCM(int num1, int num2) {\r\n    return (num1 * num2) / getGCD(num1, num2);\r\n}\r\n\r\n// 오 C로는 어떻게 구현했는지 궁금했는데 감사합니다!', 60, 6, 3),
	(16, '2022-11-19 13:37:40.786024', '코드 제출\r\n// 주석 처리\r\n\r\ndef pipe(x, y, dir):\r\n    visited[x][y] = True    # 방문 표시\r\n    if arr[x][y] == \'Z\':    # Z가 나오면 다 방문했는지 표시\r\n        if check():\r\n            return True\r\n        return False\r\n    if arr[x][y] == \'.\':    # .인 경우는 False\r\n        return False\r\n\r\n    block = arr[x][y]       # 현재 블록 값을 적어준다.\r\n', 57, 6, 4),
	(20, '2022-11-20 09:23:50.009754', 'asdfasdfasdf\r\n\r\n# //???', 37, 3, 4),
	(21, '2022-11-20 09:24:07.877601', '\r\ninput = sys.stdin.readline\r\n\r\nn = int(input())\r\nk = int(input())\r\nlst = sorted(map(int,input().split()))\r\nlst2 = []\r\nif n > k:\r\n    for idx in range(1, n):\r\n        lst2.append(lst[idx] - lst[idx - 1])\r\n    lst2.sort(reverse=True)\r\n    for _ in range(k - 1):\r\n        lst2.pop(0)\r\n    print(sum(lst2))\r\nelse:\r\n    print(0)\r\n\r\n    \r\n# 좀 더 최적화 할 방법이 있을까요?\r\nif n > k:\r\n    for idx in range(1, n):\r\n        lst2.append(lst[idx] - lst[idx - 1])\r\n    lst2.sort(reverse=True)\r\n    for _ in range(k - 1):\r\n        lst2.pop(0)\r\n    print(sum(lst2))\r\nelse:\r\n    print(0)', 46, 3, 4),
	(23, '2022-11-20 13:30:39.426037', 'asdfasdf\r\n데이터 잘 넣어놔라 진짜 ㅡㅡ', 78, 3, 4);

-- 테이블 test.member 구조 내보내기
CREATE TABLE IF NOT EXISTS `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `baekjoon_id` varchar(50) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tier` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.member:~11 rows (대략적) 내보내기
INSERT INTO `member` (`id`, `baekjoon_id`, `created_date`, `email`, `nickname`, `password`, `tier`) VALUES
	(1, 'joey0319', '2022-11-17 04:27:27.971348', 'joe930@hanmail.net', '알고리즘 마스터', '$2a$10$AMOXwaPWYjZGjzTU59A.Z.jKmJblJmxdC6VvLXHCLrJ07mjIA6Lle', 8),
	(2, 'min61037', '2022-11-17 04:27:32.883102', 'chob5864@gmail.com', '알디알지', '$2a$10$5xEnsXEzrNoPVqbU44N18ezjisDRDIShLFFWKm11roPrwnQekLcNm', 14),
	(3, 'seho27060', '2022-11-17 04:27:35.435421', 'seho27060@gmail.com', '세호세호', '$2a$10$/FnrjCsjbhFmVyPZF0.DZOrb9/Zr/TTdZBRxThVlA.aWg5pKFFJb.', 15),
	(4, 'zzzppap', '2022-11-17 04:27:38.936274', 'dream222ing@gmail.com', '모험가', '$2a$10$hIJdeis9S3Z1qfHO4QBDIe6TsQRq90ZMK9jT2KQ3xlVMdV/On0Ru.', 10),
	(5, 'gkgustj', '2022-11-17 04:28:12.362625', 'gkgustj@naver.com', '현서', '$2a$10$4NAPpU7yaNVydckJWFwO/.DvGUY0XMRiEBeRlSV2dpM6X6dfmrMu2', 12),
	(6, 'wow2867', '2022-11-17 05:00:31.543825', 'wow2867@naver.com', '옹라', '$2a$10$JzOWd47dsaxppMtxU6BHqet6XjzF8BmYwVz3olVkvrOIfrrJUGFem', 2),
	(7, 'baekhannah', '2022-11-17 05:00:51.035657', 'baekhannah@naver.com', '무등산날다람쥐', '$2a$10$ynb/iYD65/PcrSPLdx/YKuPiYsHioHICQbuYPJjq4T6ft9xaVcYNa', 10),
	(8, 'ashooozzz', '2022-11-17 05:51:04.235910', 'wallwallwall@kakao.com', '라떼', '$2a$10$HAZJgF1goTYZFGrpnOWS/eUZF1t.fApxRVrlOUyCH2jnebtzmxx2q', 17),
	(9, 'zmmmm111', '2022-11-18 01:31:37.420381', 'zmmmm111@gmail.com', '세룽룽', '$2a$10$Z1uuQEGkQLpb7E9yAkxC2.WpzjXZc8lj11omyDusNBcj.SxmA2vDW', 10),
	(10, 'mayakpari', '2022-11-18 06:14:31.737228', 'today731@naver.com', 'MPR', '$2a$10$QkyxG0S8o4TJec94.O18iOTu48iNf6w0sk46hI6mHhA/XGzMZMdeW', 12),
	(11, 'hyehello', '2022-11-19 07:23:39.016217', 'aldy@ssafy.com', '공룡왕', '$2a$10$GP18TPXT0l/PlsqmJYj4aO3vH2D.gVLnUmsPdv2wmAwYl.NjQQnrK', 1);

-- 테이블 test.member_in_study 구조 내보내기
CREATE TABLE IF NOT EXISTS `member_in_study` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `number_of_alerts` int(11) NOT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `study_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKroi4d6qyb4fh03g17yesw82ox` (`member_id`),
  KEY `FK8tk0ploe9a1oc3ubf5c3w276` (`study_id`),
  CONSTRAINT `FK8tk0ploe9a1oc3ubf5c3w276` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`),
  CONSTRAINT `FKroi4d6qyb4fh03g17yesw82ox` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.member_in_study:~46 rows (대략적) 내보내기
INSERT INTO `member_in_study` (`id`, `auth`, `message`, `number_of_alerts`, `member_id`, `study_id`) VALUES
	(1, 1, 'Leader', 0, 2, 1),
	(3, 1, 'Leader', 0, 4, 3),
	(4, 1, 'Leader', 1, 3, 4),
	(5, 1, 'Leader', 0, 1, 5),
	(6, 1, 'Leader', 0, 4, 6),
	(8, 1, 'Leader', 0, 4, 8),
	(17, 3, '가입신청합니다.', 0, 7, 6),
	(18, 3, '카카오 들어가고 싶어요...', 0, 2, 5),
	(19, 1, 'Leader', 0, 4, 13),
	(20, 3, '파이썬으로 알고리즘이 그렇게 쉽다는데...', 0, 2, 3),
	(21, 2, '퉤스트! ', 0, 2, 13),
	(22, 3, '123', 0, 6, 6),
	(24, 1, 'Leader', 1, 6, 14),
	(26, 2, 'dighgh호호야', 0, 3, 14),
	(28, 1, 'Leader', 0, 7, 15),
	(29, 3, '2트', 0, 7, 14),
	(30, 1, 'Leader', 0, 3, 16),
	(32, 2, 'string', 1, 6, 16),
	(35, 1, 'Leader', 1, 3, 18),
	(36, 2, '스터디 가입', 1, 4, 18),
	(37, 2, '가입신청합니다.', 1, 8, 18),
	(38, 2, '가입신청합니다.!!!', 1, 1, 18),
	(39, 0, '가입신청합니다.', 4, 8, 4),
	(40, 2, '', 0, 9, 18),
	(41, 2, '', 0, 9, 8),
	(42, 1, 'Leader', 3, 9, 20),
	(43, 2, '여보세요 나야', 3, 3, 20),
	(44, 0, '가입신청합니다', 4, 4, 20),
	(45, 2, '', 1, 6, 20),
	(46, 1, 'Leader', 0, 9, 21),
	(47, 3, 'll;;', 0, 3, 21),
	(48, 0, '가입신청합니다', 0, 1, 21),
	(49, 1, 'Leader', 0, 10, 22),
	(50, 1, 'Leader', 0, 4, 23),
	(51, 1, 'Leader', 0, 4, 24),
	(52, 2, '가입신청합니다.', 1, 3, 24),
	(53, 2, '안녕', 2, 7, 24),
	(56, 0, '가입신청합니다.', 3, 8, 24),
	(57, 3, '안녕하세요. \n자바로 알고리즘 푸는 법 배우고 싶습니다.\n가입 신청 받아주세요 🥰', 0, 8, 23),
	(74, 3, 'string', 0, 3, 1),
	(75, 2, 'fd', 1, 2, 4),
	(76, 1, 'Leader', 0, 2, 26),
	(77, 3, '제발 받아주세요', 0, 1, 26),
	(78, 3, 'tffuwndhy', 0, 3, 26),
	(83, 3, 'string', 0, 1, 1),
	(84, 2, '받아주세요', 3, 2, 24);

-- 테이블 test.problem 구조 내보내기
CREATE TABLE IF NOT EXISTS `problem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_checked` bit(1) DEFAULT NULL,
  `is_level_checked` bit(1) DEFAULT NULL,
  `problem_day` int(11) NOT NULL,
  `problem_name` varchar(255) DEFAULT NULL,
  `problem_num` int(11) NOT NULL,
  `problem_tier` int(11) NOT NULL,
  `calendar_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrbkhdj2n61yjyt0nv6qrhqstd` (`calendar_id`),
  CONSTRAINT `FKrbkhdj2n61yjyt0nv6qrhqstd` FOREIGN KEY (`calendar_id`) REFERENCES `calendar` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.problem:~40 rows (대략적) 내보내기
INSERT INTO `problem` (`id`, `is_checked`, `is_level_checked`, `problem_day`, `problem_name`, `problem_num`, `problem_tier`, `calendar_id`) VALUES
	(1, b'0', b'0', 24, '뱀', 3190, 12, 1),
	(2, b'0', b'0', 24, '주사위 굴리기', 14499, 12, 1),
	(4, b'0', b'0', 25, '아기 상어', 16236, 13, 1),
	(5, b'0', b'0', 25, '구슬 탈출 2', 13460, 15, 1),
	(6, b'0', b'0', 25, '2048 (Easy)', 12100, 14, 1),
	(7, b'0', b'0', 25, '나무 재테크', 16235, 13, 1),
	(10, b'1', b'0', 18, '빠른 A+B', 15552, 2, 4),
	(11, b'1', b'0', 17, '사분면 고르기', 14681, 1, 5),
	(12, b'1', b'0', 17, '삼각형 외우기', 10101, 2, 5),
	(13, b'1', b'0', 17, '다리 만들기', 2146, 13, 2),
	(14, b'0', b'0', 1, '별 찍기 - 3', 2440, 2, 6),
	(15, b'0', b'0', 1, '세 수', 10817, 3, 6),
	(16, b'1', b'0', 17, '벽 부수고 이동하기 2', 14442, 13, 2),
	(23, b'1', b'0', 17, '로봇', 1726, 13, 2),
	(38, b'1', b'0', 17, '다리 만들기', 2146, 13, 8),
	(42, b'1', b'0', 17, '트리의 지름', 1967, 12, 2),
	(44, b'1', b'0', 18, 'Viva la Diferencia', 4084, 3, 9),
	(45, b'1', b'0', 18, '회사에 있는 사람', 7785, 6, 9),
	(49, b'1', b'0', 18, '최대공약수와 최소공배수', 2609, 5, 9),
	(50, b'1', b'0', 18, '가장 긴 증가하는 부분 수열', 11053, 9, 9),
	(51, b'1', b'0', 18, '트리의 지름', 1967, 12, 2),
	(53, b'1', b'0', 17, '숫자 카드', 10815, 6, 2),
	(54, b'1', b'0', 17, '성적 통계', 5800, 6, 2),
	(56, b'1', b'0', 18, '222-풀링', 17829, 9, 10),
	(57, b'0', b'0', 22, '트리의 지름', 1967, 12, 2),
	(59, b'0', b'0', 22, '저울', 2437, 14, 2),
	(60, b'0', b'0', 22, '압축', 1662, 11, 2),
	(61, b'0', b'0', 21, '저울', 2437, 14, 8),
	(63, b'1', b'0', 17, '설탕 배달', 2839, 7, 11),
	(64, b'1', b'0', 18, '저울', 2437, 14, 11),
	(65, b'1', b'0', 18, '사냥꾼', 8983, 12, 11),
	(66, b'0', b'0', 22, '태권왕', 14562, 9, 2),
	(67, b'1', b'0', 19, '222-풀링', 17829, 9, 11),
	(68, b'1', b'0', 19, '레벨 햄버거', 16974, 9, 11),
	(69, b'1', b'0', 19, '청소년 상어', 19236, 14, 2),
	(70, b'0', b'0', 20, '저울', 2437, 14, 11),
	(80, b'0', b'0', 20, '숨바꼭질 4', 13913, 12, 2),
	(81, b'0', b'0', 20, '연구소 3', 17142, 12, 2),
	(86, b'0', b'0', 21, '숨바꼭질 4', 13913, 12, 11),
	(87, b'0', b'0', 21, '연구소 3', 17142, 12, 11);

-- 테이블 test.refresh_token 구조 내보내기
CREATE TABLE IF NOT EXISTS `refresh_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `baekjoon_id` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.refresh_token:~11 rows (대략적) 내보내기
INSERT INTO `refresh_token` (`id`, `baekjoon_id`, `refresh_token`) VALUES
	(81, 'mayakpari', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXlha3BhcmkiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjY4NzUyMDgwLCJleHAiOjE2NjkzNTY4ODB9.xk-83epLHwiU4VlN-ldTXPIxjsv9B-NqJLm2fKe0y_4'),
	(85, 'gkgustj', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJna2d1c3RqIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2ODc1MzY0MywiZXhwIjoxNjY5MzU4NDQzfQ.eat6weEmYAwD5-BLn6NsF9WdS4sb8AHfjfPN1wLBLMs'),
	(92, 'zmmmm111', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6bW1tbTExMSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2Njg3NTU0NTQsImV4cCI6MTY2OTM2MDI1NH0.Re_ojgI3-S82y9fIu6_B8uareZpGWW3omaIkPsW_kRA'),
	(123, 'wow2867', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3b3cyODY3Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2ODgyNjA5NywiZXhwIjoxNjY5NDMwODk3fQ.lMomaGhglr7fFZXGlcCIiQN3Zl5F7Eb70MpE9BVePCY'),
	(128, 'ashooozzz', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2hvb296enoiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjY4ODQ5OTQ2LCJleHAiOjE2Njk0NTQ3NDZ9.V5GvDhbyyMNnC7tqCSH7F3l9PQ1B2XM5Q-JUdpQe0LQ'),
	(139, 'joey0319', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2V5MDMxOSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2Njg4NTc1NzEsImV4cCI6MTY2OTQ2MjM3MX0.0m8C6UjSpGJTB0_FORC8qn4KcWWUjkLpqKaWBASSfxw'),
	(161, 'baekhannah', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWVraGFubmFoIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2ODkzNjM4NiwiZXhwIjoxNjY5NTQxMTg2fQ.1vCa4XD7wqL8vf1deVvAWj9BP2I5S6JZK2C-qk3E3rU'),
	(162, 'hyehello', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoeWVoZWxsbyIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2Njg5MzY0MTEsImV4cCI6MTY2OTU0MTIxMX0.AZr_mO-OFI_mR7c-x5lK6ynhhY6yrQsuXRiMNGTfuAI'),
	(163, 'min61037', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW42MTAzNyIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2Njg5MzY0NDYsImV4cCI6MTY2OTU0MTI0Nn0.jYhhAXkeIMneJZtahe3ePBk2tCeg4ltLm9Y3IKMQACo'),
	(180, 'seho27060', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZWhvMjcwNjAiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjY4OTU0NTI0LCJleHAiOjE2Njk1NTkzMjR9.6YekmxsQpTLrNSqB3wQuzmo8L92jkfFO9batZpID8y0'),
	(181, 'zzzppap', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6enpwcGFwIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2ODk1NDUzNSwiZXhwIjoxNjY5NTU5MzM1fQ.tDMxJJDXFS4t11a8wZcrA5AGC6_lA_F_uUAbKpU2y5Q');

-- 테이블 test.requested_code 구조 내보내기
CREATE TABLE IF NOT EXISTS `requested_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_checked` bit(1) DEFAULT NULL,
  `is_done` bit(1) DEFAULT NULL,
  `request_date` datetime(6) DEFAULT NULL,
  `code_id` bigint(20) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8i8gsflgvc2p0io1r27yn47n9` (`code_id`),
  KEY `FKpda60kmt3846eal2j2k3em4gy` (`receiver_id`),
  KEY `FK8vqt308jbaf6feyo8xxvv0y1c` (`sender_id`),
  CONSTRAINT `FK8i8gsflgvc2p0io1r27yn47n9` FOREIGN KEY (`code_id`) REFERENCES `code` (`id`),
  CONSTRAINT `FK8vqt308jbaf6feyo8xxvv0y1c` FOREIGN KEY (`sender_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKpda60kmt3846eal2j2k3em4gy` FOREIGN KEY (`receiver_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.requested_code:~39 rows (대략적) 내보내기
INSERT INTO `requested_code` (`id`, `is_checked`, `is_done`, `request_date`, `code_id`, `receiver_id`, `sender_id`) VALUES
	(1, b'1', b'1', '2022-11-17 05:51:05.720656', 3, 2, 3),
	(2, b'1', b'1', '2022-11-17 06:34:40.556320', 5, 2, 3),
	(3, b'1', b'1', '2022-11-17 07:01:21.685880', 7, 2, 4),
	(4, b'1', b'1', '2022-11-17 07:02:51.022083', 9, 2, 3),
	(6, b'0', b'0', '2022-11-17 07:52:45.123077', 15, 2, 4),
	(8, b'0', b'0', '2022-11-18 03:07:22.004650', 24, 2, 3),
	(9, b'0', b'0', '2022-11-18 05:33:16.413323', 26, 2, 3),
	(10, b'0', b'0', '2022-11-18 05:33:16.418085', 26, 8, 3),
	(11, b'0', b'0', '2022-11-18 05:55:41.460234', 28, 2, 3),
	(12, b'0', b'0', '2022-11-18 05:55:41.464711', 28, 8, 3),
	(13, b'1', b'1', '2022-11-18 05:59:57.417001', 32, 6, 3),
	(15, b'1', b'1', '2022-11-18 06:14:28.492548', 34, 3, 9),
	(16, b'1', b'1', '2022-11-18 06:14:28.495364', 34, 6, 9),
	(17, b'0', b'0', '2022-11-18 06:17:56.606834', 37, 9, 3),
	(19, b'0', b'0', '2022-11-18 06:18:19.482827', 39, 9, 6),
	(20, b'0', b'0', '2022-11-18 06:37:10.627845', 41, 2, 3),
	(21, b'0', b'0', '2022-11-18 06:37:10.632493', 41, 8, 3),
	(22, b'0', b'0', '2022-11-18 06:49:56.634162', 44, 2, 3),
	(23, b'0', b'0', '2022-11-18 06:49:56.638692', 44, 8, 3),
	(24, b'0', b'0', '2022-11-18 07:02:52.400586', 46, 8, 3),
	(26, b'0', b'0', '2022-11-18 07:02:52.407900', 46, 9, 3),
	(27, b'0', b'0', '2022-11-18 07:02:52.410547', 46, 1, 3),
	(28, b'1', b'1', '2022-11-18 07:24:43.679678', 50, 3, 4),
	(29, b'0', b'0', '2022-11-18 07:24:43.684053', 50, 7, 4),
	(30, b'0', b'0', '2022-11-18 07:30:09.391489', 52, 7, 3),
	(32, b'1', b'1', '2022-11-18 07:42:25.064486', 54, 3, 4),
	(33, b'0', b'0', '2022-11-18 07:42:25.068723', 54, 7, 4),
	(34, b'0', b'0', '2022-11-18 14:57:21.134094', 57, 9, 6),
	(36, b'1', b'1', '2022-11-18 14:57:21.141200', 57, 3, 6),
	(37, b'1', b'1', '2022-11-18 17:10:40.559961', 60, 3, 6),
	(38, b'0', b'1', '2022-11-19 10:43:20.737712', 63, 3, 4),
	(39, b'0', b'0', '2022-11-19 10:54:54.062472', 65, 8, 4),
	(40, b'0', b'1', '2022-11-19 10:54:54.069332', 65, 3, 4),
	(41, b'0', b'0', '2022-11-19 11:09:27.958464', 67, 2, 3),
	(42, b'0', b'1', '2022-11-19 13:36:59.531984', 69, 3, 4),
	(43, b'0', b'0', '2022-11-19 13:36:59.541493', 69, 7, 4),
	(44, b'0', b'0', '2022-11-19 13:36:59.545688', 69, 8, 4),
	(45, b'0', b'0', '2022-11-20 09:08:45.950092', 74, 7, 3),
	(51, b'0', b'0', '2022-11-20 14:28:27.445300', 78, 4, 3);

-- 테이블 test.study 구조 내보내기
CREATE TABLE IF NOT EXISTS `study` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activation_level` int(11) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `threshold` int(11) NOT NULL,
  `upper_limit` int(11) NOT NULL,
  `visibility` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.study:~18 rows (대략적) 내보내기
INSERT INTO `study` (`id`, `activation_level`, `created_date`, `introduction`, `level`, `name`, `threshold`, `upper_limit`, `visibility`) VALUES
	(1, 3, '2022-11-17 04:27:58.456389', '재밌는 일들이 많아지는 스터디입니다.', 0, '백준 플래티넘 찍기', 3, 5, 0),
	(3, 3, '2022-11-17 04:28:16.366670', '파이썬으로 하는 알고리즘 스터디 꾸준히 참여하실 분 구합니다.', 0, '파이썬으로 알고리즘 공부하는 모임', 1, 5, 0),
	(4, 3, '2022-11-17 04:28:25.321143', '🎈삼성역량테스트 B형 대비 스터디입니다!/ 취득을 위해 고난도의 문제를 풀 예정이에요!', 0, '삼성역량테스트 B형 대비 스터디', 11, 4, 1),
	(5, 3, '2022-11-17 04:28:59.285670', '안녕하세요 카카오 공채 대비 알고리즘 스터디입니다.', 0, '카카오 대비 알고리즘 스터디', 3, 6, 0),
	(6, 3, '2022-11-17 04:29:05.971221', '삼성전자 코테 대비 스터디입니다. 같이 하실 분 구합니다.', 0, '삼성전자 코테 대비 스터디', 1, 5, 0),
	(8, 3, '2022-11-17 04:47:38.172932', '123', 0, '자바스크립트로 알고리즘 풀어보실분!', 2, 3, 1),
	(13, 3, '2022-11-17 05:03:40.947540', 't\ne\ns\nt\nt\ne\ns\nt\n', 0, 'JS로 알고리즘', 1, 2, 1),
	(14, 3, '2022-11-17 05:22:53.209005', 'solvedac 루비를 향해!', 0, 'Solvedac 스터디 하실분~', 1, 5, 0),
	(15, 3, '2022-11-17 06:16:46.285454', '무등산 등산보다 어려운 알고리즘 정복', 0, '알고리즘 정상을 향해 달리는 스터디', 6, 6, 1),
	(16, 3, '2022-11-17 06:20:18.719404', '뭐요', 0, '알고세상', 1, 2, 0),
	(18, 3, '2022-11-17 07:50:39.193584', '✨공룡 알디와 함께하는 알고리즘 스터디✨\n디스코드 : https://discord.gg/kzHBXzbK', 0, '알디와 함께하는 스터디', 1, 5, 1),
	(19, 2, '2022-11-18 00:00:00.684128', '알고풀어용', 0, '알고알고', 11, 6, 1),
	(20, 2, '2022-11-18 02:26:43.165971', '카카오톡 오픈채팅방 : ansdkgjkl', 0, '코드리뷰 연습하기!', 1, 4, 1),
	(21, 2, '2022-11-18 03:29:36.192961', '비공개 스터디 입니다.', 0, '자료구조 위주로 스터디 하실분', 3, 5, 0),
	(22, 2, '2022-11-18 06:15:09.930318', 'ㄷㄱ', 0, 'SQL 스터디', 11, 6, 0),
	(23, 0, '2022-11-18 07:19:00.647121', 'https://open.kakao.com/o/gYq00Gs\n\n자바로 알고리즘 문제 풀이하는 스터디입니다.\n주 2회 진행됩니다.', 5, '자바로 알고리즘 정복', 4, 5, 0),
	(24, 4, '2022-11-18 07:20:45.788799', 'https://open.kakao.com/o/ske32\n\n알고리즘 모험가~ 탐험을 함께 하실 분 구합니다.\n', 15, '알고리즘 모험가', 3, 5, 1),
	(26, 5, '2022-11-19 11:15:25.460052', '코드리뷰를 극한으로 해봅시다!', 30, '코드리뷰전문스터디', 1, 5, 0);

-- 테이블 test.tag_of_problem 구조 내보내기
CREATE TABLE IF NOT EXISTS `tag_of_problem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `problem_tag` varchar(255) DEFAULT NULL,
  `problem_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbkwxd7r89pg3q7bp7fw4nssh1` (`problem_id`),
  CONSTRAINT `FKbkwxd7r89pg3q7bp7fw4nssh1` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=utf8mb4;

-- 테이블 데이터 test.tag_of_problem:~117 rows (대략적) 내보내기
INSERT INTO `tag_of_problem` (`id`, `problem_tag`, `problem_id`) VALUES
	(1, 'data_structures', 1),
	(2, 'deque', 1),
	(3, 'implementation', 1),
	(4, 'queue', 1),
	(5, 'simulation', 1),
	(6, 'implementation', 2),
	(7, 'simulation', 2),
	(9, 'bfs', 4),
	(10, 'graphs', 4),
	(11, 'graph_traversal', 4),
	(12, 'implementation', 4),
	(13, 'simulation', 4),
	(14, 'bfs', 5),
	(15, 'graphs', 5),
	(16, 'graph_traversal', 5),
	(17, 'implementation', 5),
	(18, 'simulation', 5),
	(19, 'backtracking', 6),
	(20, 'bruteforcing', 6),
	(21, 'implementation', 6),
	(22, 'simulation', 6),
	(23, 'data_structures', 7),
	(24, 'implementation', 7),
	(25, 'simulation', 7),
	(32, 'implementation', 10),
	(33, 'arithmetic', 10),
	(34, 'math', 10),
	(35, 'implementation', 11),
	(36, 'geometry', 11),
	(37, 'geometry', 12),
	(38, 'implementation', 12),
	(39, 'bfs', 13),
	(40, 'graphs', 13),
	(41, 'graph_traversal', 13),
	(42, 'implementation', 14),
	(43, 'implementation', 15),
	(44, 'sorting', 15),
	(45, 'bfs', 16),
	(46, 'graphs', 16),
	(47, 'graph_traversal', 16),
	(62, 'bfs', 23),
	(63, 'graphs', 23),
	(64, 'graph_traversal', 23),
	(112, 'bfs', 38),
	(113, 'graphs', 38),
	(114, 'graph_traversal', 38),
	(124, 'dfs', 42),
	(125, 'graphs', 42),
	(126, 'graph_traversal', 42),
	(127, 'trees', 42),
	(130, 'arithmetic', 44),
	(131, 'implementation', 44),
	(132, 'math', 44),
	(133, 'simulation', 44),
	(134, 'data_structures', 45),
	(135, 'hash_set', 45),
	(143, 'euclidean', 49),
	(144, 'math', 49),
	(145, 'number_theory', 49),
	(146, 'dp', 50),
	(147, 'dfs', 51),
	(148, 'graphs', 51),
	(149, 'graph_traversal', 51),
	(150, 'trees', 51),
	(152, 'binary_search', 53),
	(153, 'data_structures', 53),
	(154, 'sorting', 53),
	(155, 'implementation', 54),
	(156, 'sorting', 54),
	(159, 'divide_and_conquer', 56),
	(160, 'implementation', 56),
	(161, 'dfs', 57),
	(162, 'graphs', 57),
	(163, 'graph_traversal', 57),
	(164, 'trees', 57),
	(168, 'greedy', 59),
	(169, 'sorting', 59),
	(170, 'data_structures', 60),
	(171, 'recursion', 60),
	(172, 'stack', 60),
	(173, 'greedy', 61),
	(174, 'sorting', 61),
	(178, 'dp', 63),
	(179, 'greedy', 63),
	(180, 'math', 63),
	(181, 'greedy', 64),
	(182, 'sorting', 64),
	(183, 'binary_search', 65),
	(184, 'sorting', 65),
	(185, 'bfs', 66),
	(186, 'graphs', 66),
	(187, 'graph_traversal', 66),
	(188, 'divide_and_conquer', 67),
	(189, 'implementation', 67),
	(190, 'divide_and_conquer', 68),
	(191, 'dp', 68),
	(192, 'math', 68),
	(193, 'recursion', 68),
	(194, 'backtracking', 69),
	(195, 'implementation', 69),
	(196, 'simulation', 69),
	(197, 'greedy', 70),
	(198, 'sorting', 70),
	(229, 'bfs', 80),
	(230, 'graphs', 80),
	(231, 'graph_traversal', 80),
	(232, 'bfs', 81),
	(233, 'bruteforcing', 81),
	(234, 'graphs', 81),
	(235, 'graph_traversal', 81),
	(250, 'bfs', 86),
	(251, 'graphs', 86),
	(252, 'graph_traversal', 86),
	(253, 'bfs', 87),
	(254, 'bruteforcing', 87),
	(255, 'graphs', 87),
	(256, 'graph_traversal', 87);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
