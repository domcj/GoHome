DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增，主键',
  `username` varchar(50) NOT NULL COMMENT '用户姓名',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `telPhone` varchar(50) NOT NULL COMMENT '用户电话',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '用户地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user values(1, 'domcj', 'domcj', '13535323575', 'cj5804@126.com', '湖南省邵阳市');
insert into user values(2, 'huarun', 'huarun123', '17673049327', '247440114@qq.com', '广东省深圳市');
insert into user values(3, 'test', 'test', '15576631449', '3787687322@qq.com', '广东省深圳市');