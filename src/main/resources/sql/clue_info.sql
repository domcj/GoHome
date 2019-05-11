DROP TABLE IF EXISTS `clue_info`;
CREATE TABLE `clue_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增，主键',
  `mp_id` int(10) NOT NULL  COMMENT '失踪人ID',
  `u_id` int(10) NOT NULL COMMENT '志愿者Id',
  `lose_name` varchar(50) DEFAULT NULL COMMENT '失踪人名字',
  `lose_iphone` varchar(50) DEFAULT NULL COMMENT '失踪人电话',
  `lose_sex` varchar(50) DEFAULT NULL COMMENT '失踪人性别',
  `lose_address` varchar(200) DEFAULT NULL COMMENT '失踪人地址',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `imageUrl` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `confidence` varchar(50) DEFAULT NULL COMMENT '相似度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;