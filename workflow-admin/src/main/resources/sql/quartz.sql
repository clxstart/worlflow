-- =============================================
-- 定时任务调度表
-- =============================================
DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job (
    job_id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    job_name            VARCHAR(64)     NOT NULL                COMMENT '任务名称',
    job_group           VARCHAR(64)     NOT NULL                COMMENT '任务组名',
    invoke_target       VARCHAR(500)    NOT NULL                COMMENT '调用目标字符串',
    cron_expression     VARCHAR(255)    NOT NULL                COMMENT 'cron执行表达式',
    misfire_policy      VARCHAR(20)     DEFAULT '3'             COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    concurrent          CHAR(1)         DEFAULT '1'             COMMENT '是否并发执行（0允许 1禁止）',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1暂停）',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark              VARCHAR(500)    DEFAULT ''              COMMENT '备注信息',
    PRIMARY KEY (job_id, job_name, job_group)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='定时任务调度表';

-- =============================================
-- 定时任务调度日志表
-- =============================================
DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log (
    job_log_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
    job_name            VARCHAR(64)     NOT NULL                COMMENT '任务名称',
    job_group           VARCHAR(64)     NOT NULL                COMMENT '任务组名',
    invoke_target       VARCHAR(500)    NOT NULL                COMMENT '调用目标字符串',
    job_message         VARCHAR(500)    DEFAULT NULL            COMMENT '日志信息',
    status              CHAR(1)         DEFAULT '0'             COMMENT '执行状态（0正常 1失败）',
    exception_info      VARCHAR(2000)   DEFAULT ''              COMMENT '异常信息',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (job_log_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='定时任务调度日志表';

-- =============================================
-- 初始化定时任务数据
-- =============================================
INSERT INTO sys_job VALUES(1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_job VALUES(2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_job VALUES(3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', NOW(), '', NULL, '');