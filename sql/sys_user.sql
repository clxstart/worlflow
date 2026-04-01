-- 创建数据库
CREATE DATABASE IF NOT EXISTS workflow DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE workflow;

-- 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    user_id         BIGINT          NOT NULL AUTO_INCREMENT    COMMENT '用户ID',
    dept_id         BIGINT          DEFAULT NULL               COMMENT '部门ID',
    user_name       VARCHAR(30)     NOT NULL                   COMMENT '用户账号',
    nick_name       VARCHAR(30)     DEFAULT NULL               COMMENT '用户昵称',
    email           VARCHAR(50)     DEFAULT NULL               COMMENT '用户邮箱',
    phonenumber     VARCHAR(11)     DEFAULT NULL               COMMENT '手机号码',
    sex             CHAR(1)         DEFAULT '0'                COMMENT '用户性别（0男 1女 2未知）',
    avatar          VARCHAR(100)    DEFAULT NULL               COMMENT '用户头像',
    password        VARCHAR(100)    DEFAULT NULL               COMMENT '密码',
    status          CHAR(1)         DEFAULT '0'                COMMENT '帐号状态（0正常 1停用）',
    login_ip        VARCHAR(128)    DEFAULT NULL               COMMENT '最后登录IP',
    deleted         INT             DEFAULT 0                  COMMENT '删除标志（0未删除 1已删除）',
    create_time     DATETIME        DEFAULT NULL               COMMENT '创建时间',
    update_time     DATETIME        DEFAULT NULL               COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT = '用户信息表';

-- 初始化-用户表数据
INSERT INTO sys_user VALUES(1, 103, 'admin', '管理员', 'admin@clx.com', '15888888888', '0', NULL, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE/TU.QQ03.X00', '0', '127.0.0.1', 0, NOW(), NOW(), '管理员');
INSERT INTO sys_user VALUES(2, 105, 'test', '测试用户', 'test@clx.com', '15666666666', '1', NULL, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE/TU.QQ03.X00', '0', '127.0.0.1', 0, NOW(), NOW(), '测试用户');