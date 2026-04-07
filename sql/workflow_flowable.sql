-- ==================== workflow-flowable模块数据库表 ====================
-- 流程分类表
DROP TABLE IF EXISTS wf_process_category;
CREATE TABLE wf_process_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    ancestors VARCHAR(200) COMMENT '祖级列表',
    category_code VARCHAR(50) COMMENT '分类编码',
    sort INT DEFAULT 0 COMMENT '排序',
    icon VARCHAR(200) COMMENT '图标',
    status VARCHAR(10) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB COMMENT='流程分类表';

-- 表单定义表
DROP TABLE IF EXISTS wf_form_definition;
CREATE TABLE wf_form_definition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '表单ID',
    form_name VARCHAR(100) NOT NULL COMMENT '表单名称',
    form_code VARCHAR(50) COMMENT '表单编码',
    form_type VARCHAR(20) DEFAULT 'dynamic' COMMENT '表单类型',
    description VARCHAR(500) COMMENT '表单描述',
    form_config TEXT COMMENT '表单配置JSON',
    status VARCHAR(10) DEFAULT '0' COMMENT '状态',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB COMMENT='表单定义表';

-- 表单字段表
DROP TABLE IF EXISTS wf_form_field;
CREATE TABLE wf_form_field (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字段ID',
    form_id BIGINT NOT NULL COMMENT '表单ID',
    field_name VARCHAR(100) NOT NULL COMMENT '字段名称',
    field_code VARCHAR(50) NOT NULL COMMENT '字段编码',
    field_type VARCHAR(30) NOT NULL COMMENT '字段类型',
    required VARCHAR(10) DEFAULT '0' COMMENT '是否必填',
    placeholder VARCHAR(200) COMMENT '提示信息',
    default_value VARCHAR(500) COMMENT '默认值',
    validate_rule VARCHAR(500) COMMENT '校验规则JSON',
    sort INT DEFAULT 0 COMMENT '排序',
    status VARCHAR(10) DEFAULT '0' COMMENT '状态',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB COMMENT='表单字段表';

-- 流程定义扩展表
DROP TABLE IF EXISTS wf_process_definition;
CREATE TABLE wf_process_definition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_key VARCHAR(100) NOT NULL COMMENT '流程定义KEY',
    process_name VARCHAR(100) NOT NULL COMMENT '流程名称',
    category_id BIGINT COMMENT '流程分类ID',
    form_id BIGINT COMMENT '关联表单ID',
    description VARCHAR(500) COMMENT '流程描述',
    version INT DEFAULT 1 COMMENT '版本号',
    suspension_state VARCHAR(10) DEFAULT 'active' COMMENT '状态',
    deployment_id VARCHAR(64) COMMENT 'Flowable部署ID',
    bpmn_xml TEXT COMMENT 'BPMN XML内容',
    icon VARCHAR(200) COMMENT '流程图标',
    sort INT DEFAULT 0 COMMENT '排序',
    status VARCHAR(10) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB COMMENT='流程定义扩展表';

-- 流程实例扩展表
DROP TABLE IF EXISTS wf_process_instance;
CREATE TABLE wf_process_instance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_definition_id BIGINT COMMENT '流程定义ID',
    process_instance_id VARCHAR(64) NOT NULL COMMENT 'Flowable流程实例ID',
    business_key VARCHAR(100) COMMENT '业务KEY',
    business_table VARCHAR(100) COMMENT '业务表名',
    business_id BIGINT COMMENT '业务数据ID',
    title VARCHAR(200) COMMENT '流程标题',
    initiator_id BIGINT COMMENT '发起人ID',
    initiator_name VARCHAR(50) COMMENT '发起人姓名',
    dept_id BIGINT COMMENT '部门ID',
    form_data TEXT COMMENT '表单数据JSON',
    status VARCHAR(20) DEFAULT 'running' COMMENT '流程状态',
    result VARCHAR(20) COMMENT '审批结果',
    end_time DATETIME COMMENT '结束时间',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB COMMENT='流程实例扩展表';

-- 流程流转记录表
DROP TABLE IF EXISTS wf_flow_record;
CREATE TABLE wf_flow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_instance_id VARCHAR(64) NOT NULL COMMENT 'Flowable流程实例ID',
    task_id VARCHAR(64) COMMENT 'Flowable任务ID',
    task_name VARCHAR(100) COMMENT '任务名称',
    task_key VARCHAR(50) COMMENT '任务节点KEY',
    assignee_id BIGINT COMMENT '审批人ID',
    assignee_name VARCHAR(50) COMMENT '审批人姓名',
    dept_id BIGINT COMMENT '部门ID',
    claim_time DATETIME COMMENT '认领时间',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    approve_type VARCHAR(20) COMMENT '审批类型',
    approve_result VARCHAR(20) COMMENT '审批结果',
    comment VARCHAR(500) COMMENT '审批意见',
    attachment VARCHAR(500) COMMENT '附件',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='流程流转记录表';

-- 待办任务扩展表
DROP TABLE IF EXISTS wf_todo_task;
CREATE TABLE wf_todo_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_instance_id VARCHAR(64) NOT NULL COMMENT 'Flowable流程实例ID',
    task_id VARCHAR(64) NOT NULL COMMENT 'Flowable任务ID',
    task_name VARCHAR(100) COMMENT '任务名称',
    task_key VARCHAR(50) COMMENT '任务节点KEY',
    process_definition_id BIGINT COMMENT '流程定义ID',
    business_key VARCHAR(100) COMMENT '业务KEY',
    title VARCHAR(200) COMMENT '流程标题',
    assignee_id BIGINT COMMENT '审批人ID',
    assignee_name VARCHAR(50) COMMENT '审批人姓名',
    dept_id BIGINT COMMENT '部门ID',
    priority INT DEFAULT 0 COMMENT '优先级',
    due_date DATETIME COMMENT '截止时间',
    status VARCHAR(20) DEFAULT 'pending' COMMENT '任务状态',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='待办任务扩展表';

-- ==================== workflow-oa模块数据库表 ====================
-- 请假申请表（关联流程）
DROP TABLE IF EXISTS oa_leave_apply;
CREATE TABLE oa_leave_apply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '请假ID',
    process_instance_id VARCHAR(64) COMMENT '流程实例ID',
    user_id BIGINT NOT NULL COMMENT '申请人ID',
    user_name VARCHAR(50) COMMENT '申请人姓名',
    dept_id BIGINT COMMENT '部门ID',
    dept_name VARCHAR(100) COMMENT '部门名称',
    leave_type_id BIGINT NOT NULL COMMENT '请假类型ID',
    leave_type_name VARCHAR(50) COMMENT '请假类型名称',
    start_date DATETIME NOT NULL COMMENT '开始时间',
    end_date DATETIME NOT NULL COMMENT '结束时间',
    leave_days DECIMAL(10,2) NOT NULL COMMENT '请假天数',
    reason VARCHAR(500) COMMENT '请假原因',
    status VARCHAR(20) DEFAULT 'draft' COMMENT '状态',
    urgent_level VARCHAR(10) DEFAULT 'normal' COMMENT '紧急程度',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB COMMENT='请假申请表';

-- ==================== 初始化数据 ====================
-- 流程分类初始化
INSERT INTO wf_process_category (id, category_name, parent_id, ancestors, category_code, sort, status) VALUES
(1, '人事审批', 0, '0', 'hr', 1, '0'),
(2, '财务审批', 0, '0', 'finance', 2, '0'),
(3, '行政审批', 0, '0', 'admin', 3, '0');