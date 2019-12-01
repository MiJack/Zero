# ----------------------
# 用户信息表
# ----------------------

CREATE TABLE `Zero_User` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(60) NOT NULL DEFAULT ''COMMENT '用户姓名',
  `email` varchar(200) NOT NULL DEFAULT '' COMMENT '用户注册邮箱',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci  COMMENT='用户信息表';

