/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50617
Source Host           : 127.0.0.1:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-08-10 21:54:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) DEFAULT NULL COMMENT '//房主id',
  `room_number` int(11) DEFAULT NULL COMMENT '//房间号',
  `number` int(11) DEFAULT NULL COMMENT '//房间人数',
  `expries_time` bigint(20) DEFAULT NULL COMMENT '//过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('12', 'oc6fGwjf-OUlebNC1L0uQlXOsk-0', '4170', '6', '1502374920169');

-- ----------------------------
-- Table structure for room_play
-- ----------------------------
DROP TABLE IF EXISTS `room_play`;
CREATE TABLE `room_play` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//玩家id',
  `play_id` varchar(255) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL COMMENT '房间id',
  `identify` int(11) DEFAULT NULL COMMENT '//玩家身份 1平民 2卧底',
  `number` int(11) DEFAULT NULL COMMENT '//玩家号码',
  `word` varchar(255) DEFAULT NULL COMMENT '//玩家词汇',
  `expries_time` bigint(11) DEFAULT NULL COMMENT '//房间过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6188 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room_play
-- ----------------------------
INSERT INTO `room_play` VALUES ('6176', null, '12', '2', '1', '图书店', '1502371320257');
INSERT INTO `room_play` VALUES ('6177', null, '12', '1', '2', '图书馆', '1502371320405');
INSERT INTO `room_play` VALUES ('6178', null, '12', '1', '3', '图书馆', '1502371320470');
INSERT INTO `room_play` VALUES ('6179', null, '12', '2', '4', '图书店', '1502371320523');
INSERT INTO `room_play` VALUES ('6180', null, '12', '1', '5', '图书馆', '1502371320580');
INSERT INTO `room_play` VALUES ('6181', null, '12', '1', '6', '图书馆', '1502371320634');
INSERT INTO `room_play` VALUES ('6182', null, '12', '1', '1', '橙子', '1502374920169');
INSERT INTO `room_play` VALUES ('6183', null, '12', '1', '2', '橙子', '1502374920169');
INSERT INTO `room_play` VALUES ('6184', null, '12', '1', '3', '橙子', '1502374920169');
INSERT INTO `room_play` VALUES ('6185', null, '12', '2', '4', '橘子', '1502374920169');
INSERT INTO `room_play` VALUES ('6186', null, '12', '1', '5', '橙子', '1502374920169');
INSERT INTO `room_play` VALUES ('6187', null, '12', '2', '6', '橘子', '1502374920169');

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) DEFAULT NULL,
  `expires_in` int(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `status` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of token
-- ----------------------------
INSERT INTO `token` VALUES ('2', '5uP7oU0Ls9C03UbmkP7WPE2cpW0Ul4OpD95OWog2GqpdFZl8yDt_QJLysNInKLAbYE4TNSSG-atosz17QnB-tMF9xlc19RSdNvORyu4Y3MhOaD-4Snm4Zi5Hn96Y0MfBIJZaAAATUK', '7200', '150', '1');
INSERT INTO `token` VALUES ('3', 'iYQaPImCWT95KgWCr90tJKXHMcMMOdBfiZCLs69nYFrUvHSRHqlS0GTAvTi51kKu3Q3GBvklfxTkelWR2sF1Ze7uILxEWrNyAM-8ZQF7fUWwIKY_-KB-gfNhZp6G1ScWMTRcAFACJH', '7200', '1502016154111', '1');
INSERT INTO `token` VALUES ('4', 'Ik-4zxVYjcioR8Gwt9rUueP1yJgcDGOpnL7dZ2fXUmlwq0BmXbaDWqooyyPPBpAePk-zU6Nb6Osxh9VAHeqaZGzJ31seWTGlN3wu_Hcedk3vUyoKNMcV3PMxje0V9TQKSQRbAGAOMN', '7200', '1502019916338', '1');
INSERT INTO `token` VALUES ('5', 'rkkvq6vadII2xz0yqKn85cRLQdiRaCGUeA61zu31tdyRqJ2qRqDZfdAVf78yuR9FFBM3aMhi6ddLybyjQJOMF7zN12tmOGRCdow6Vzapg70FWPaAEAOFY', '7200', '1502020055242', '1');
INSERT INTO `token` VALUES ('6', 'z6c_TtmvSO22sym-arVcG1KDrm10b4XIS7vBnVHcckWLi2ku6no3zzNftz-AZWtVclIctQdw-vrPIkPpbkM7beGcNtPcFmyPmXy4UmRDXNuitouGys0IQckPO6rcI9koOLSgABATQX', '7200', '1502020269470', '2');
INSERT INTO `token` VALUES ('7', 'iLSuWs_bdcwBvQA0p-tRGaMr8rElmxtkcpP7NYmNV7iSviobIXl1gh3_RB8-0WNzq0xoBFtAzichkEw6uFzWiuXXoa9Arptd2VM5kc6eSfJh1Pv-ACRFItMQ0Qc2Liq3SOFbAJAWSJ', '7200', '1502022032174', '2');
INSERT INTO `token` VALUES ('8', 'dHKEQYOALP-vIin7sPIErs2IyEqA94PCh-Dm4ufUdTx1xuhSKuXEBeCqRTM1J2HSSWmYk_jWk4xeShWvOayPzQX9gnopuDekWE2HRwWVmlPvkzftDbDfVEAIGekMPBRIFNNiAHAKSS', '7200', '1502023520947', '1');
INSERT INTO `token` VALUES ('9', 'pl5CMp5yJfEAr6OQH9fltT7XQIvYqaC6jpu7yZooYbtDWlyWhjbOdrbkNvpQhwXktleFahB7p-v4kqPN4vU9V0gQQgGPvTJjPDC-2F1_g-Vl1znU7F7mGsbGPS7IjreYKENbAGAVPY', '7200', '1502080952692', '1');
INSERT INTO `token` VALUES ('10', 'oavvmE3qTn9aZSEe3h0s4OwRITuZ0dAUMJ_JEXAynXTcswWYcCtYz0qIsxBWGI7rkZIa4SMG5hGJ7kyiRYbYc9Mx6zPmSw6Z6p-gmV5kNP50GO9lHHuUPDQK2iVlInLWCZOhAJAGDS', '7200', '1502102335275', '1');
INSERT INTO `token` VALUES ('11', 'ske12-K7oRn7vFt5b4t2MIXr9OdFFeD3acxKsNhDykv78Tz5KrinIf_UKRzyB3rUCCIql30N4WJme_36nmnV7DYyVvs7L7eB8bneN9WDnkPVdwpo4fEbVKkwyRgu-B7-RXSeAIABHC', '7200', '1502180004975', '1');

-- ----------------------------
-- Table structure for words
-- ----------------------------
DROP TABLE IF EXISTS `words`;
CREATE TABLE `words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word_one` varchar(255) DEFAULT NULL,
  `word_two` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of words
-- ----------------------------
INSERT INTO `words` VALUES ('1', '镜子', '玻璃', '1502112185558', '1');
INSERT INTO `words` VALUES ('2', '玉米', '小米', '1502112197184', '1');
INSERT INTO `words` VALUES ('3', '反射', '折射', '1502112197343', '1');
INSERT INTO `words` VALUES ('4', '穿衣', '试衣', '1502112197579', '1');
INSERT INTO `words` VALUES ('5', '梦境', '幻想', '1502112197723', '1');
INSERT INTO `words` VALUES ('6', '屌丝', '宅男', '1502112197875', '1');
INSERT INTO `words` VALUES ('7', '推销', '销售', '1502112197968', '1');
INSERT INTO `words` VALUES ('8', '淘宝', '拍拍', '1502112198045', '1');
INSERT INTO `words` VALUES ('9', '妩媚', '百媚', '1502112198146', '1');
INSERT INTO `words` VALUES ('10', '盒饭', '外卖', '1502112198246', '1');
INSERT INTO `words` VALUES ('11', '推销', '销售', '1502112198312', '1');
INSERT INTO `words` VALUES ('12', '淘宝', '拍拍', '1502112198400', '1');
INSERT INTO `words` VALUES ('13', '叉烧包', '灌汤包', '1502112198500', '1');
INSERT INTO `words` VALUES ('14', '散热器', '电风扇', '1502112198589', '1');
INSERT INTO `words` VALUES ('15', '伐木工', '木匠工', '1502112198679', '1');
INSERT INTO `words` VALUES ('16', '点烟器', '打火机', '1502112198744', '1');
INSERT INTO `words` VALUES ('17', '热的快', '热水器', '1502112198822', '1');
INSERT INTO `words` VALUES ('18', '榨菜丝', '萝卜头', '1502112198933', '1');
INSERT INTO `words` VALUES ('19', '老朋友', '老男孩', '1502112199022', '1');
INSERT INTO `words` VALUES ('20', '干洗机', '甩干机', '1502112199106', '1');
INSERT INTO `words` VALUES ('21', '王菲', '那英', '1502112199189', '1');
INSERT INTO `words` VALUES ('22', '元芳', '展昭', '1502112199277', '1');
INSERT INTO `words` VALUES ('23', '麻雀', '乌鸦', '1502112199388', '1');
INSERT INTO `words` VALUES ('24', '胖子', '肥肉', '1502112199439', '1');
INSERT INTO `words` VALUES ('25', '眉毛', '胡须', '1502112199532', '1');
INSERT INTO `words` VALUES ('26', '何炅', '维嘉', '1502112199595', '1');
INSERT INTO `words` VALUES ('27', '状元', '冠军', '1502112199650', '1');
INSERT INTO `words` VALUES ('28', '饺子', '包子', '1502112199732', '1');
INSERT INTO `words` VALUES ('29', '端午节', '中秋节', '1502112199798', '1');
INSERT INTO `words` VALUES ('30', '摩托车', '电动车', '1502112199865', '1');
INSERT INTO `words` VALUES ('31', '高跟鞋', '增高鞋', '1502112200039', '1');
INSERT INTO `words` VALUES ('32', '汉堡包', '肉夹馍', '1502112200120', '1');
INSERT INTO `words` VALUES ('33', '小矮人', '葫芦娃', '1502112200199', '1');
INSERT INTO `words` VALUES ('34', '蜘蛛侠', '蜘蛛精', '1502112200287', '1');
INSERT INTO `words` VALUES ('35', '节节高升', '票房大卖', '1502112200365', '1');
INSERT INTO `words` VALUES ('36', '反弹琵琶', '乱弹棉花', '1502112200431', '1');
INSERT INTO `words` VALUES ('37', '玫瑰', '月季', '1502112200498', '1');
INSERT INTO `words` VALUES ('38', '董永', '许仙', '1502112200575', '1');
INSERT INTO `words` VALUES ('39', '若曦', '晴川', '1502112200639', '1');
INSERT INTO `words` VALUES ('40', '谢娜', '李湘', '1502112200704', '1');
INSERT INTO `words` VALUES ('41', '孟非', '乐嘉', '1502112200764', '1');
INSERT INTO `words` VALUES ('42', '牛奶', '豆浆', '1502112200853', '1');
INSERT INTO `words` VALUES ('43', '保安', '保镖', '1502112200942', '1');
INSERT INTO `words` VALUES ('44', '白菜', '生菜', '1502112200993', '1');
INSERT INTO `words` VALUES ('45', '辣椒', '芥末', '1502112201082', '1');
INSERT INTO `words` VALUES ('46', '金庸', '古龙', '1502112201397', '1');
INSERT INTO `words` VALUES ('47', '赵敏', '黄蓉', '1502112201475', '1');
INSERT INTO `words` VALUES ('48', '海豚', '海狮', '1502112201570', '1');
INSERT INTO `words` VALUES ('49', '水盆', '水桶', '1502112201664', '1');
INSERT INTO `words` VALUES ('50', '唇膏', '口红', '1502112201797', '1');
INSERT INTO `words` VALUES ('51', '森马', '以纯', '1502112201930', '1');
INSERT INTO `words` VALUES ('52', '烤肉', '涮肉', '1502112202002', '1');
INSERT INTO `words` VALUES ('53', '气泡', '水泡', '1502112202084', '1');
INSERT INTO `words` VALUES ('54', '纸巾', '手帕', '1502112202173', '1');
INSERT INTO `words` VALUES ('55', '杭州', '苏州', '1502112202247', '1');
INSERT INTO `words` VALUES ('56', '香港', '台湾', '1502112202313', '1');
INSERT INTO `words` VALUES ('57', '首尔', '东京', '1502112202382', '1');
INSERT INTO `words` VALUES ('58', '橙子', '橘子', '1502112202462', '1');
INSERT INTO `words` VALUES ('59', '葡萄', '提子', '1502112202528', '1');
INSERT INTO `words` VALUES ('60', '太监', '人妖', '1502112202568', '1');
INSERT INTO `words` VALUES ('61', '蝴蝶', '蜜蜂', '1502112202606', '1');
INSERT INTO `words` VALUES ('62', '小品', '话剧', '1502112202646', '1');
INSERT INTO `words` VALUES ('63', '裸婚', '闪婚', '1502112202717', '1');
INSERT INTO `words` VALUES ('64', '新年', '跨年', '1502112202783', '1');
INSERT INTO `words` VALUES ('65', '吉他', '琵琶', '1502112202846', '1');
INSERT INTO `words` VALUES ('66', '公交', '地铁', '1502112202923', '1');
INSERT INTO `words` VALUES ('67', '剩女', '御姐', '1502112203160', '1');
INSERT INTO `words` VALUES ('68', '童话', '神话', '1502112203249', '1');
INSERT INTO `words` VALUES ('69', '作家', '编剧', '1502112203306', '1');
INSERT INTO `words` VALUES ('70', '警察', '捕快', '1502112203383', '1');
INSERT INTO `words` VALUES ('71', '结婚', '订婚', '1502112203478', '1');
INSERT INTO `words` VALUES ('72', '奖牌', '金牌', '1502112203545', '1');
INSERT INTO `words` VALUES ('73', '孟飞', '乐嘉', '1502112203605', '1');
INSERT INTO `words` VALUES ('74', '那英', '韩红', '1502112203682', '1');
INSERT INTO `words` VALUES ('75', '面包', '蛋糕', '1502112204082', '1');
INSERT INTO `words` VALUES ('76', '作文', '论文', '1502112204393', '1');
INSERT INTO `words` VALUES ('77', '油条', '麻花', '1502112204714', '1');
INSERT INTO `words` VALUES ('78', '壁纸', '贴画', '1502112204998', '1');
INSERT INTO `words` VALUES ('79', '枕头', '抱枕', '1502112205175', '1');
INSERT INTO `words` VALUES ('80', '手机', '座机', '1502112205346', '1');
INSERT INTO `words` VALUES ('81', '同学', '同桌', '1502112205442', '1');
INSERT INTO `words` VALUES ('82', '婚纱', '喜服', '1502112205498', '1');
INSERT INTO `words` VALUES ('83', '老佛爷', '老天爷', '1502112205535', '1');
INSERT INTO `words` VALUES ('84', '魔术师', '魔法师', '1502112205624', '1');
INSERT INTO `words` VALUES ('85', '鸭舌帽', '遮阳帽', '1502112205719', '1');
INSERT INTO `words` VALUES ('86', '双胞胎', '龙凤胎', '1502112205812', '1');
INSERT INTO `words` VALUES ('87', '情人节', '光棍节', '1502112205885', '1');
INSERT INTO `words` VALUES ('88', '丑小鸭', '灰姑娘', '1502112206023', '1');
INSERT INTO `words` VALUES ('89', '富二代', '高富帅', '1502112206096', '1');
INSERT INTO `words` VALUES ('90', '生活费', '零花钱', '1502112206134', '1');
INSERT INTO `words` VALUES ('91', '麦克风', '扩音器', '1502112206174', '1');
INSERT INTO `words` VALUES ('92', '郭德纲', '周立波', '1502112206212', '1');
INSERT INTO `words` VALUES ('93', '图书馆', '图书店', '1502112206274', '1');
INSERT INTO `words` VALUES ('94', '男朋友', '前男友', '1502112206443', '1');
INSERT INTO `words` VALUES ('95', '洗衣粉', '皂角粉', '1502112206500', '1');
INSERT INTO `words` VALUES ('96', '牛肉干', '猪肉脯', '1502112206567', '1');
INSERT INTO `words` VALUES ('97', '泡泡糖', '棒棒糖', '1502112206633', '1');
INSERT INTO `words` VALUES ('98', '小沈阳', '宋小宝', '1502112206767', '1');
INSERT INTO `words` VALUES ('99', '土豆粉', '酸辣粉', '1502112206895', '1');
INSERT INTO `words` VALUES ('100', '蜘蛛侠', '蝙蝠侠', '1502112206963', '1');
INSERT INTO `words` VALUES ('101', '口香糖', '木糖醇', '1502112207045', '1');
INSERT INTO `words` VALUES ('102', '酸菜鱼', '水煮鱼', '1502112207132', '1');
INSERT INTO `words` VALUES ('103', '小笼包', '灌汤包', '1502112207266', '1');
INSERT INTO `words` VALUES ('104', '薰衣草', '满天星', '1502112207321', '1');
INSERT INTO `words` VALUES ('105', '张韶涵', '王心凌', '1502112207399', '1');
INSERT INTO `words` VALUES ('106', '刘诗诗', '刘亦菲', '1502112207529', '1');
INSERT INTO `words` VALUES ('107', '甄嬛传', '红楼梦', '1502112207599', '1');
INSERT INTO `words` VALUES ('108', '甄子丹', '李连杰', '1502112207676', '1');
INSERT INTO `words` VALUES ('109', '包青天', '狄仁杰', '1502112207750', '1');
INSERT INTO `words` VALUES ('110', '大白兔', '金丝猴', '1502112207909', '1');
INSERT INTO `words` VALUES ('111', '果粒橙', '鲜橙多', '1502112208096', '1');
INSERT INTO `words` VALUES ('112', '沐浴露', '沐浴盐', '1502112208176', '1');
INSERT INTO `words` VALUES ('113', '洗发露', '护发素', '1502112208420', '1');
INSERT INTO `words` VALUES ('114', '自行车', '电动车', '1502112208509', '1');
INSERT INTO `words` VALUES ('115', '班主任', '辅导员', '1502112208608', '1');
INSERT INTO `words` VALUES ('116', '过山车', '碰碰车', '1502112208675', '1');
INSERT INTO `words` VALUES ('117', '铁观音', '碧螺春', '1502112208775', '1');
INSERT INTO `words` VALUES ('118', '十面埋伏', '四面楚歌', '1502112208908', '1');
INSERT INTO `words` VALUES ('119', '成吉思汗', '努尔哈赤', '1502112209042', '1');
INSERT INTO `words` VALUES ('120', '谢娜张杰', '邓超孙俪', '1502112209315', '1');
INSERT INTO `words` VALUES ('121', '福尔摩斯', '工藤新一', '1502112209441', '1');
INSERT INTO `words` VALUES ('122', '贵妃醉酒', '黛玉葬花', '1502112209692', '1');
INSERT INTO `words` VALUES ('123', '流星花园', '花样男子', '1502112209791', '1');
INSERT INTO `words` VALUES ('124', '神雕侠侣', '天龙八部', '1502112209847', '1');
INSERT INTO `words` VALUES ('125', '天天向上', '非诚勿扰', '1502112209980', '1');
INSERT INTO `words` VALUES ('126', '勇往直前', '全力以赴', '1502112210162', '1');
INSERT INTO `words` VALUES ('127', '鱼香肉丝', '四喜丸子', '1502112210440', '1');
INSERT INTO `words` VALUES ('128', '麻婆豆腐', '皮蛋豆腐', '1502112210717', '1');
INSERT INTO `words` VALUES ('129', '语无伦次', '词不达意', '1502112210883', '1');
INSERT INTO `words` VALUES ('130', '鼠目寸光', '井底之蛙', '1502112211138', '1');
INSERT INTO `words` VALUES ('131', '近视眼镜', '隐形眼镜', '1502112211215', '1');
INSERT INTO `words` VALUES ('132', '美人心计', '倾世皇妃', '1502112211312', '1');
INSERT INTO `words` VALUES ('133', '夏家三千金', '爱情睡醒了', '1502112211416', '1');
INSERT INTO `words` VALUES ('134', '降龙十八掌', '九阴白骨爪', '1502112211683', '1');
INSERT INTO `words` VALUES ('135', '红烧牛肉面', '香辣牛肉面', '1502112211755', '1');
INSERT INTO `words` VALUES ('136', '江南style', '最炫民族风', '1502112211823', '1');
INSERT INTO `words` VALUES ('137', '梁山伯与祝英台', '罗密欧与朱丽叶', '1502112211933', '1');
INSERT INTO `words` VALUES ('138', '气泡', '水泡', '1502112212026', '1');
INSERT INTO `words` VALUES ('139', '老佛爷', '老天爷', '1502112212293', '1');
INSERT INTO `words` VALUES ('140', '十面埋伏', '四面楚歌', '1502112212399', '1');
INSERT INTO `words` VALUES ('141', '纸巾', '手帕', '1502112212481', '1');
INSERT INTO `words` VALUES ('142', '魔术师', '魔法师', '1502112212548', '1');
INSERT INTO `words` VALUES ('143', '成吉思汗', '努尔哈赤', '1502112212636', '1');
INSERT INTO `words` VALUES ('144', '杭州', '苏州', '1502112212688', '1');
INSERT INTO `words` VALUES ('145', '鸭舌帽', '遮阳帽', '1502112212803', '1');
INSERT INTO `words` VALUES ('146', '谢娜张杰', '邓超孙俪', '1502112213014', '1');
INSERT INTO `words` VALUES ('147', '香港', '台湾', '1502112213192', '1');
INSERT INTO `words` VALUES ('148', '双胞胎', '龙凤胎', '1502112213445', '1');
INSERT INTO `words` VALUES ('149', '福尔摩斯', '工藤新一', '1502112213508', '1');
INSERT INTO `words` VALUES ('150', '首尔', '东京', '1502112213590', '1');
INSERT INTO `words` VALUES ('151', '情人节', '光棍节', '1502112213668', '1');
INSERT INTO `words` VALUES ('152', '贵妃醉酒', '黛玉葬花', '1502112213768', '1');
INSERT INTO `words` VALUES ('153', '橙子', '橘子', '1502112213830', '1');
INSERT INTO `words` VALUES ('154', '丑小鸭', '灰姑娘', '1502112213908', '1');
INSERT INTO `words` VALUES ('155', '流星花园', '花样男子', '1502112213996', '1');
INSERT INTO `words` VALUES ('156', '葡萄', '提子', '1502112214112', '1');
INSERT INTO `words` VALUES ('157', '富二代', '高富帅', '1502112214178', '1');
INSERT INTO `words` VALUES ('158', '神雕侠侣', '天龙八部', '1502112214300', '1');
INSERT INTO `words` VALUES ('159', '太监', '人妖', '1502112214389', '1');
INSERT INTO `words` VALUES ('160', '生活费', '零花钱', '1502112214463', '1');
INSERT INTO `words` VALUES ('161', '天天向上', '非诚勿扰', '1502112214500', '1');
INSERT INTO `words` VALUES ('162', '蝴蝶', '蜜蜂', '1502112214540', '1');
INSERT INTO `words` VALUES ('163', '麦克风', '扩音器', '1502112214578', '1');
INSERT INTO `words` VALUES ('164', '勇往直前', '全力以赴', '1502112214618', '1');
INSERT INTO `words` VALUES ('165', '小品', '话剧', '1502112214667', '1');
INSERT INTO `words` VALUES ('166', '郭德纲', '周立波', '1502112214740', '1');
INSERT INTO `words` VALUES ('167', '鱼香肉丝', '四喜丸子', '1502112214872', '1');
INSERT INTO `words` VALUES ('168', '裸婚', '闪婚', '1502112214955', '1');
INSERT INTO `words` VALUES ('169', '图书馆', '图书店', '1502112215043', '1');
INSERT INTO `words` VALUES ('170', '麻婆豆腐', '皮蛋豆腐', '1502112215105', '1');
INSERT INTO `words` VALUES ('171', '新年', '跨年', '1502112215188', '1');
INSERT INTO `words` VALUES ('172', '男朋友', '前男友', '1502112215283', '1');
INSERT INTO `words` VALUES ('173', '语无伦次', '词不达意', '1502112215350', '1');
INSERT INTO `words` VALUES ('174', '吉他', '琵琶', '1502112215422', '1');
INSERT INTO `words` VALUES ('175', '洗衣粉', '皂角粉', '1502112215483', '1');
INSERT INTO `words` VALUES ('176', '鼠目寸光', '井底之蛙', '1502112215638', '1');
INSERT INTO `words` VALUES ('177', '公交', '地铁', '1502112215716', '1');
INSERT INTO `words` VALUES ('178', '牛肉干', '猪肉脯', '1502112215976', '1');
INSERT INTO `words` VALUES ('179', '近视眼镜', '隐形眼镜', '1502112216066', '1');
INSERT INTO `words` VALUES ('180', '剩女', '御姐', '1502112216115', '1');
INSERT INTO `words` VALUES ('181', '泡泡糖', '棒棒糖', '1502112216316', '1');
INSERT INTO `words` VALUES ('182', '美人心计', '倾世皇妃', '1502112216542', '1');
INSERT INTO `words` VALUES ('183', '童话', '神话', '1502112216631', '1');
INSERT INTO `words` VALUES ('184', '小沈阳', '宋小宝', '1502112216681', '1');
INSERT INTO `words` VALUES ('185', '大白兔', '金丝猴', '1502112216741', '1');
INSERT INTO `words` VALUES ('186', '作家', '编剧', '1502112216781', '1');
INSERT INTO `words` VALUES ('187', '土豆粉', '酸辣粉', '1502112216886', '1');
INSERT INTO `words` VALUES ('188', '果粒橙', '鲜橙多', '1502112216964', '1');
INSERT INTO `words` VALUES ('189', '警察', '捕快', '1502112217037', '1');
INSERT INTO `words` VALUES ('190', '蜘蛛侠', '蝙蝠侠', '1502112217103', '1');
INSERT INTO `words` VALUES ('191', '沐浴露', '沐浴盐', '1502112217185', '1');
INSERT INTO `words` VALUES ('192', '结婚', '订婚', '1502112217310', '1');
INSERT INTO `words` VALUES ('193', '口香糖', '木糖醇', '1502112217374', '1');
INSERT INTO `words` VALUES ('194', '洗发露', '护发素', '1502112217460', '1');
INSERT INTO `words` VALUES ('195', '奖牌', '金牌', '1502112217628', '1');
INSERT INTO `words` VALUES ('196', '酸菜鱼', '水煮鱼', '1502112217895', '1');
INSERT INTO `words` VALUES ('197', '自行车', '电动车', '1502112217980', '1');
INSERT INTO `words` VALUES ('198', '孟飞', '乐嘉', '1502112218040', '1');
INSERT INTO `words` VALUES ('199', '小笼包', '灌汤包', '1502112218107', '1');
INSERT INTO `words` VALUES ('200', '班主任', '辅导员', '1502112218173', '1');
INSERT INTO `words` VALUES ('201', '那英', '韩红', '1502112218236', '1');
INSERT INTO `words` VALUES ('202', '薰衣草', '满天星', '1502112218295', '1');
INSERT INTO `words` VALUES ('203', '过山车', '碰碰车面包', '1502112218357', '1');
INSERT INTO `words` VALUES ('204', '张韶涵', '王心凌', '1502112218456', '1');
INSERT INTO `words` VALUES ('205', '铁观音', '碧螺春', '1502112218528', '1');
INSERT INTO `words` VALUES ('206', '作文', '论文', '1502112218590', '1');
INSERT INTO `words` VALUES ('207', '刘诗诗', '刘亦菲', '1502112218827', '1');
INSERT INTO `words` VALUES ('208', '壁纸', '贴画', '1502112218890', '1');
INSERT INTO `words` VALUES ('209', '油条', '麻花', '1502112218945', '1');
INSERT INTO `words` VALUES ('210', '甄嬛传', '红楼梦', '1502112219011', '1');
INSERT INTO `words` VALUES ('211', '枕头', '抱枕', '1502112219082', '1');
INSERT INTO `words` VALUES ('212', '甄子丹', '李连杰', '1502112219178', '1');
INSERT INTO `words` VALUES ('213', '同学', '同桌', '1502112219271', '1');
INSERT INTO `words` VALUES ('214', '手机', '座机', '1502112219377', '1');
INSERT INTO `words` VALUES ('215', '包青天', '狄仁杰', '1502112219449', '1');
INSERT INTO `words` VALUES ('216', '婚纱', '喜服', '1502112219511', '1');
