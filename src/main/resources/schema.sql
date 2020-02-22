# ----------------------
# 用户信息表
# ----------------------
DROP TABLE IF EXISTS `Zero_User`;
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

DROP TABLE IF EXISTS `Zero_UserAuth`;

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

DROP TABLE IF EXISTS `Zero_Token`;

CREATE TABLE `Zero_Token`
(
    `id`           bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`         int           NOT NULL COMMENT 'token 类型',
    `resource_id`  bigint        NOT NULL COMMENT 'resourceId',
    `token`        varchar(1024) NOT NULL COMMENT 'token内容',
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

DROP TABLE IF EXISTS `Zero_Resource`;

CREATE TABLE `Zero_Resource`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `storage_type`     int          NOT NULL COMMENT '储存类型',
    `storage_location` varchar(100) NOT NULL COMMENT '存储地址',
    `content_name`     varchar(100) NOT NULL COMMENT '资源买菜',
    `content_type`     varchar(100) NOT NULL COMMENT '内容类型',
    `content_length`   int          NOT NULL COMMENT '内容大小',
    `status`           int          NOT NULL COMMENT '资源状态',
    `md5`              varchar(100) NOT NULL COMMENT 'md5',
    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          int          NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='资源信息表';

DROP TABLE IF EXISTS `Zero_AccountType`;

CREATE TABLE `Zero_AccountType`
(
    `id`                bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type_name`         varchar(1024) NOT NULL COMMENT '账号类型名称',
    `account_type_icon` bigint        NOT NULL COMMENT '账号图标',
    `billing_type`      int           NOT NULL COMMENT '账号类型',
    `create_time`       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           int           NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='token信息表';

DROP TABLE IF EXISTS `Zero_UserAccount`;


CREATE TABLE `Zero_UserAccount`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`         bigint       NOT NULL COMMENT '用户id',
    `account_type_id` bigint       NOT NULL COMMENT '账号类型id',
    `title`           varchar(200) NOT NULL COMMENT '账号名称',
    `number`          varchar(200) NOT NULL COMMENT '账号编号',
    `create_time`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         int          NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户账户表';



DROP TABLE IF EXISTS `Zero_UserActivity`;

CREATE TABLE `Zero_UserActivity`
(
    `id`            bigint        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       bigint        NOT NULL COMMENT '用户id',
    `name`          varchar(200)  NOT NULL DEFAULT '' COMMENT '事项名称',
    `mark`          varchar(2000) NOT NULL DEFAULT '' COMMENT '事项备注',
    `tags`          varchar(2000) NOT NULL DEFAULT '' COMMENT '事项标签',
    `activity_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动时间',
    `create_time`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       int           NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户活动记录表';

DROP TABLE IF EXISTS `Zero_UserTransaction`;

CREATE TABLE `Zero_UserTransaction`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `activity_id`      bigint       NOT NULL COMMENT '活动id',
    `user_account_id`  bigint       NOT NULL COMMENT '用户账号id',
    `currency`         int          NOT NULL COMMENT '货币类型',
    `amount`           float        NOT NULL COMMENT '交易金额',
    `transaction_type` int          NOT NULL COMMENT '资金变动类型',
    `name`             varchar(200) NOT NULL DEFAULT '' COMMENT '事项名称',
    `transaction_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易类型时间',
    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          int          NOT NULL DEFAULT '0' COMMENT '是否删除，1表示删除，0表示未删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户交易记录表';

