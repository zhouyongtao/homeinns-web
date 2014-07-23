/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50157
Source Host           : localhost:3306
Source Database       : ssmtset

Target Server Type    : MYSQL
Target Server Version : 50157
File Encoding         : 65001

Date: 2012-11-20 15:26:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_books`
-- ----------------------------
DROP TABLE IF EXISTS `s_books`;
CREATE TABLE `s_books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `book_name` varchar(50) NOT NULL COMMENT '书名',
  `mfr_name` varchar(20) DEFAULT NULL COMMENT '出版商',
  `book_sort` varchar(20) DEFAULT NULL COMMENT '类型',
  `book_author` varchar(25) DEFAULT NULL COMMENT '作者',
  `cost_price` float(11,3) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Records of s_books
-- ----------------------------
INSERT INTO `s_books` VALUES ('24', 'SDFG', 'FG', 'SRRRR', '', '9.000');
INSERT INTO `s_books` VALUES ('25', 'ADFA', 'DFASDF', 'ADSFA', 'ADADSFADSFADSFA', '33.000');
INSERT INTO `s_books` VALUES ('27', 'NAME11111', '22', 'dfdf', '12121', '90.000');
INSERT INTO `s_books` VALUES ('29', 'GGGG', 'GGGG', 'GGGG', 'AUT', '99.800');
INSERT INTO `s_books` VALUES ('30', 'sdfdfd', 'fdfdf', 'dfdfdfd', 'auth', '34.090');



/*MSSQL*/
CREATE TABLE [dbo].[s_books](
	[book_id] [int] IDENTITY(1,1) NOT NULL,
	[book_name] [varchar](50) NOT NULL,
	[mfr_name] [varchar](20) NULL,
	[book_sort] [varchar](20) NULL,
	[book_author] [varchar](25) NULL,
	[cost_price] [float] NULL,
 CONSTRAINT [PK_s_books] PRIMARY KEY CLUSTERED
(
	[book_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]