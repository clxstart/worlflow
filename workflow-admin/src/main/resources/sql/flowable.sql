-- =============================================
-- 流程分类表
-- =============================================
DROP TABLE IF EXISTS flow_category;
CREATE TABLE flow_category (
    category_id     BIGINT          NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    category_name   VARCHAR(50)     NOT NULL                COMMENT '分类名称',
    category_code   VARCHAR(50)     NOT NULL                COMMENT '分类编码',
    parent_id       BIGINT          DEFAULT 0               COMMENT '父分类ID',
    order_num       INT             DEFAULT 0               COMMENT '排序',
    status          CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='流程分类表';

-- =============================================
-- 表单管理表
-- =============================================
DROP TABLE IF EXISTS flow_form;
CREATE TABLE flow_form (
    form_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '表单ID',
    form_name       VARCHAR(100)    NOT NULL                COMMENT '表单名称',
    form_code       VARCHAR(50)     DEFAULT ''              COMMENT '表单编码',
    form_content    TEXT            DEFAULT NULL            COMMENT '表单内容（JSON）',
    status          CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (form_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='表单管理表';

-- =============================================
-- 部署表单关联表
-- =============================================
DROP TABLE IF EXISTS flow_deploy_form;
CREATE TABLE flow_deploy_form (
    deploy_id       BIGINT          NOT NULL                COMMENT '部署ID',
    form_id         BIGINT          NOT NULL                COMMENT '表单ID',
    node_key        VARCHAR(50)     DEFAULT ''              COMMENT '节点Key',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (deploy_id, form_id)
) ENGINE=InnoDB COMMENT='部署表单关联表';

-- =============================================
-- 流程抄送表
-- =============================================
DROP TABLE IF EXISTS flow_copy;
CREATE TABLE flow_copy (
    copy_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '抄送ID',
    title           VARCHAR(255)    DEFAULT ''              COMMENT '流程标题',
    process_id      BIGINT          DEFAULT NULL            COMMENT '流程ID',
    process_name    VARCHAR(100)    DEFAULT ''              COMMENT '流程名称',
    category_id     BIGINT          DEFAULT NULL            COMMENT '分类ID',
    originator_id   BIGINT          DEFAULT NULL            COMMENT '发起人ID',
    originator_name VARCHAR(50)     DEFAULT ''              COMMENT '发起人名称',
    assignee_id     BIGINT          DEFAULT NULL            COMMENT '抄送人ID',
    assignee_name   VARCHAR(50)     DEFAULT ''              COMMENT '抄送人名称',
    status          CHAR(1)         DEFAULT '0'             COMMENT '状态',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (copy_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='流程抄送表';

-- =============================================
-- 初始化流程分类数据
-- =============================================
INSERT INTO flow_category VALUES(1, '审批流程', 'approval', 0, 1, '0', 'admin', NOW(), '', NULL, '', 0);
INSERT INTO flow_category VALUES(2, '人事流程', 'hr', 0, 2, '0', 'admin', NOW(), '', NULL, '', 0);
INSERT INTO flow_category VALUES(3, '财务流程', 'finance', 0, 3, '0', 'admin', NOW(), '', NULL, '', 0);
INSERT INTO flow_category VALUES(4, '项目流程', 'project', 0, 4, '0', 'admin', NOW(), '', NULL, '', 0);