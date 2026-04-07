-- 文档分类表
DROP TABLE IF EXISTS doc_category;
CREATE TABLE doc_category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    ancestors VARCHAR(200) DEFAULT '' COMMENT '祖级列表',
    category_code VARCHAR(50) DEFAULT '' COMMENT '分类编码',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    category_type VARCHAR(20) DEFAULT 'doc' COMMENT '分类类型（doc文档 knowledge知识库）',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB COMMENT='文档分类表';

-- 文档信息表
DROP TABLE IF EXISTS doc_info;
CREATE TABLE doc_info (
    doc_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文档ID',
    doc_name VARCHAR(200) NOT NULL COMMENT '文档名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    category_name VARCHAR(50) DEFAULT '' COMMENT '分类名称',
    doc_type VARCHAR(20) DEFAULT 'file' COMMENT '文档类型（file文件 text文本）',
    file_path VARCHAR(500) DEFAULT '' COMMENT '文件路径',
    file_size VARCHAR(50) DEFAULT '' COMMENT '文件大小',
    file_ext VARCHAR(20) DEFAULT '' COMMENT '文件扩展名',
    content TEXT COMMENT '文档内容',
    version BIGINT DEFAULT 1 COMMENT '版本号',
    tags VARCHAR(200) DEFAULT '' COMMENT '标签',
    read_count INT DEFAULT 0 COMMENT '阅读次数',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    permission VARCHAR(20) DEFAULT 'public' COMMENT '权限（public公开 private私有 dept部门）',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0草稿 1发布 2归档）',
    create_user_id BIGINT DEFAULT NULL COMMENT '创建人ID',
    create_user_name VARCHAR(50) DEFAULT '' COMMENT '创建人姓名',
    publish_time DATETIME DEFAULT NULL COMMENT '发布时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB COMMENT='文档信息表';

-- 文档版本表
DROP TABLE IF EXISTS doc_version;
CREATE TABLE doc_version (
    version_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '版本ID',
    doc_id BIGINT NOT NULL COMMENT '文档ID',
    version_no BIGINT DEFAULT 1 COMMENT '版本号',
    file_path VARCHAR(500) DEFAULT '' COMMENT '文件路径',
    file_size VARCHAR(50) DEFAULT '' COMMENT '文件大小',
    change_log VARCHAR(500) DEFAULT '' COMMENT '变更说明',
    create_user_id BIGINT DEFAULT NULL COMMENT '创建人ID',
    create_user_name VARCHAR(50) DEFAULT '' COMMENT '创建人姓名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB COMMENT='文档版本表';

-- 文档收藏表
DROP TABLE IF EXISTS doc_favorite;
CREATE TABLE doc_favorite (
    favorite_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '收藏ID',
    doc_id BIGINT NOT NULL COMMENT '文档ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) DEFAULT '' COMMENT '用户姓名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间'
) ENGINE=InnoDB COMMENT='文档收藏表';

-- 知识库文章表
DROP TABLE IF EXISTS knowledge_article;
CREATE TABLE knowledge_article (
    article_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID',
    title VARCHAR(200) NOT NULL COMMENT '文章标题',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    category_name VARCHAR(50) DEFAULT '' COMMENT '分类名称',
    summary VARCHAR(500) DEFAULT '' COMMENT '文章摘要',
    content LONGTEXT COMMENT '文章内容',
    article_type VARCHAR(20) DEFAULT 'article' COMMENT '文章类型（article文章 faq常见问题 manual操作手册）',
    tags VARCHAR(200) DEFAULT '' COMMENT '标签',
    read_count INT DEFAULT 0 COMMENT '阅读次数',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    comment_count INT DEFAULT 0 COMMENT '评论次数',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0草稿 1发布 2下架）',
    author_id BIGINT DEFAULT NULL COMMENT '作者ID',
    author_name VARCHAR(50) DEFAULT '' COMMENT '作者姓名',
    publish_time DATETIME DEFAULT NULL COMMENT '发布时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志'
) ENGINE=InnoDB COMMENT='知识库文章表';

-- 插入测试数据
INSERT INTO doc_category (category_name, parent_id, ancestors, category_code, sort, category_type, status) VALUES
('Company Docs', 0, '0', 'COMP', 1, 'doc', '0'),
('Technical Docs', 0, '0', 'TECH', 2, 'doc', '0'),
('HR Docs', 1, '0,1', 'COMP-HR', 1, 'doc', '0'),
('Finance Docs', 1, '0,1', 'COMP-FIN', 2, 'doc', '0'),
('Development', 2, '0,2', 'TECH-DEV', 1, 'doc', '0');

INSERT INTO doc_category (category_name, parent_id, ancestors, category_code, sort, category_type, status) VALUES
('Knowledge Base', 0, '0', 'KB', 3, 'knowledge', '0'),
('FAQ', 6, '0,6', 'KB-FAQ', 1, 'knowledge', '0'),
('Manuals', 6, '0,6', 'KB-MAN', 2, 'knowledge', '0');

INSERT INTO doc_info (doc_name, category_id, category_name, doc_type, file_ext, version, read_count, download_count, status, create_user_id, create_user_name) VALUES
('Employee Handbook', 3, 'HR Docs', 'file', 'pdf', 1, 100, 50, '1', 1, 'admin'),
('Development Guidelines', 5, 'Development', 'file', 'docx', 2, 200, 80, '1', 1, 'admin'),
('API Documentation', 5, 'Development', 'text', '', 1, 300, 0, '1', 1, 'admin');

INSERT INTO knowledge_article (title, category_id, category_name, summary, article_type, read_count, like_count, comment_count, status, author_id, author_name) VALUES
('Getting Started Guide', 8, 'Manuals', 'A quick guide to get started with the system', 'manual', 500, 50, 10, '1', 1, 'admin'),
('FAQ: How to reset password', 7, 'FAQ', 'Step by step guide to reset your password', 'faq', 1000, 100, 20, '1', 1, 'admin'),
('System Architecture Overview', 8, 'Manuals', 'Detailed overview of system architecture', 'article', 300, 30, 5, '1', 1, 'admin');