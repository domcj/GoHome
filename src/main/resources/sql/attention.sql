DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention` (
  `u_id` int(10) NOT NULL COMMENT '用户Id',
  `mp_id` int(10) NOT NULL  COMMENT '失踪人ID',
) ENGINE=InnoDB DEFAULT CHARSET=utf8;