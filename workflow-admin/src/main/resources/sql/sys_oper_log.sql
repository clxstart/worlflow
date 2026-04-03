-- =============================================
-- 操作日志记录表
-- =============================================
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log (
    oper_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    title           VARCHAR(50)     DEFAULT ''              COMMENT '模块标题',
    business_type   INT             DEFAULT 0               COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    method          VARCHAR(200)    DEFAULT ''              COMMENT '方法名称',
    request_method  VARCHAR(10)     DEFAULT ''              COMMENT '请求方式',
    operator_type   INT             DEFAULT 0               COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    oper_name       VARCHAR(50)     DEFAULT ''              COMMENT '操作人员',
    dept_name       VARCHAR(50)     DEFAULT ''              COMMENT '部门名称',
    oper_url        VARCHAR(255)    DEFAULT ''              COMMENT '请求URL',
    oper_ip         VARCHAR(128)    DEFAULT ''              COMMENT '主机地址',
    oper_location   VARCHAR(255)    DEFAULT ''              COMMENT '操作地点',
    oper_param      TEXT            DEFAULT NULL            COMMENT '请求参数',
    json_result     TEXT            DEFAULT NULL            COMMENT '返回参数',
    status          INT             DEFAULT 0               COMMENT '操作状态（0正常 1异常）',
    error_msg       TEXT            DEFAULT NULL            COMMENT '错误消息',
    oper_time       DATETIME        DEFAULT NULL            COMMENT '操作时间',
    cost_time       BIGINT          DEFAULT 0               COMMENT '消耗时间',
    PRIMARY KEY (oper_id),
    KEY idx_sys_oper_log_bt (business_type),
    KEY idx_sys_oper_log_s (status),
    KEY idx_sys_oper_log_ot (oper_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='操作日志记录表';