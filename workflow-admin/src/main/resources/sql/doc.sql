-- =============================================
-- 文档管理模块数据库表
-- =============================================

-- =============================================
-- 文档分类表
-- =============================================
DROP TABLE IF EXISTS doc_category;
CREATE TABLE doc_category (
    category_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    category_name       VARCHAR(50)     NOT NULL                COMMENT '分类名称',
    parent_id           BIGINT          DEFAULT 0               COMMENT '父分类ID',
    ancestors           VARCHAR(200)    DEFAULT ''              COMMENT '祖级列表',
    category_code       VARCHAR(50)     DEFAULT ''              COMMENT '分类编码',
    sort               INT             DEFAULT 0               COMMENT '排序',
    icon               VARCHAR(100)    DEFAULT ''              COMMENT '图标',
    status             CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    remark             VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by          VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time        DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by          VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time        DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted            INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文档分类表';

-- =============================================
-- 文档表
-- =============================================
DROP TABLE IF EXISTS doc_document;
CREATE TABLE doc_document (
    doc_id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '文档ID',
    doc_no              VARCHAR(50)     NOT NULL                COMMENT '文档编号',
    doc_name            VARCHAR(200)    NOT NULL                COMMENT '文档名称',
    category_id         BIGINT          NOT NULL                COMMENT '分类ID',
    category_name       VARCHAR(50)     DEFAULT ''              COMMENT '分类名称',
    doc_type            VARCHAR(50)     DEFAULT ''              COMMENT '文档类型（pdf、doc、xls等）',
    file_path           VARCHAR(500)    NOT NULL                COMMENT '文件路径',
    file_size           BIGINT          DEFAULT 0               COMMENT '文件大小（字节）',
    version             VARCHAR(20)     DEFAULT '1.0'           COMMENT '当前版本',
    keywords            VARCHAR(200)    DEFAULT ''              COMMENT '关键词',
    summary             VARCHAR(500)    DEFAULT NULL            COMMENT '摘要',
    author_id           BIGINT          DEFAULT NULL            COMMENT '作者ID',
    author_name         VARCHAR(50)     DEFAULT ''              COMMENT '作者姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '所属部门ID',
    is_public           CHAR(1)         DEFAULT '0'             COMMENT '是否公开（0否 1是）',
    download_count      INT             DEFAULT 0               COMMENT '下载次数',
    view_count          INT             DEFAULT 0               COMMENT '浏览次数',
    favorite_count      INT             DEFAULT 0               COMMENT '收藏次数',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0草稿 1已发布 2已下架 3已归档）',
    publish_time        DATETIME        DEFAULT NULL            COMMENT '发布时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (doc_id),
    UNIQUE KEY uk_doc_no (doc_no),
    KEY idx_category_id (category_id),
    KEY idx_author_id (author_id),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文档表';

-- =============================================
-- 文档版本表
-- =============================================
DROP TABLE IF EXISTS doc_version;
CREATE TABLE doc_version (
    version_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '版本ID',
    doc_id              BIGINT          NOT NULL                COMMENT '文档ID',
    version_no          VARCHAR(20)     NOT NULL                COMMENT '版本号',
    file_path           VARCHAR(500)    NOT NULL                COMMENT '文件路径',
    file_size           BIGINT          DEFAULT 0               COMMENT '文件大小（字节）',
    change_desc         VARCHAR(500)    DEFAULT NULL            COMMENT '变更说明',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (version_id),
    KEY idx_doc_id (doc_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文档版本表';

-- =============================================
-- 文档权限表
-- =============================================
DROP TABLE IF EXISTS doc_permission;
CREATE TABLE doc_permission (
    permission_id       BIGINT          NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    doc_id              BIGINT          NOT NULL                COMMENT '文档ID',
    target_type         CHAR(1)         NOT NULL                COMMENT '目标类型（1用户 2部门 3角色）',
    target_id           BIGINT          NOT NULL                COMMENT '目标ID',
    permission_type     CHAR(1)         NOT NULL                COMMENT '权限类型（1查看 2下载 3编辑 4管理）',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (permission_id),
    KEY idx_doc_id (doc_id),
    UNIQUE KEY uk_doc_target (doc_id, target_type, target_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文档权限表';

-- =============================================
-- 文档收藏表
-- =============================================
DROP TABLE IF EXISTS doc_favorite;
CREATE TABLE doc_favorite (
    favorite_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    doc_id              BIGINT          NOT NULL                COMMENT '文档ID',
    user_id             BIGINT          NOT NULL                COMMENT '用户ID',
    create_time         DATETIME        DEFAULT NULL            COMMENT '收藏时间',
    PRIMARY KEY (favorite_id),
    UNIQUE KEY uk_doc_user (doc_id, user_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文档收藏表';

-- =============================================
-- 文档操作日志表
-- =============================================
DROP TABLE IF EXISTS doc_oper_log;
CREATE TABLE doc_oper_log (
    log_id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    doc_id              BIGINT          NOT NULL                COMMENT '文档ID',
    doc_name            VARCHAR(200)    DEFAULT ''              COMMENT '文档名称',
    oper_type           CHAR(1)         NOT NULL                COMMENT '操作类型（1查看 2下载 3编辑 4删除 5分享）',
    user_id             BIGINT          DEFAULT NULL            COMMENT '操作人ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '操作人姓名',
    oper_time           DATETIME        DEFAULT NULL            COMMENT '操作时间',
    oper_ip             VARCHAR(50)     DEFAULT ''              COMMENT '操作IP',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    PRIMARY KEY (log_id),
    KEY idx_doc_id (doc_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文档操作日志表';

-- =============================================
-- 知识库表
-- =============================================
DROP TABLE IF EXISTS doc_knowledge;
CREATE TABLE doc_knowledge (
    knowledge_id        BIGINT          NOT NULL AUTO_INCREMENT COMMENT '知识ID',
    knowledge_title     VARCHAR(200)    NOT NULL                COMMENT '知识标题',
    knowledge_type      VARCHAR(50)     DEFAULT ''              COMMENT '知识类型',
    content             TEXT            DEFAULT NULL            COMMENT '知识内容',
    keywords            VARCHAR(200)    DEFAULT ''              COMMENT '关键词',
    source              VARCHAR(100)    DEFAULT ''              COMMENT '来源',
    author_id           BIGINT          DEFAULT NULL            COMMENT '作者ID',
    author_name         VARCHAR(50)     DEFAULT ''              COMMENT '作者姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '所属部门ID',
    is_public           CHAR(1)         DEFAULT '0'             COMMENT '是否公开（0否 1是）',
    view_count          INT             DEFAULT 0               COMMENT '浏览次数',
    like_count          INT             DEFAULT 0               COMMENT '点赞次数',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0草稿 1已发布 2已下架）',
    publish_time        DATETIME        DEFAULT NULL            COMMENT '发布时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (knowledge_id),
    KEY idx_knowledge_type (knowledge_type),
    KEY idx_author_id (author_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='知识库表';

-- =============================================
-- 初始化文档分类数据
-- =============================================
INSERT INTO doc_category VALUES(1, '公司制度', 0, '', 'policy', 1, 'document', '0', '公司规章制度', 'admin', NOW(), '', NULL, 0);
INSERT INTO doc_category VALUES(2, '操作手册', 0, '', 'manual', 2, 'book', '0', '系统操作手册', 'admin', NOW(), '', NULL, 0);
INSERT INTO doc_category VALUES(3, '技术文档', 0, '', 'tech', 3, 'code', '0', '技术开发文档', 'admin', NOW(), '', NULL, 0);
INSERT INTO doc_category VALUES(4, '产品文档', 0, '', 'product', 4, 'product', '0', '产品相关文档', 'admin', NOW(), '', NULL, 0);
INSERT INTO doc_category VALUES(5, '培训资料', 0, '', 'training', 5, 'education', '0', '员工培训资料', 'admin', NOW(), '', NULL, 0);
INSERT INTO doc_category VALUES(6, '合同模板', 0, '', 'contract', 6, 'contract', '0', '合同模板文件', 'admin', NOW(), '', NULL, 0);
INSERT INTO doc_category VALUES(7, '会议纪要', 0, '', 'meeting', 7, 'meeting', '0', '会议纪要记录', 'admin', NOW(), '', NULL, 0);