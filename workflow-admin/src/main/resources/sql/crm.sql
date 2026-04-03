-- =============================================
-- 客户管理模块数据库表
-- =============================================

-- =============================================
-- 客户信息表
-- =============================================
DROP TABLE IF EXISTS crm_customer;
CREATE TABLE crm_customer (
    customer_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '客户ID',
    customer_no         VARCHAR(50)     NOT NULL                COMMENT '客户编号',
    customer_name       VARCHAR(100)    NOT NULL                COMMENT '客户名称',
    customer_type       CHAR(1)         DEFAULT '1'             COMMENT '客户类型（1企业 2个人）',
    industry            VARCHAR(50)     DEFAULT ''              COMMENT '所属行业',
    scale              VARCHAR(50)     DEFAULT ''              COMMENT '企业规模',
    source             VARCHAR(50)     DEFAULT ''              COMMENT '客户来源',
    level              CHAR(1)         DEFAULT 'C'             COMMENT '客户等级（A、B、C、D）',
    status             CHAR(1)         DEFAULT '1'             COMMENT '客户状态（0潜在客户 1意向客户 2成交客户 3流失客户）',
    owner_id           BIGINT          DEFAULT NULL            COMMENT '负责人ID',
    owner_name         VARCHAR(50)     DEFAULT ''              COMMENT '负责人姓名',
    dept_id            BIGINT          DEFAULT NULL            COMMENT '所属部门ID',
    province           VARCHAR(50)     DEFAULT ''              COMMENT '省份',
    city               VARCHAR(50)     DEFAULT ''              COMMENT '城市',
    address            VARCHAR(200)    DEFAULT ''              COMMENT '详细地址',
    website            VARCHAR(100)    DEFAULT ''              COMMENT '公司网站',
    fax                VARCHAR(20)     DEFAULT ''              COMMENT '传真',
    remark             VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    next_follow_time   DATETIME        DEFAULT NULL            COMMENT '下次跟进时间',
    deal_time          DATETIME        DEFAULT NULL            COMMENT '成交时间',
    create_by          VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time        DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by          VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time        DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted            INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (customer_id),
    UNIQUE KEY uk_customer_no (customer_no),
    KEY idx_owner_id (owner_id),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='客户信息表';

-- =============================================
-- 联系人表
-- =============================================
DROP TABLE IF EXISTS crm_contact;
CREATE TABLE crm_contact (
    contact_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '联系人ID',
    customer_id         BIGINT          NOT NULL                COMMENT '客户ID',
    contact_name        VARCHAR(50)     NOT NULL                COMMENT '联系人姓名',
    gender             CHAR(1)         DEFAULT '0'             COMMENT '性别（0男 1女）',
    position           VARCHAR(50)     DEFAULT ''              COMMENT '职位',
    department         VARCHAR(50)     DEFAULT ''              COMMENT '部门',
    phone              VARCHAR(20)     DEFAULT ''              COMMENT '手机号',
    telephone          VARCHAR(20)     DEFAULT ''              COMMENT '座机',
    email              VARCHAR(100)    DEFAULT ''              COMMENT '邮箱',
    wechat             VARCHAR(50)     DEFAULT ''              COMMENT '微信号',
    qq                 VARCHAR(20)     DEFAULT ''              COMMENT 'QQ号',
    birthday           DATE            DEFAULT NULL            COMMENT '生日',
    is_primary         CHAR(1)         DEFAULT '0'             COMMENT '是否主要联系人（0否 1是）',
    is_decision_maker  CHAR(1)         DEFAULT '0'             COMMENT '是否决策人（0否 1是）',
    address            VARCHAR(200)    DEFAULT ''              COMMENT '地址',
    remark             VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by          VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time        DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by          VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time        DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted            INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (contact_id),
    KEY idx_customer_id (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='联系人表';

-- =============================================
-- 跟进记录表
-- =============================================
DROP TABLE IF EXISTS crm_follow;
CREATE TABLE crm_follow (
    follow_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '跟进ID',
    customer_id         BIGINT          NOT NULL                COMMENT '客户ID',
    customer_name       VARCHAR(100)    DEFAULT ''              COMMENT '客户名称',
    contact_id          BIGINT          DEFAULT NULL            COMMENT '联系人ID',
    contact_name        VARCHAR(50)     DEFAULT ''              COMMENT '联系人姓名',
    user_id             BIGINT          NOT NULL                COMMENT '跟进人ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '跟进人姓名',
    follow_type         VARCHAR(50)     NOT NULL                COMMENT '跟进方式（电话、拜访、微信、邮件等）',
    follow_stage        VARCHAR(50)     DEFAULT ''              COMMENT '跟进阶段',
    content             TEXT            DEFAULT NULL            COMMENT '跟进内容',
    next_time           DATETIME        DEFAULT NULL            COMMENT '下次跟进时间',
    next_content        VARCHAR(500)    DEFAULT NULL            COMMENT '下次跟进计划',
    attachment          VARCHAR(255)    DEFAULT NULL            COMMENT '附件',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (follow_id),
    KEY idx_customer_id (customer_id),
    KEY idx_user_id (user_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='跟进记录表';

-- =============================================
-- 合同表
-- =============================================
DROP TABLE IF EXISTS crm_contract;
CREATE TABLE crm_contract (
    contract_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '合同ID',
    contract_no         VARCHAR(50)     NOT NULL                COMMENT '合同编号',
    contract_name       VARCHAR(100)    NOT NULL                COMMENT '合同名称',
    customer_id         BIGINT          NOT NULL                COMMENT '客户ID',
    customer_name       VARCHAR(100)    DEFAULT ''              COMMENT '客户名称',
    contract_type       VARCHAR(50)     DEFAULT ''              COMMENT '合同类型',
    contract_amount     DECIMAL(12,2)   DEFAULT 0.00            COMMENT '合同金额',
    received_amount     DECIMAL(12,2)   DEFAULT 0.00            COMMENT '已收款金额',
    start_date          DATE            DEFAULT NULL            COMMENT '开始日期',
    end_date            DATE            DEFAULT NULL            COMMENT '结束日期',
    sign_date           DATE            DEFAULT NULL            COMMENT '签订日期',
    owner_id            BIGINT          DEFAULT NULL            COMMENT '负责人ID',
    owner_name          VARCHAR(50)     DEFAULT ''              COMMENT '负责人姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '部门ID',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0草稿 1审批中 2生效中 3已完成 4已作废）',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    file_path           VARCHAR(255)    DEFAULT NULL            COMMENT '合同文件',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (contract_id),
    UNIQUE KEY uk_contract_no (contract_no),
    KEY idx_customer_id (customer_id),
    KEY idx_owner_id (owner_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='合同表';

-- =============================================
-- 合同产品明细表
-- =============================================
DROP TABLE IF EXISTS crm_contract_product;
CREATE TABLE crm_contract_product (
    product_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '产品ID',
    contract_id         BIGINT          NOT NULL                COMMENT '合同ID',
    product_name        VARCHAR(100)    NOT NULL                COMMENT '产品名称',
    product_code        VARCHAR(50)     DEFAULT ''              COMMENT '产品编码',
    spec               VARCHAR(100)    DEFAULT ''              COMMENT '规格',
    unit               VARCHAR(20)     DEFAULT ''              COMMENT '单位',
    quantity           INT             DEFAULT 1               COMMENT '数量',
    price              DECIMAL(12,2)   DEFAULT 0.00            COMMENT '单价',
    discount           DECIMAL(5,2)    DEFAULT 100.00          COMMENT '折扣（%）',
    amount             DECIMAL(12,2)   DEFAULT 0.00            COMMENT '金额',
    remark             VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_time        DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (product_id),
    KEY idx_contract_id (contract_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='合同产品明细表';

-- =============================================
-- 回款记录表
-- =============================================
DROP TABLE IF EXISTS crm_payment;
CREATE TABLE crm_payment (
    payment_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '回款ID',
    payment_no          VARCHAR(50)     NOT NULL                COMMENT '回款编号',
    contract_id         BIGINT          NOT NULL                COMMENT '合同ID',
    contract_no         VARCHAR(50)     DEFAULT ''              COMMENT '合同编号',
    customer_id         BIGINT          NOT NULL                COMMENT '客户ID',
    customer_name       VARCHAR(100)    DEFAULT ''              COMMENT '客户名称',
    payment_amount      DECIMAL(12,2)   NOT NULL                COMMENT '回款金额',
    payment_date        DATE            NOT NULL                COMMENT '回款日期',
    payment_type        VARCHAR(50)     DEFAULT ''              COMMENT '回款方式',
    account_name        VARCHAR(100)    DEFAULT ''              COMMENT '收款账户名称',
    account_no          VARCHAR(50)     DEFAULT ''              COMMENT '收款账号',
    invoice_status      CHAR(1)         DEFAULT '0'             COMMENT '开票状态（0未开票 1已开票）',
    invoice_id          BIGINT          DEFAULT NULL            COMMENT '发票ID',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待确认 1已确认 2已作废）',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (payment_id),
    UNIQUE KEY uk_payment_no (payment_no),
    KEY idx_contract_id (contract_id),
    KEY idx_customer_id (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='回款记录表';