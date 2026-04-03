-- =============================================
-- 通知公告表
-- =============================================
DROP TABLE IF EXISTS sys_notice;
CREATE TABLE sys_notice (
    notice_id       BIGINT          NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    notice_title    VARCHAR(50)     NOT NULL                COMMENT '公告标题',
    notice_type     CHAR(1)         NOT NULL                COMMENT '公告类型（1通知 2公告）',
    notice_content  TEXT            DEFAULT NULL            COMMENT '公告内容',
    status          CHAR(1)         DEFAULT '0'             COMMENT '公告状态（0正常 1关闭）',
    create_by       VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL            COMMENT '更新时间',
    remark          VARCHAR(255)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (notice_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='通知公告表';

-- =============================================
-- 初始化通知公告数据
-- =============================================
INSERT INTO sys_notice VALUES(1, '欢迎使用Workflow工作流系统', '2', '欢迎使用Workflow工作流系统，本系统基于Spring Boot + MyBatis-Plus + Flowable开发。', '0', 'admin', NOW(), '', NULL, '管理员');
INSERT INTO sys_notice VALUES(2, '系统上线通知', '1', 'Workflow工作流系统已正式上线，请各位用户注意。', '0', 'admin', NOW(), '', NULL, '管理员');