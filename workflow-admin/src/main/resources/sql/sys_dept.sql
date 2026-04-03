-- =============================================
-- 部门表
-- =============================================
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
    dept_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    parent_id       BIGINT          DEFAULT 0               COMMENT '父部门ID',
    ancestors       VARCHAR(50)     DEFAULT ''              COMMENT '祖级列表',
    dept_name       VARCHAR(30)     NOT NULL                COMMENT '部门名称',
    order_num       INT             DEFAULT 0               COMMENT '显示顺序',
    leader          VARCHAR(20)     DEFAULT NULL            COMMENT '负责人',
    phone           VARCHAR(11)     DEFAULT NULL            COMMENT '联系电话',
    email           VARCHAR(50)     DEFAULT NULL            COMMENT '邮箱',
    status          CHAR(1)         DEFAULT '0'             COMMENT '部门状态（0正常 1停用）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (dept_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='部门表';

-- =============================================
-- 角色部门关联表（数据权限）
-- =============================================
DROP TABLE IF EXISTS sys_role_dept;
CREATE TABLE sys_role_dept (
    role_id         BIGINT          NOT NULL                COMMENT '角色ID',
    dept_id         BIGINT          NOT NULL                COMMENT '部门ID',
    PRIMARY KEY (role_id, dept_id)
) ENGINE=InnoDB COMMENT='角色和部门关联表';

-- =============================================
-- 初始化部门数据
-- =============================================
INSERT INTO sys_dept VALUES(100, 0, '0', '总公司', 0, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(101, 100, '0,100', '深圳分公司', 1, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(102, 100, '0,100', '长沙分公司', 2, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(103, 101, '0,100,101', '研发部门', 1, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(104, 101, '0,100,101', '市场部门', 2, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(105, 101, '0,100,101', '测试部门', 3, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(106, 101, '0,100,101', '财务部门', 4, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(107, 101, '0,100,101', '运维部门', 5, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(108, 102, '0,100,102', '市场部门', 1, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_dept VALUES(109, 102, '0,100,102', '财务部门', 2, NULL, NULL, NULL, '0', 'admin', NOW(), '', NULL, 0);