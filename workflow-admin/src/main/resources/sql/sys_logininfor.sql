-- =============================================
-- 系统访问记录表
-- =============================================
DROP TABLE IF EXISTS sys_logininfor;
CREATE TABLE sys_logininfor (
    info_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    user_name       VARCHAR(50)     DEFAULT ''              COMMENT '用户账号',
    ipaddr          VARCHAR(128)    DEFAULT ''              COMMENT '登录IP地址',
    login_location  VARCHAR(255)    DEFAULT ''              COMMENT '登录地点',
    browser         VARCHAR(50)     DEFAULT ''              COMMENT '浏览器类型',
    os              VARCHAR(50)     DEFAULT ''              COMMENT '操作系统',
    status          CHAR(1)         DEFAULT '0'             COMMENT '登录状态（0成功 1失败）',
    msg             VARCHAR(255)    DEFAULT ''              COMMENT '提示消息',
    login_time      DATETIME        DEFAULT NULL            COMMENT '访问时间',
    PRIMARY KEY (info_id),
    KEY idx_sys_logininfor_s (status),
    KEY idx_sys_logininfor_lt (login_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='系统访问记录表';