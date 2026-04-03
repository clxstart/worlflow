-- =============================================
-- 系统运维扩展模块数据库表
-- =============================================

-- =============================================
-- 服务器监控记录表
-- =============================================
DROP TABLE IF EXISTS sys_server_monitor;
CREATE TABLE sys_server_monitor (
    monitor_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '监控ID',
    server_name         VARCHAR(100)    NOT NULL                COMMENT '服务器名称',
    server_ip           VARCHAR(50)     NOT NULL                COMMENT '服务器IP',
    server_type         VARCHAR(50)     DEFAULT 'application'   COMMENT '服务器类型（application应用服务器 database数据库服务器 cache缓存服务器）',
    cpu_usage           DECIMAL(5,2)    DEFAULT 0.00            COMMENT 'CPU使用率（%）',
    memory_usage        DECIMAL(5,2)    DEFAULT 0.00            COMMENT '内存使用率（%）',
    memory_total        BIGINT          DEFAULT 0               COMMENT '内存总量（MB）',
    memory_used         BIGINT          DEFAULT 0               COMMENT '已用内存（MB）',
    disk_usage          DECIMAL(5,2)    DEFAULT 0.00            COMMENT '磁盘使用率（%）',
    disk_total          BIGINT          DEFAULT 0               COMMENT '磁盘总量（GB）',
    disk_used           BIGINT          DEFAULT 0               COMMENT '已用磁盘（GB）',
    network_in          BIGINT          DEFAULT 0               COMMENT '网络入流量（KB/s）',
    network_out         BIGINT          DEFAULT 0               COMMENT '网络出流量（KB/s）',
    process_count       INT             DEFAULT 0               COMMENT '进程数',
    thread_count        INT             DEFAULT 0               COMMENT '线程数',
    load_average        DECIMAL(5,2)    DEFAULT 0.00            COMMENT '系统负载',
    uptime              BIGINT          DEFAULT 0               COMMENT '运行时长（秒）',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1异常 2离线）',
    monitor_time        DATETIME        NOT NULL                COMMENT '监控时间',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (monitor_id),
    KEY idx_server_ip (server_ip),
    KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='服务器监控记录表';

-- =============================================
-- 服务状态表
-- =============================================
DROP TABLE IF EXISTS sys_service_status;
CREATE TABLE sys_service_status (
    status_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '状态ID',
    service_name        VARCHAR(100)    NOT NULL                COMMENT '服务名称',
    service_code        VARCHAR(50)     NOT NULL                COMMENT '服务编码',
    service_type        VARCHAR(50)     DEFAULT ''              COMMENT '服务类型',
    service_url         VARCHAR(255)    DEFAULT ''              COMMENT '服务地址',
    service_port        INT             DEFAULT 0               COMMENT '服务端口',
    health_check_url    VARCHAR(255)    DEFAULT ''              COMMENT '健康检查地址',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1异常 2离线）',
    response_time       INT             DEFAULT 0               COMMENT '响应时间（ms）',
    last_check_time     DATETIME        DEFAULT NULL            COMMENT '最后检查时间',
    last_success_time   DATETIME        DEFAULT NULL            COMMENT '最后成功时间',
    last_fail_time      DATETIME        DEFAULT NULL            COMMENT '最后失败时间',
    fail_count          INT             DEFAULT 0               COMMENT '连续失败次数',
    total_request       BIGINT          DEFAULT 0               COMMENT '总请求数',
    success_request     BIGINT          DEFAULT 0               COMMENT '成功请求数',
    fail_request        BIGINT          DEFAULT 0               COMMENT '失败请求数',
    avg_response_time   INT             DEFAULT 0               COMMENT '平均响应时间（ms）',
    description         VARCHAR(500)    DEFAULT NULL            COMMENT '服务描述',
    alert_threshold     INT             DEFAULT 3               COMMENT '告警阈值（连续失败次数）',
    is_alert            CHAR(1)         DEFAULT '0'             COMMENT '是否告警（0否 1是）',
    alert_time          DATETIME        DEFAULT NULL            COMMENT '告警时间',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (status_id),
    UNIQUE KEY uk_service_code (service_code),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='服务状态表';

-- =============================================
-- 缓存监控表
-- =============================================
DROP TABLE IF EXISTS sys_cache_monitor;
CREATE TABLE sys_cache_monitor (
    cache_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '缓存ID',
    cache_name          VARCHAR(100)    NOT NULL                COMMENT '缓存名称',
    cache_type          VARCHAR(50)     DEFAULT 'redis'         COMMENT '缓存类型（redis、ehcache等）',
    cache_host          VARCHAR(100)    DEFAULT ''              COMMENT '缓存主机',
    cache_port          INT             DEFAULT 6379            COMMENT '缓存端口',
    used_memory         BIGINT          DEFAULT 0               COMMENT '已用内存（字节）',
    max_memory          BIGINT          DEFAULT 0               COMMENT '最大内存（字节）',
    memory_usage        DECIMAL(5,2)    DEFAULT 0.00            COMMENT '内存使用率（%）',
    connected_clients   INT             DEFAULT 0               COMMENT '连接客户端数',
    total_connections   BIGINT          DEFAULT 0               COMMENT '总连接数',
    total_commands      BIGINT          DEFAULT 0               COMMENT '总命令数',
    key_count           BIGINT          DEFAULT 0               COMMENT 'Key数量',
    expired_keys        BIGINT          DEFAULT 0               COMMENT '过期Key数量',
    evicted_keys        BIGINT          DEFAULT 0               COMMENT '驱逐Key数量',
    keyspace_hits       BIGINT          DEFAULT 0               COMMENT '命中次数',
    keyspace_misses     BIGINT          DEFAULT 0               COMMENT '未命中次数',
    hit_rate            DECIMAL(5,2)    DEFAULT 0.00            COMMENT '命中率（%）',
    ops_per_sec         BIGINT          DEFAULT 0               COMMENT '每秒操作数',
    uptime              BIGINT          DEFAULT 0               COMMENT '运行时长（秒）',
    version             VARCHAR(50)     DEFAULT ''              COMMENT '版本',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1异常 2离线）',
    monitor_time        DATETIME        NOT NULL                COMMENT '监控时间',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (cache_id),
    KEY idx_cache_name (cache_name),
    KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='缓存监控表';

-- =============================================
-- 缓存Key管理表
-- =============================================
DROP TABLE IF EXISTS sys_cache_key;
CREATE TABLE sys_cache_key (
    key_id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT 'Key ID',
    cache_name          VARCHAR(100)    NOT NULL                COMMENT '缓存名称',
    key_name            VARCHAR(255)    NOT NULL                COMMENT 'Key名称',
    key_type            VARCHAR(50)     DEFAULT 'string'        COMMENT 'Key类型（string、hash、list、set、zset）',
    key_value           TEXT            DEFAULT NULL            COMMENT 'Key值',
    ttl                 BIGINT          DEFAULT -1              COMMENT '过期时间（秒，-1永不过期）',
    size                BIGINT          DEFAULT 0               COMMENT '大小（字节）',
    hit_count           BIGINT          DEFAULT 0               COMMENT '命中次数',
    last_access_time    DATETIME        DEFAULT NULL            COMMENT '最后访问时间',
    expire_time         DATETIME        DEFAULT NULL            COMMENT '过期时间',
    description         VARCHAR(500)    DEFAULT NULL            COMMENT '描述',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1已过期）',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (key_id),
    KEY idx_cache_name (cache_name),
    KEY idx_key_name (key_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='缓存Key管理表';

-- =============================================
-- 数据库监控表
-- =============================================
DROP TABLE IF EXISTS sys_database_monitor;
CREATE TABLE sys_database_monitor (
    db_id               BIGINT          NOT NULL AUTO_INCREMENT COMMENT '数据库ID',
    db_name             VARCHAR(100)    NOT NULL                COMMENT '数据库名称',
    db_type             VARCHAR(50)     DEFAULT 'mysql'         COMMENT '数据库类型',
    db_host             VARCHAR(100)    NOT NULL                COMMENT '数据库主机',
    db_port             INT             DEFAULT 3306            COMMENT '数据库端口',
    db_schema           VARCHAR(100)    DEFAULT ''              COMMENT '数据库名',
    connection_count    INT             DEFAULT 0               COMMENT '当前连接数',
    max_connections     INT             DEFAULT 0               COMMENT '最大连接数',
    active_connections  INT             DEFAULT 0               COMMENT '活跃连接数',
    idle_connections    INT             DEFAULT 0               COMMENT '空闲连接数',
    queries_per_sec     INT             DEFAULT 0               COMMENT '每秒查询数',
    slow_queries        BIGINT          DEFAULT 0               COMMENT '慢查询数',
    table_count         INT             DEFAULT 0               COMMENT '表数量',
    total_size          BIGINT          DEFAULT 0               COMMENT '总大小（字节）',
    data_size           BIGINT          DEFAULT 0               COMMENT '数据大小（字节）',
    index_size          BIGINT          DEFAULT 0               COMMENT '索引大小（字节）',
    innodb_buffer_usage DECIMAL(5,2)    DEFAULT 0.00            COMMENT 'InnoDB缓冲池使用率（%）',
    thread_count        INT             DEFAULT 0               COMMENT '线程数',
    uptime              BIGINT          DEFAULT 0               COMMENT '运行时长（秒）',
    version             VARCHAR(50)     DEFAULT ''              COMMENT '版本',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1异常 2离线）',
    monitor_time        DATETIME        NOT NULL                COMMENT '监控时间',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (db_id),
    KEY idx_db_name (db_name),
    KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='数据库监控表';

-- =============================================
-- 慢查询日志表
-- =============================================
DROP TABLE IF EXISTS sys_slow_query;
CREATE TABLE sys_slow_query (
    query_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '查询ID',
    db_name             VARCHAR(100)    NOT NULL                COMMENT '数据库名称',
    query_sql           TEXT            NOT NULL                COMMENT 'SQL语句',
    query_time          DECIMAL(10,3)   NOT NULL                COMMENT '查询时间（秒）',
    lock_time           DECIMAL(10,3)   DEFAULT 0.000           COMMENT '锁等待时间（秒）',
    rows_sent           BIGINT          DEFAULT 0               COMMENT '返回行数',
    rows_examined       BIGINT          DEFAULT 0               COMMENT '扫描行数',
    schema_name         VARCHAR(100)    DEFAULT ''              COMMENT '数据库',
    user_host           VARCHAR(100)    DEFAULT ''              COMMENT '用户主机',
    query_time_ms       BIGINT          DEFAULT 0               COMMENT '查询时间（毫秒）',
    execution_time      DATETIME        DEFAULT NULL            COMMENT '执行时间',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (query_id),
    KEY idx_db_name (db_name),
    KEY idx_query_time (query_time),
    KEY idx_execution_time (execution_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='慢查询日志表';

-- =============================================
-- 系统告警表
-- =============================================
DROP TABLE IF EXISTS sys_alert;
CREATE TABLE sys_alert (
    alert_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '告警ID',
    alert_no            VARCHAR(50)     NOT NULL                COMMENT '告警编号',
    alert_type          VARCHAR(50)     NOT NULL                COMMENT '告警类型（server服务 database数据库 cache缓存 application应用）',
    alert_level         CHAR(1)         NOT NULL                COMMENT '告警级别（1信息 2警告 3严重 4紧急）',
    alert_source        VARCHAR(100)    DEFAULT ''              COMMENT '告警源',
    alert_title         VARCHAR(200)    NOT NULL                COMMENT '告警标题',
    alert_content       TEXT            DEFAULT NULL            COMMENT '告警内容',
    target_id           BIGINT          DEFAULT NULL            COMMENT '目标ID',
    target_name         VARCHAR(100)    DEFAULT ''              COMMENT '目标名称',
    threshold_value     VARCHAR(100)    DEFAULT ''              COMMENT '阈值',
    actual_value        VARCHAR(100)    DEFAULT ''              COMMENT '实际值',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0未处理 1已确认 2已处理 3已忽略）',
    handle_user_id      BIGINT          DEFAULT NULL            COMMENT '处理人ID',
    handle_user_name    VARCHAR(50)     DEFAULT ''              COMMENT '处理人姓名',
    handle_time         DATETIME        DEFAULT NULL            COMMENT '处理时间',
    handle_remark       VARCHAR(500)    DEFAULT NULL            COMMENT '处理备注',
    notify_type         VARCHAR(100)    DEFAULT ''              COMMENT '通知方式（email邮件 sms短信 wechat微信）',
    notify_status       CHAR(1)         DEFAULT '0'             COMMENT '通知状态（0未发送 1已发送）',
    notify_time         DATETIME        DEFAULT NULL            COMMENT '通知时间',
    alert_time          DATETIME        NOT NULL                COMMENT '告警时间',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (alert_id),
    UNIQUE KEY uk_alert_no (alert_no),
    KEY idx_alert_type (alert_type),
    KEY idx_alert_level (alert_level),
    KEY idx_status (status),
    KEY idx_alert_time (alert_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='系统告警表';

-- =============================================
-- 告警规则表
-- =============================================
DROP TABLE IF EXISTS sys_alert_rule;
CREATE TABLE sys_alert_rule (
    rule_id             BIGINT          NOT NULL AUTO_INCREMENT COMMENT '规则ID',
    rule_name           VARCHAR(100)    NOT NULL                COMMENT '规则名称',
    rule_code           VARCHAR(50)     NOT NULL                COMMENT '规则编码',
    alert_type          VARCHAR(50)     NOT NULL                COMMENT '告警类型',
    alert_level         CHAR(1)         NOT NULL                COMMENT '告警级别',
    metric_name         VARCHAR(100)    NOT NULL                COMMENT '指标名称',
    metric_type         VARCHAR(50)     DEFAULT 'value'         COMMENT '指标类型（value值 rate比率）',
    operator            VARCHAR(10)     NOT NULL                COMMENT '操作符（> < >= <= == !=）',
    threshold           DECIMAL(10,2)   NOT NULL                COMMENT '阈值',
    duration            INT             DEFAULT 60              COMMENT '持续时间（秒）',
    silence_period      INT             DEFAULT 300             COMMENT '静默期（秒）',
    notify_type         VARCHAR(100)    DEFAULT ''              COMMENT '通知方式',
    notify_users        VARCHAR(500)    DEFAULT ''              COMMENT '通知人ID列表',
    notify_groups       VARCHAR(500)    DEFAULT ''              COMMENT '通知组ID列表',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0启用 1停用）',
    description         VARCHAR(500)    DEFAULT NULL            COMMENT '描述',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (rule_id),
    UNIQUE KEY uk_rule_code (rule_code),
    KEY idx_alert_type (alert_type),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='告警规则表';

-- =============================================
-- 系统性能统计表
-- =============================================
DROP TABLE IF EXISTS sys_performance_stat;
CREATE TABLE sys_performance_stat (
    stat_id             BIGINT          NOT NULL AUTO_INCREMENT COMMENT '统计ID',
    stat_type           VARCHAR(50)     NOT NULL                COMMENT '统计类型（api接口 page页面 service服务）',
    stat_key            VARCHAR(200)    NOT NULL                COMMENT '统计键（URL/服务名）',
    stat_date           DATE            NOT NULL                COMMENT '统计日期',
    stat_hour           INT             DEFAULT NULL            COMMENT '统计小时',
    request_count       BIGINT          DEFAULT 0               COMMENT '请求数',
    success_count       BIGINT          DEFAULT 0               COMMENT '成功数',
    fail_count          BIGINT          DEFAULT 0               COMMENT '失败数',
    success_rate        DECIMAL(5,2)    DEFAULT 0.00            COMMENT '成功率（%）',
    avg_response_time   INT             DEFAULT 0               COMMENT '平均响应时间（ms）',
    max_response_time   INT             DEFAULT 0               COMMENT '最大响应时间（ms）',
    min_response_time   INT             DEFAULT 0               COMMENT '最小响应时间（ms）',
    p50_response_time   INT             DEFAULT 0               COMMENT 'P50响应时间（ms）',
    p90_response_time   INT             DEFAULT 0               COMMENT 'P90响应时间（ms）',
    p99_response_time   INT             DEFAULT 0               COMMENT 'P99响应时间（ms）',
    qps                 DECIMAL(10,2)   DEFAULT 0.00            COMMENT 'QPS',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    PRIMARY KEY (stat_id),
    UNIQUE KEY uk_stat_key_date_hour (stat_type, stat_key, stat_date, stat_hour),
    KEY idx_stat_date (stat_date)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='系统性能统计表';

-- =============================================
-- 初始化告警规则数据
-- =============================================
INSERT INTO sys_alert_rule VALUES(1, 'CPU使用率告警', 'cpu_usage_alert', 'server', '2', 'cpu_usage', 'rate', '>', 80.00, 60, 300, 'email,sms', '', '', '0', 'CPU使用率超过80%告警', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_alert_rule VALUES(2, '内存使用率告警', 'memory_usage_alert', 'server', '2', 'memory_usage', 'rate', '>', 85.00, 60, 300, 'email,sms', '', '', '0', '内存使用率超过85%告警', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_alert_rule VALUES(3, '磁盘使用率告警', 'disk_usage_alert', 'server', '2', 'disk_usage', 'rate', '>', 90.00, 60, 300, 'email,sms', '', '', '0', '磁盘使用率超过90%告警', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_alert_rule VALUES(4, '缓存命中率告警', 'cache_hit_rate_alert', 'cache', '2', 'hit_rate', 'rate', '<', 80.00, 300, 600, 'email', '', '', '0', '缓存命中率低于80%告警', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_alert_rule VALUES(5, '慢查询告警', 'slow_query_alert', 'database', '2', 'query_time', 'value', '>', 3.00, 0, 300, 'email', '', '', '0', '查询时间超过3秒告警', 'admin', NOW(), '', NULL, 0);
INSERT INTO sys_alert_rule VALUES(6, '服务异常告警', 'service_error_alert', 'application', '3', 'fail_count', 'value', '>', 5.00, 60, 300, 'email,sms', '', '', '0', '连续失败超过5次告警', 'admin', NOW(), '', NULL, 0);