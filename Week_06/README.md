## 基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交DDL的SQL文件到Github  
```sql
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(16) DEFAULT NULL COMMENT '用户名',
  `pass` varchar(32) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `id_card` varchar(16) DEFAULT NULL COMMENT '身份证',
  `phone_no` char(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `phone_no_index` (`phone_no`) USING BTREE,
  KEY `id_card_index` (`id_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `t_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `cost_count` bigint(20) DEFAULT NULL COMMENT '金额（单位为分）',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（0：待付款、1：待发货、2：待评论、3：待退款、4：已退款、5：支付超时）',
  `snapshot` varchar(255) DEFAULT NULL COMMENT '订单快照',
  PRIMARY KEY (`order_id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE `t_order_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单商品关系表',
  `snapshot` varchar(255) DEFAULT NULL COMMENT '订单商品快照',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `good_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`),
  KEY `order_id_index` (`order_id`) USING BTREE,
  KEY `good_id_index` (`good_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品关系表';

CREATE TABLE `t_goods` (
  `id` tinyint(4) NOT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `class` tinyint(4) DEFAULT NULL COMMENT '分类（0：母婴、1：数码、2：食品、3：烟酒、4：家电）',
  `weight` bigint(20) DEFAULT NULL COMMENT '重量（单位：毫克）',
  `cost_count` bigint(20) DEFAULT NULL COMMENT '售价（单位：为分）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';
```
