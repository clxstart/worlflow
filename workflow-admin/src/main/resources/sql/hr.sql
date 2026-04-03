-- =============================================
-- 人事管理模块数据库表
-- =============================================

-- =============================================
-- 考勤记录表
-- =============================================
DROP TABLE IF EXISTS hr_attendance;
CREATE TABLE hr_attendance (
    attendance_id       BIGINT          NOT NULL AUTO_INCREMENT COMMENT '考勤ID',
    user_id             BIGINT          NOT NULL                COMMENT '用户ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '用户姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '部门ID',
    attendance_date     DATE            NOT NULL                COMMENT '考勤日期',
    clock_in_time       TIME            DEFAULT NULL            COMMENT '上班打卡时间',
    clock_out_time      TIME            DEFAULT NULL            COMMENT '下班打卡时间',
    work_hours          DECIMAL(4,1)    DEFAULT 0.0             COMMENT '工作时长（小时）',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1迟到 2早退 3缺勤 4请假）',
    late_minutes        INT             DEFAULT 0               COMMENT '迟到分钟数',
    early_minutes       INT             DEFAULT 0               COMMENT '早退分钟数',
    overtime_hours      DECIMAL(4,1)    DEFAULT 0.0             COMMENT '加班时长（小时）',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (attendance_id),
    UNIQUE KEY uk_user_date (user_id, attendance_date),
    KEY idx_attendance_date (attendance_date)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='考勤记录表';

-- =============================================
-- 考勤排班表
-- =============================================
DROP TABLE IF EXISTS hr_attendance_shift;
CREATE TABLE hr_attendance_shift (
    shift_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '班次ID',
    shift_name          VARCHAR(50)     NOT NULL                COMMENT '班次名称',
    work_start_time     TIME            NOT NULL                COMMENT '上班时间',
    work_end_time       TIME            NOT NULL                COMMENT '下班时间',
    late_threshold      INT             DEFAULT 0               COMMENT '迟到阈值（分钟）',
    early_threshold     INT             DEFAULT 0               COMMENT '早退阈值（分钟）',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (shift_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='考勤排班表';

-- =============================================
-- 请假类型表
-- =============================================
DROP TABLE IF EXISTS hr_leave_type;
CREATE TABLE hr_leave_type (
    type_id             BIGINT          NOT NULL AUTO_INCREMENT COMMENT '类型ID',
    type_name           VARCHAR(50)     NOT NULL                COMMENT '类型名称',
    type_code           VARCHAR(50)     NOT NULL                COMMENT '类型编码',
    annual_days         INT             DEFAULT 0               COMMENT '年假天数',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (type_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='请假类型表';

-- =============================================
-- 请假申请表
-- =============================================
DROP TABLE IF EXISTS hr_leave;
CREATE TABLE hr_leave (
    leave_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '请假ID',
    user_id             BIGINT          NOT NULL                COMMENT '申请人ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '申请人姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '部门ID',
    type_id             BIGINT          NOT NULL                COMMENT '请假类型ID',
    leave_type          VARCHAR(50)     DEFAULT ''              COMMENT '请假类型名称',
    start_date          DATETIME        NOT NULL                COMMENT '开始时间',
    end_date            DATETIME        NOT NULL                COMMENT '结束时间',
    leave_days          DECIMAL(4,1)    NOT NULL                COMMENT '请假天数',
    reason              VARCHAR(500)    DEFAULT NULL            COMMENT '请假原因',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待审批 1已通过 2已拒绝 3已撤销）',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_user_name   VARCHAR(50)     DEFAULT ''              COMMENT '审批人姓名',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    approve_remark      VARCHAR(500)    DEFAULT NULL            COMMENT '审批意见',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (leave_id),
    KEY idx_user_id (user_id),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='请假申请表';

-- =============================================
-- 假期余额表
-- =============================================
DROP TABLE IF EXISTS hr_leave_balance;
CREATE TABLE hr_leave_balance (
    balance_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '余额ID',
    user_id             BIGINT          NOT NULL                COMMENT '用户ID',
    type_id             BIGINT          NOT NULL                COMMENT '请假类型ID',
    year                INT             NOT NULL                COMMENT '年份',
    total_days          DECIMAL(4,1)    DEFAULT 0.0             COMMENT '总额度（天）',
    used_days           DECIMAL(4,1)    DEFAULT 0.0             COMMENT '已使用（天）',
    remaining_days      DECIMAL(4,1)    DEFAULT 0.0             COMMENT '剩余（天）',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    PRIMARY KEY (balance_id),
    UNIQUE KEY uk_user_type_year (user_id, type_id, year)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='假期余额表';

-- =============================================
-- 薪资表
-- =============================================
DROP TABLE IF EXISTS hr_salary;
CREATE TABLE hr_salary (
    salary_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '薪资ID',
    user_id             BIGINT          NOT NULL                COMMENT '用户ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '用户姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '部门ID',
    salary_month        VARCHAR(7)      NOT NULL                COMMENT '薪资月份（格式：2024-01）',
    base_salary         DECIMAL(10,2)   DEFAULT 0.00            COMMENT '基本工资',
    post_salary         DECIMAL(10,2)   DEFAULT 0.00            COMMENT '岗位工资',
    performance_salary  DECIMAL(10,2)   DEFAULT 0.00            COMMENT '绩效工资',
    overtime_salary     DECIMAL(10,2)   DEFAULT 0.00            COMMENT '加班工资',
    bonus               DECIMAL(10,2)   DEFAULT 0.00            COMMENT '奖金',
    subsidy             DECIMAL(10,2)   DEFAULT 0.00            COMMENT '补贴',
    deduction           DECIMAL(10,2)   DEFAULT 0.00            COMMENT '扣款',
    tax                 DECIMAL(10,2)   DEFAULT 0.00            COMMENT '个税',
    social_insurance    DECIMAL(10,2)   DEFAULT 0.00            COMMENT '社保',
    housing_fund        DECIMAL(10,2)   DEFAULT 0.00            COMMENT '公积金',
    actual_salary       DECIMAL(10,2)   DEFAULT 0.00            COMMENT '实发工资',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待发放 1已发放 2已撤销）',
    pay_time            DATETIME        DEFAULT NULL            COMMENT '发放时间',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (salary_id),
    UNIQUE KEY uk_user_month (user_id, salary_month),
    KEY idx_salary_month (salary_month)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='薪资表';

-- =============================================
-- 薪资项表
-- =============================================
DROP TABLE IF EXISTS hr_salary_item;
CREATE TABLE hr_salary_item (
    item_id             BIGINT          NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    salary_id           BIGINT          NOT NULL                COMMENT '薪资ID',
    item_name           VARCHAR(50)     NOT NULL                COMMENT '项目名称',
    item_type           CHAR(1)         NOT NULL                COMMENT '项目类型（1增加 2扣减）',
    amount              DECIMAL(10,2)   DEFAULT 0.00            COMMENT '金额',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (item_id),
    KEY idx_salary_id (salary_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='薪资项表';

-- =============================================
-- 初始化请假类型数据
-- =============================================
INSERT INTO hr_leave_type VALUES(1, '年假', 'annual', 15, '0', 'admin', NOW(), '', NULL, '带薪年假');
INSERT INTO hr_leave_type VALUES(2, '事假', 'personal', 0, '0', 'admin', NOW(), '', NULL, '无薪事假');
INSERT INTO hr_leave_type VALUES(3, '病假', 'sick', 0, '0', 'admin', NOW(), '', NULL, '带薪病假');
INSERT INTO hr_leave_type VALUES(4, '婚假', 'marriage', 3, '0', 'admin', NOW(), '', NULL, '带薪婚假');
INSERT INTO hr_leave_type VALUES(5, '产假', 'maternity', 98, '0', 'admin', NOW(), '', NULL, '带薪产假');
INSERT INTO hr_leave_type VALUES(6, '陪产假', 'paternity', 15, '0', 'admin', NOW(), '', NULL, '带薪陪产假');
INSERT INTO hr_leave_type VALUES(7, '丧假', 'bereavement', 3, '0', 'admin', NOW(), '', NULL, '带薪丧假');
INSERT INTO hr_leave_type VALUES(8, '调休', 'compensatory', 0, '0', 'admin', NOW(), '', NULL, '加班调休');