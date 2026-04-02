-- 角色信息表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `role_id`       BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`     VARCHAR(30) NOT NULL COMMENT '角色名称',
    `role_key`      VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort`     INT NOT NULL COMMENT '显示顺序',
    `status`        CHAR(1) NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `deleted`       INT DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `create_time`   DATETIME COMMENT '创建时间',
    `update_time`   DATETIME COMMENT '更新时间',
    `remark`        VARCHAR(500) COMMENT '备注',
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB COMMENT='角色信息表';

-- 初始化角色数据
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '0', 0, NOW(), NOW(), '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通用户', 'common', 2, '0', 0, NOW(), NOW(), '普通用户');

-- 用户和角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `user_id`   BIGINT NOT NULL COMMENT '用户ID',
    `role_id`   BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB COMMENT='用户和角色关联表';

-- 初始化用户角色关联数据（admin用户关联超级管理员角色）
INSERT INTO `sys_user_role` VALUES (1, 1);