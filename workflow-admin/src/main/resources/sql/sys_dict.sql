-- =============================================
-- 字典类型表
-- =============================================
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type (
    dict_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    dict_name       VARCHAR(100)    DEFAULT ''              COMMENT '字典名称',
    dict_type       VARCHAR(100)    DEFAULT ''              COMMENT '字典类型',
    status          CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (dict_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='字典类型表';

-- =============================================
-- 字典数据表
-- =============================================
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data (
    dict_code       BIGINT          NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    dict_sort       INT             DEFAULT 0               COMMENT '字典排序',
    dict_label      VARCHAR(100)    DEFAULT ''              COMMENT '字典标签',
    dict_value      VARCHAR(100)    DEFAULT ''              COMMENT '字典键值',
    dict_type       VARCHAR(100)    DEFAULT ''              COMMENT '字典类型',
    css_class       VARCHAR(100)    DEFAULT NULL            COMMENT '样式属性',
    list_class      VARCHAR(100)    DEFAULT NULL            COMMENT '表格回显样式',
    is_default      CHAR(1)         DEFAULT 'N'             COMMENT '是否默认（Y是 N否）',
    status          CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (dict_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='字典数据表';

-- =============================================
-- 初始化字典类型数据
-- =============================================
INSERT INTO sys_dict_type VALUES(1, '用户性别', 'sys_user_sex', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(2, '菜单状态', 'sys_show_hide', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(3, '开关', 'sys_normal_disable', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(4, '任务状态', 'sys_job_status', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(5, '任务分组', 'sys_job_group', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(6, '系统是否', 'sys_yes_no', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(7, '通知类型', 'sys_notice_type', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(8, '通知状态', 'sys_notice_status', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(9, '操作类型', 'sys_oper_type', '0', 'admin', NOW(), '', NULL, NULL);
INSERT INTO sys_dict_type VALUES(10, '系统状态', 'sys_common_status', '0', 'admin', NOW(), '', NULL, NULL);

-- =============================================
-- 初始化字典数据
-- =============================================
INSERT INTO sys_dict_data VALUES(1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', NOW(), '', NULL, '性别男');
INSERT INTO sys_dict_data VALUES(2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', NOW(), '', NULL, '性别女');
INSERT INTO sys_dict_data VALUES(3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', NOW(), '', NULL, '性别未知');
INSERT INTO sys_dict_data VALUES(4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '显示菜单');
INSERT INTO sys_dict_data VALUES(5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '隐藏菜单');
INSERT INTO sys_dict_data VALUES(6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data VALUES(7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '停用状态');
INSERT INTO sys_dict_data VALUES(8, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '系统默认是');
INSERT INTO sys_dict_data VALUES(9, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '系统默认否');
INSERT INTO sys_dict_data VALUES(10, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', NOW(), '', NULL, '通知');
INSERT INTO sys_dict_data VALUES(11, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', NOW(), '', NULL, '公告');
INSERT INTO sys_dict_data VALUES(12, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data VALUES(13, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '关闭状态');
INSERT INTO sys_dict_data VALUES(14, 1, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '其他操作');
INSERT INTO sys_dict_data VALUES(15, 2, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '新增操作');
INSERT INTO sys_dict_data VALUES(16, 3, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '修改操作');
INSERT INTO sys_dict_data VALUES(17, 4, '删除', '3', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '删除操作');
INSERT INTO sys_dict_data VALUES(18, 5, '授权', '4', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '授权操作');
INSERT INTO sys_dict_data VALUES(19, 6, '导出', '5', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '导出操作');
INSERT INTO sys_dict_data VALUES(20, 7, '导入', '6', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '导入操作');
INSERT INTO sys_dict_data VALUES(21, 8, '强退', '7', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '强退操作');
INSERT INTO sys_dict_data VALUES(22, 9, '生成代码', '8', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '生成代码');
INSERT INTO sys_dict_data VALUES(23, 10, '清空', '9', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '清空操作');