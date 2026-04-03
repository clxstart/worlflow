-- =============================================
-- 菜单权限表
-- =============================================
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
    menu_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    menu_name       VARCHAR(50)     NOT NULL                COMMENT '菜单名称',
    parent_id       BIGINT          DEFAULT 0               COMMENT '父菜单ID',
    order_num       INT             DEFAULT 0               COMMENT '显示顺序',
    path            VARCHAR(200)    DEFAULT ''              COMMENT '路由地址',
    component       VARCHAR(255)    DEFAULT NULL            COMMENT '组件路径',
    query           VARCHAR(255)    DEFAULT NULL            COMMENT '路由参数',
    is_frame        INT             DEFAULT 1               COMMENT '是否外链（0否 1是）',
    is_cache        INT             DEFAULT 0               COMMENT '是否缓存（0否 1是）',
    menu_type       CHAR(1)         DEFAULT ''              COMMENT '菜单类型（M目录 C菜单 F按钮）',
    visible         CHAR(1)         DEFAULT '0'             COMMENT '显示状态（0显示 1隐藏）',
    status          CHAR(1)         DEFAULT '0'             COMMENT '菜单状态（0正常 1停用）',
    perms           VARCHAR(100)    DEFAULT NULL            COMMENT '权限标识',
    icon            VARCHAR(100)    DEFAULT '#'             COMMENT '菜单图标',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT ''              COMMENT '备注',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (menu_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='菜单权限表';

-- =============================================
-- 角色菜单关联表
-- =============================================
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
    role_id         BIGINT          NOT NULL                COMMENT '角色ID',
    menu_id         BIGINT          NOT NULL                COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB COMMENT='角色和菜单关联表';

-- =============================================
-- 初始化菜单数据
-- =============================================
INSERT INTO sys_menu VALUES(1, '系统管理', 0, 1, 'system', NULL, NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', NOW(), '', NULL, '系统管理目录', 0);
INSERT INTO sys_menu VALUES(2, '用户管理', 1, 1, 'user', 'system/user/index', NULL, 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', NOW(), '', NULL, '用户管理菜单', 0);
INSERT INTO sys_menu VALUES(3, '角色管理', 1, 2, 'role', 'system/role/index', NULL, 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', NOW(), '', NULL, '角色管理菜单', 0);
INSERT INTO sys_menu VALUES(4, '菜单管理', 1, 3, 'menu', 'system/menu/index', NULL, 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', NOW(), '', NULL, '菜单管理菜单', 0);
INSERT INTO sys_menu VALUES(5, '部门管理', 1, 4, 'dept', 'system/dept/index', NULL, 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', NOW(), '', NULL, '部门管理菜单', 0);

-- 初始化角色菜单关联数据（超级管理员角色拥有所有菜单权限）
INSERT INTO sys_role_menu VALUES(1, 1);
INSERT INTO sys_role_menu VALUES(1, 2);
INSERT INTO sys_role_menu VALUES(1, 3);
INSERT INTO sys_role_menu VALUES(1, 4);
INSERT INTO sys_role_menu VALUES(1, 5);