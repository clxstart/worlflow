-- =============================================
-- 岗位信息表
-- =============================================
DROP TABLE IF EXISTS sys_post;
CREATE TABLE sys_post (
    post_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    post_code       VARCHAR(64)     NOT NULL                COMMENT '岗位编码',
    post_name       VARCHAR(50)     NOT NULL                COMMENT '岗位名称',
    post_sort       INT             NOT NULL                COMMENT '显示顺序',
    status          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '状态（0正常 1停用）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (post_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='岗位信息表';

-- =============================================
-- 用户岗位关联表
-- =============================================
DROP TABLE IF EXISTS sys_user_post;
CREATE TABLE sys_user_post (
    user_id         BIGINT          NOT NULL                COMMENT '用户ID',
    post_id         BIGINT          NOT NULL                COMMENT '岗位ID',
    PRIMARY KEY (user_id, post_id)
) ENGINE=InnoDB COMMENT='用户和岗位关联表';

-- =============================================
-- 初始化岗位数据
-- =============================================
INSERT INTO sys_post VALUES(1, 'ceo', '董事长', 1, '0', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_post VALUES(2, 'se', '项目经理', 2, '0', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_post VALUES(3, 'hr', '人力资源', 3, '0', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_post VALUES(4, 'user', '普通员工', 4, '0', 'admin', NOW(), '', NULL, '');

-- 初始化用户岗位关联数据
INSERT INTO sys_user_post VALUES(1, 1);
INSERT INTO sys_user_post VALUES(2, 4);