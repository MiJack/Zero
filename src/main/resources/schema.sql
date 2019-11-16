----------------------
-- 用户信息表
----------------------

CREATE TABLE `Zero_User` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(60) NOT NULL COMMENT '用户姓名',
  `email` varchar(200) NOT NULL COMMENT '用户注册邮箱',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci  COMMENT='用户信息表';
