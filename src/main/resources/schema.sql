# ----------------------
# 用户信息表
# ----------------------
DROP table `Zero_User`;
CREATE TABLE `Zero_User`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(60)  NOT NULL DEFAULT '' COMMENT '用户姓名',
    `email`       varchar(200) NOT NULL DEFAULT '' COMMENT '用户注册邮箱',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     int          NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息表';

DROP table `Zero_UserAuth`;

CREATE TABLE `Zero_UserAuth`
(
    `id`             bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `salt`           varchar(200)  NOT NULL DEFAULT '' COMMENT '密码对应的盐',
    `type`           int           NOT NULL DEFAULT 0 COMMENT '加密类型',
    `crypt_password` varchar(1024) NOT NULL DEFAULT '' COMMENT '加密后的密码',
    `create_time`    timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        int           NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户密码信息表';

DROP TABLE `Zero_Token`;

CREATE TABLE `Zero_Token`
(
    `id`           bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`         int           NOT NULL DEFAULT 0 COMMENT 'token 类型',
    `resource_id`  bigint        NOT NULL DEFAULT 0 COMMENT 'resourceId',
    `token`        varchar(1024) NOT NULL DEFAULT '' COMMENT 'token内容',
    `expire`       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'token过期时间',
    `token_status` int           NOT NULL DEFAULT 0 COMMENT 'token状态',
    `create_time`  timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      int           NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='token信息表';

DROP TABLE `Zero_AccountType`;

CREATE TABLE `Zero_AccountType`
(
    `id`              bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `typeName`        varchar(1024) NOT NULL DEFAULT '' COMMENT '账号类型名称',
    `accountTypeIcon` varchar(1024) NOT NULL DEFAULT '' COMMENT '账号图标',
    `billingType`     int           NOT NULL DEFAULT '0' COMMENT '账号类型',
    `create_time`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         int           NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='token信息表';
