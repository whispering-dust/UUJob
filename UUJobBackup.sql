/*
 Navicat Premium Data Transfer

 Source Server         : UUJob
 Source Server Type    : MySQL
 Source Server Version : 50650 (5.6.50-log)
 Source Host           : 110.42.189.226:3306
 Source Schema         : UUJob

 Target Server Type    : MySQL
 Target Server Version : 50650 (5.6.50-log)
 File Encoding         : 65001

 Date: 01/01/2023 18:23:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
  `profile_id` int(11) NOT NULL,
  `recruittable_id` int(11) NOT NULL,
  `date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`profile_id`, `recruittable_id`) USING BTREE,
  INDEX `recruittableid`(`recruittable_id`) USING BTREE,
  CONSTRAINT `profileid` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `recruittableid` FOREIGN KEY (`recruittable_id`) REFERENCES `recruittable` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES (18, 19, '2022-12-08 22:05:41');
INSERT INTO `application` VALUES (19, 2, '2022-12-09 09:26:55');
INSERT INTO `application` VALUES (20, 2, '2022-12-09 09:29:46');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` datetime NULL DEFAULT NULL,
  `publiser_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `publisher`(`publiser_id`) USING BTREE,
  INDEX `post`(`post_id`) USING BTREE,
  CONSTRAINT `post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `publisher` FOREIGN KEY (`publiser_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`(191)) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of company
-- ----------------------------

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `publisher_id` int(11) NOT NULL,
  `date` datetime NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `publiser`(`publisher_id`) USING BTREE,
  CONSTRAINT `publiser` FOREIGN KEY (`publisher_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` tinyint(4) NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `edu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '简历信息',
  `seeker_id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sid`(`seeker_id`) USING BTREE,
  CONSTRAINT `sid` FOREIGN KEY (`seeker_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of profile
-- ----------------------------
INSERT INTO `profile` VALUES (18, 20, '男', '1347277022@qq.com', '大专', '同济第一外卖员', 1040, '磨尖吻');
INSERT INTO `profile` VALUES (19, 0, '', '', '', '', 12, '莫健文');
INSERT INTO `profile` VALUES (20, 21, '男', '2051964@tongji.edu.cn', '本科', 'i am test person', 1043, 'mjw');

-- ----------------------------
-- Table structure for recruittable
-- ----------------------------
DROP TABLE IF EXISTS `recruittable`;
CREATE TABLE `recruittable`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `salary` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '招聘的薪资范围',
  `date` datetime NULL DEFAULT NULL,
  `status` tinyint(4) NOT NULL COMMENT '表示招聘信息的状态，0为未审核，1为已审核',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位的招聘介绍',
  `publisher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid`(`publisher_id`) USING BTREE,
  CONSTRAINT `pid` FOREIGN KEY (`publisher_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of recruittable
-- ----------------------------
INSERT INTO `recruittable` VALUES (2, '上海宠物店招聘兽医', '兽医', '5k-8k', '2008-09-02 22:14:35', 1, 'Veterinarian', 11);
INSERT INTO `recruittable` VALUES (3, '北京动物园招聘饲养员', '饲养员', '8k', '2019-01-13 02:44:48', 0, 'Breeder', 10);
INSERT INTO `recruittable` VALUES (7, '华莱士广东地区销售经理', '销售经理', '10k-12k', '2021-07-09 06:25:58', 1, 'Sales manager', 5);
INSERT INTO `recruittable` VALUES (19, '上海外卖员招聘', '外卖员', '10k-12k', '2022-12-01 08:54:09', 1, '风里雨里，外卖等你', 11);
INSERT INTO `recruittable` VALUES (20, '外卖员', '外卖员', '10k-12k', '2022-12-08 03:02:10', 1, '风里雨里，外卖等你', 11);
INSERT INTO `recruittable` VALUES (23, '标题', '游戏架构师', '30w-50w', '2022-12-09 09:33:23', 1, '详细描述', 11);
INSERT INTO `recruittable` VALUES (24, '上猎', '代打', '100w', '2022-12-24 20:31:47', 1, '暴打pite', 11);

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NOT NULL,
  `reporter_id` int(11) NOT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reporter`(`reporter_id`) USING BTREE,
  INDEX `postID`(`post_id`) USING BTREE,
  CONSTRAINT `postID` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reporter` FOREIGN KEY (`reporter_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of report
-- ----------------------------

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star`  (
  `user_id` int(11) NOT NULL,
  `recruittable_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `recruittable`(`recruittable_id`) USING BTREE,
  CONSTRAINT `recruittable` FOREIGN KEY (`recruittable_id`) REFERENCES `recruittable` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of star
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` tinyint(4) NOT NULL COMMENT '不同数字表示不同身份，0为求职者，1为招聘者',
  `company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `position` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1044 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, 'ouINfGYI6T', 'j5AT7WZZ8n', 'Deng Yunxi', '838-276-9163', 1, 'Lee Brothers Technology Inc.', '零售助理');
INSERT INTO `user` VALUES (4, 'up2hMkmf9T', 'aruAzbhFqq', 'Wong Ming Sze', '3-9407-0929', 1, 'Kinoshita Corporation', '美容师');
INSERT INTO `user` VALUES (5, 'gc085SO2Q3', 'dIPbdWaQA3', 'Dai Kar Yan', '330-649-4385', 1, 'Jesse Inc.', '网页开发人员');
INSERT INTO `user` VALUES (6, 'nCbNbYH2UA', 'hCzK6WEq4c', 'Shibata Kazuma', '52-801-7462', 0, 'Rui Technology Company Limited', '财务分析师');
INSERT INTO `user` VALUES (7, 'fkQZ7YKvaN', '9D9Y1z2XMH', 'Chung Lik Sun', '74-544-3675', 1, 'Victor Logistic Inc.', '教授');
INSERT INTO `user` VALUES (8, '9LvLbhNkgR', 'TMFeIa9pt4', 'Willie Butler', '80-1510-6433', 0, 'Joan Pharmaceutical Inc.', '农夫');
INSERT INTO `user` VALUES (9, 'Kzpb3ko5ro', 'Hv4tTK6J0f', 'Mo Hiu Tung', '21-3884-5602', 1, 'Duan Kee Company Limited', '活动经理');
INSERT INTO `user` VALUES (10, 'BXivz4472C', 'c2g56Sgw7v', 'Long Yunxi', '330-079-6488', 1, 'Xie Kee Electronic Company Limited', '舞蹈演员');
INSERT INTO `user` VALUES (11, '6767', '123456', '莫健文', '13622200901', 1, '腾讯', 'CEO');
INSERT INTO `user` VALUES (12, '6969', '123456', '莫健文', '13622200901', 0, '阿里巴巴', 'CEO');
INSERT INTO `user` VALUES (1040, '1111', '123456', '磨尖吻', '205', 0, '网易', 'HR');
INSERT INTO `user` VALUES (1043, '123456667', '123456', 'mjw', '13726921', 0, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
