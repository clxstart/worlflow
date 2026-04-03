-- =============================================
-- 项目表
-- =============================================
DROP TABLE IF EXISTS project;
CREATE TABLE project (
    project_id      BIGINT          NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    project_name    VARCHAR(100)    NOT NULL                COMMENT '项目名称',
    project_code    VARCHAR(50)     DEFAULT ''              COMMENT '项目编码',
    description     TEXT            DEFAULT NULL            COMMENT '项目描述',
    owner_id        BIGINT          NOT NULL                COMMENT '项目负责人ID',
    dept_id         BIGINT          DEFAULT NULL            COMMENT '所属部门ID',
    status          CHAR(1)         DEFAULT '0'             COMMENT '项目状态（0规划 1进行中 2已完成 3已取消）',
    start_date      DATE            DEFAULT NULL            COMMENT '开始日期',
    end_date        DATE            DEFAULT NULL            COMMENT '结束日期',
    priority        INT             DEFAULT 0               COMMENT '优先级',
    progress        INT             DEFAULT 0               COMMENT '进度百分比',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (project_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目表';

-- =============================================
-- 项目阶段表
-- =============================================
DROP TABLE IF EXISTS project_stage;
CREATE TABLE project_stage (
    stage_id        BIGINT          NOT NULL AUTO_INCREMENT COMMENT '阶段ID',
    project_id      BIGINT          NOT NULL                COMMENT '项目ID',
    stage_name      VARCHAR(50)     NOT NULL                COMMENT '阶段名称',
    stage_sort      INT             DEFAULT 0               COMMENT '阶段顺序',
    status          CHAR(1)         DEFAULT '0'             COMMENT '状态',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (stage_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目阶段表';

-- =============================================
-- 项目任务表
-- =============================================
DROP TABLE IF EXISTS project_task;
CREATE TABLE project_task (
    task_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    project_id      BIGINT          NOT NULL                COMMENT '项目ID',
    stage_id        BIGINT          DEFAULT NULL            COMMENT '阶段ID',
    parent_id       BIGINT          DEFAULT 0               COMMENT '父任务ID',
    task_name       VARCHAR(100)    NOT NULL                COMMENT '任务名称',
    description     TEXT            DEFAULT NULL            COMMENT '任务描述',
    assignee_id     BIGINT          DEFAULT NULL            COMMENT '负责人ID',
    status          CHAR(1)         DEFAULT '0'             COMMENT '任务状态（0待办 1进行中 2已完成 3已取消）',
    priority        INT             DEFAULT 0               COMMENT '优先级',
    start_date      DATE            DEFAULT NULL            COMMENT '开始日期',
    due_date        DATE            DEFAULT NULL            COMMENT '截止日期',
    progress        INT             DEFAULT 0               COMMENT '进度百分比',
    work_hours      INT             DEFAULT 0               COMMENT '工时（小时）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (task_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目任务表';

-- =============================================
-- 项目成员表
-- =============================================
DROP TABLE IF EXISTS project_member;
CREATE TABLE project_member (
    member_id       BIGINT          NOT NULL AUTO_INCREMENT COMMENT '成员ID',
    project_id      BIGINT          NOT NULL                COMMENT '项目ID',
    task_id         BIGINT          DEFAULT NULL            COMMENT '任务ID',
    user_id         BIGINT          NOT NULL                COMMENT '用户ID',
    role_type       INT             DEFAULT 0               COMMENT '成员角色类型（0普通成员 1项目管理员 2任务负责人）',
    join_time       DATETIME        DEFAULT NULL            COMMENT '加入时间',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (member_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目成员表';

-- =============================================
-- 项目文件表
-- =============================================
DROP TABLE IF EXISTS project_file;
CREATE TABLE project_file (
    file_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '文件ID',
    project_id      BIGINT          DEFAULT NULL            COMMENT '项目ID',
    task_id         BIGINT          DEFAULT NULL            COMMENT '任务ID',
    file_name       VARCHAR(100)    NOT NULL                COMMENT '文件名称',
    file_path       VARCHAR(255)    NOT NULL                COMMENT '文件路径',
    file_size       BIGINT          DEFAULT 0               COMMENT '文件大小（字节）',
    file_type       VARCHAR(50)     DEFAULT ''              COMMENT '文件类型',
    upload_by       VARCHAR(64)     DEFAULT ''              COMMENT '上传者',
    upload_time     DATETIME        DEFAULT NULL            COMMENT '上传时间',
    deleted         INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (file_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目文件表';

-- =============================================
-- 项目日志表
-- =============================================
DROP TABLE IF EXISTS project_log;
CREATE TABLE project_log (
    log_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    project_id      BIGINT          DEFAULT NULL            COMMENT '项目ID',
    task_id         BIGINT          DEFAULT NULL            COMMENT '任务ID',
    log_type        INT             DEFAULT 0               COMMENT '日志类型',
    log_content     VARCHAR(500)    DEFAULT ''              COMMENT '日志内容',
    operator_id     BIGINT          DEFAULT NULL            COMMENT '操作人ID',
    operator_name   VARCHAR(50)     DEFAULT ''              COMMENT '操作人名称',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (log_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目日志表';

-- =============================================
-- 项目收藏表
-- =============================================
DROP TABLE IF EXISTS project_favorite;
CREATE TABLE project_favorite (
    favorite_id     BIGINT          NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    project_id      BIGINT          NOT NULL                COMMENT '项目ID',
    user_id         BIGINT          NOT NULL                COMMENT '用户ID',
    create_time     DATETIME        DEFAULT NULL            COMMENT '收藏时间',
    PRIMARY KEY (favorite_id),
    UNIQUE KEY uk_project_user (project_id, user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='项目收藏表';