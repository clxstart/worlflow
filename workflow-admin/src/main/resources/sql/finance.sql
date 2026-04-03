-- =============================================
-- 财务管理模块数据库表
-- =============================================

-- =============================================
-- 收入记录表
-- =============================================
DROP TABLE IF EXISTS finance_income;
CREATE TABLE finance_income (
    income_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '收入ID',
    income_no           VARCHAR(50)     NOT NULL                COMMENT '收入编号',
    income_type         VARCHAR(50)     NOT NULL                COMMENT '收入类型',
    income_source       VARCHAR(100)    DEFAULT ''              COMMENT '收入来源',
    amount              DECIMAL(12,2)   NOT NULL                COMMENT '金额',
    currency            VARCHAR(10)     DEFAULT 'CNY'           COMMENT '币种',
    income_date         DATE            NOT NULL                COMMENT '收入日期',
    payment_method      VARCHAR(50)     DEFAULT ''              COMMENT '收款方式',
    payer              VARCHAR(100)    DEFAULT ''              COMMENT '付款方',
    contract_id         BIGINT          DEFAULT NULL            COMMENT '关联合同ID',
    project_id          BIGINT          DEFAULT NULL            COMMENT '关联项目ID',
    invoice_status      CHAR(1)         DEFAULT '0'             COMMENT '开票状态（0未开票 1已开票）',
    invoice_id          BIGINT          DEFAULT NULL            COMMENT '发票ID',
    handler_id          BIGINT          DEFAULT NULL            COMMENT '经办人ID',
    handler_name        VARCHAR(50)     DEFAULT ''              COMMENT '经办人姓名',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待确认 1已确认 2已取消）',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (income_id),
    UNIQUE KEY uk_income_no (income_no),
    KEY idx_income_date (income_date),
    KEY idx_income_type (income_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='收入记录表';

-- =============================================
-- 支出记录表
-- =============================================
DROP TABLE IF EXISTS finance_expense;
CREATE TABLE finance_expense (
    expense_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '支出ID',
    expense_no          VARCHAR(50)     NOT NULL                COMMENT '支出编号',
    expense_type        VARCHAR(50)     NOT NULL                COMMENT '支出类型',
    expense_category    VARCHAR(50)     DEFAULT ''              COMMENT '支出分类',
    amount              DECIMAL(12,2)   NOT NULL                COMMENT '金额',
    currency            VARCHAR(10)     DEFAULT 'CNY'           COMMENT '币种',
    expense_date        DATE            NOT NULL                COMMENT '支出日期',
    payment_method      VARCHAR(50)     DEFAULT ''              COMMENT '付款方式',
    payee              VARCHAR(100)    DEFAULT ''              COMMENT '收款方',
    bank_account        VARCHAR(50)     DEFAULT ''              COMMENT '收款账户',
    contract_id         BIGINT          DEFAULT NULL            COMMENT '关联合同ID',
    project_id          BIGINT          DEFAULT NULL            COMMENT '关联项目ID',
    invoice_status      CHAR(1)         DEFAULT '0'             COMMENT '收票状态（0未收票 1已收票）',
    invoice_id          BIGINT          DEFAULT NULL            COMMENT '发票ID',
    handler_id          BIGINT          DEFAULT NULL            COMMENT '经办人ID',
    handler_name        VARCHAR(50)     DEFAULT ''              COMMENT '经办人姓名',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待审批 1已审批 2已支付 3已取消）',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (expense_id),
    UNIQUE KEY uk_expense_no (expense_no),
    KEY idx_expense_date (expense_date),
    KEY idx_expense_type (expense_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='支出记录表';

-- =============================================
-- 报销申请表
-- =============================================
DROP TABLE IF EXISTS finance_reimbursement;
CREATE TABLE finance_reimbursement (
    reimbursement_id    BIGINT          NOT NULL AUTO_INCREMENT COMMENT '报销ID',
    reimbursement_no    VARCHAR(50)     NOT NULL                COMMENT '报销编号',
    user_id             BIGINT          NOT NULL                COMMENT '申请人ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '申请人姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '部门ID',
    reimbursement_type  VARCHAR(50)     NOT NULL                COMMENT '报销类型',
    amount              DECIMAL(12,2)   NOT NULL                COMMENT '报销金额',
    currency            VARCHAR(10)     DEFAULT 'CNY'           COMMENT '币种',
    expense_date        DATE            NOT NULL                COMMENT '费用发生日期',
    description         VARCHAR(500)    DEFAULT NULL            COMMENT '报销说明',
    attachment          VARCHAR(255)    DEFAULT NULL            COMMENT '附件',
    project_id          BIGINT          DEFAULT NULL            COMMENT '关联项目ID',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '当前审批人ID',
    approve_status      CHAR(1)         DEFAULT '0'             COMMENT '审批状态（0待审批 1审批中 2已通过 3已拒绝 4已撤销）',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '最后审批时间',
    pay_status          CHAR(1)         DEFAULT '0'             COMMENT '支付状态（0未支付 1已支付）',
    pay_time            DATETIME        DEFAULT NULL            COMMENT '支付时间',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (reimbursement_id),
    UNIQUE KEY uk_reimbursement_no (reimbursement_no),
    KEY idx_user_id (user_id),
    KEY idx_approve_status (approve_status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='报销申请表';

-- =============================================
-- 报销明细表
-- =============================================
DROP TABLE IF EXISTS finance_reimbursement_detail;
CREATE TABLE finance_reimbursement_detail (
    detail_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    reimbursement_id    BIGINT          NOT NULL                COMMENT '报销ID',
    expense_type        VARCHAR(50)     NOT NULL                COMMENT '费用类型',
    amount              DECIMAL(12,2)   NOT NULL                COMMENT '金额',
    expense_date        DATE            DEFAULT NULL            COMMENT '费用日期',
    description         VARCHAR(500)    DEFAULT NULL            COMMENT '说明',
    invoice_no          VARCHAR(50)     DEFAULT ''              COMMENT '发票号',
    attachment          VARCHAR(255)    DEFAULT NULL            COMMENT '附件',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    PRIMARY KEY (detail_id),
    KEY idx_reimbursement_id (reimbursement_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='报销明细表';

-- =============================================
-- 发票管理表
-- =============================================
DROP TABLE IF EXISTS finance_invoice;
CREATE TABLE finance_invoice (
    invoice_id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '发票ID',
    invoice_no          VARCHAR(50)     NOT NULL                COMMENT '发票号码',
    invoice_type        VARCHAR(50)     NOT NULL                COMMENT '发票类型（增值税专用发票、增值税普通发票、电子发票等）',
    invoice_category    CHAR(1)         NOT NULL                COMMENT '发票类别（1进项 2销项）',
    amount              DECIMAL(12,2)   NOT NULL                COMMENT '发票金额',
    tax_amount          DECIMAL(12,2)   DEFAULT 0.00            COMMENT '税额',
    total_amount        DECIMAL(12,2)   NOT NULL                COMMENT '价税合计',
    company_name        VARCHAR(100)    NOT NULL                COMMENT '开票公司',
    tax_no             VARCHAR(50)     DEFAULT ''              COMMENT '纳税人识别号',
    company_address     VARCHAR(200)    DEFAULT ''              COMMENT '公司地址',
    company_phone       VARCHAR(20)     DEFAULT ''              COMMENT '公司电话',
    bank_name          VARCHAR(100)    DEFAULT ''              COMMENT '开户银行',
    bank_account        VARCHAR(50)     DEFAULT ''              COMMENT '银行账号',
    invoice_date        DATE            NOT NULL                COMMENT '开票日期',
    verify_status       CHAR(1)         DEFAULT '0'             COMMENT '查验状态（0未查验 1已查验 2查验失败）',
    verify_time         DATETIME        DEFAULT NULL            COMMENT '查验时间',
    file_path           VARCHAR(255)    DEFAULT NULL            COMMENT '发票文件路径',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0有效 1作废 2红冲）',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (invoice_id),
    UNIQUE KEY uk_invoice_no (invoice_no),
    KEY idx_invoice_date (invoice_date),
    KEY idx_invoice_category (invoice_category)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='发票管理表';

-- =============================================
-- 预算表
-- =============================================
DROP TABLE IF EXISTS finance_budget;
CREATE TABLE finance_budget (
    budget_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '预算ID',
    budget_no           VARCHAR(50)     NOT NULL                COMMENT '预算编号',
    budget_name         VARCHAR(100)    NOT NULL                COMMENT '预算名称',
    budget_type         VARCHAR(50)     NOT NULL                COMMENT '预算类型',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '部门ID',
    project_id          BIGINT          DEFAULT NULL            COMMENT '项目ID',
    budget_year         INT             NOT NULL                COMMENT '预算年度',
    budget_month        INT             DEFAULT NULL            COMMENT '预算月份（年度预算为空）',
    total_amount        DECIMAL(12,2)   NOT NULL                COMMENT '预算总额',
    used_amount         DECIMAL(12,2)   DEFAULT 0.00            COMMENT '已使用金额',
    remaining_amount    DECIMAL(12,2)   DEFAULT 0.00            COMMENT '剩余金额',
    freeze_amount       DECIMAL(12,2)   DEFAULT 0.00            COMMENT '冻结金额',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0草稿 1已生效 2已失效）',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (budget_id),
    UNIQUE KEY uk_budget_no (budget_no),
    KEY idx_budget_year (budget_year),
    KEY idx_dept_id (dept_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='预算表';

-- =============================================
-- 预算明细表
-- =============================================
DROP TABLE IF EXISTS finance_budget_detail;
CREATE TABLE finance_budget_detail (
    detail_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    budget_id           BIGINT          NOT NULL                COMMENT '预算ID',
    expense_type        VARCHAR(50)     NOT NULL                COMMENT '费用类型',
    budget_amount       DECIMAL(12,2)   NOT NULL                COMMENT '预算金额',
    used_amount         DECIMAL(12,2)   DEFAULT 0.00            COMMENT '已使用金额',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    PRIMARY KEY (detail_id),
    KEY idx_budget_id (budget_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='预算明细表';