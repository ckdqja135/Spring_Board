-- --------------------------------------------------------
-- 호스트:                          localhost
-- 서버 버전:                        10.3.11-MariaDB - mariadb.org binary distribution
-- HeidiSQL 버전:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- coco 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `coco` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `coco`;

-- 테이블 coco.bbs 구조 내보내기
CREATE TABLE IF NOT EXISTS `bbs` (
  `b_no` int(11) NOT NULL AUTO_INCREMENT,
  `b_owner` int(11) NOT NULL,
  `b_title` varchar(50) NOT NULL,
  `b_content` text NOT NULL,
  `b_regdate` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`b_no`),
  KEY `FK_bbs_user` (`b_owner`),
  CONSTRAINT `FK_bbs_user` FOREIGN KEY (`b_owner`) REFERENCES `user` (`u_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 테이블 데이터 coco.bbs:~0 rows (대략적) 내보내기
DELETE FROM `bbs`;
/*!40000 ALTER TABLE `bbs` DISABLE KEYS */;
INSERT INTO `bbs` (`b_no`, `b_owner`, `b_title`, `b_content`, `b_regdate`) VALUES
	(1, 1, '1', '2', '2018-12-06 22:00:22'),
	(2, 1, '테스트', '테스트', '2018-12-06 22:10:26');
/*!40000 ALTER TABLE `bbs` ENABLE KEYS */;

-- 테이블 coco.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `u_no` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` varchar(50) NOT NULL,
  `u_pw` varchar(255) NOT NULL,
  PRIMARY KEY (`u_no`),
  UNIQUE KEY `u_id` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 테이블 데이터 coco.user:~2 rows (대략적) 내보내기
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`u_no`, `u_id`, `u_pw`) VALUES
	(1, '1', '2'),
	(2, '2', '2');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
