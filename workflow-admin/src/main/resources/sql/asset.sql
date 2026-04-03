-- =============================================
-- 资产管理模块数据库表
-- =============================================

-- =============================================
-- 资产分类表
-- =============================================
DROP TABLE IF EXISTS asset_category;
CREATE TABLE asset_category (
    category_id         BIGINT          NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    category_name       VARCHAR(50)     NOT NULL                COMMENT '分类名称',
    parent_id           BIGINT          DEFAULT 0               COMMENT '父分类ID',
    ancestors           VARCHAR(200)    DEFAULT ''              COMMENT '祖级列表',
    category_code       VARCHAR(50)     DEFAULT ''              COMMENT '分类编码',
    sort               INT             DEFAULT 0               COMMENT '排序',
    depreciation_method VARCHAR(50)     DEFAULT 'straight_line' COMMENT '折旧方式',
    depreciation_years INT             DEFAULT 5               COMMENT '折旧年限',
    status             CHAR(1)         DEFAULT '0'             COMMENT '状态（0正常 1停用）',
    remark             VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by          VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time        DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by          VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time        DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted            INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='资产分类表';

-- =============================================
-- 资产信息表
-- =============================================
DROP TABLE IF EXISTS asset_info;
CREATE TABLE asset_info (
    asset_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '资产ID',
    asset_no            VARCHAR(50)     NOT NULL                COMMENT '资产编号',
    asset_name          VARCHAR(100)    NOT NULL                COMMENT '资产名称',
    category_id         BIGINT          NOT NULL                COMMENT '资产分类ID',
    category_name       VARCHAR(50)     DEFAULT ''              COMMENT '资产分类名称',
    brand              VARCHAR(100)    DEFAULT ''              COMMENT '品牌',
    model              VARCHAR(100)    DEFAULT ''              COMMENT '型号',
    spec               VARCHAR(200)    DEFAULT ''              COMMENT '规格',
    unit               VARCHAR(20)     DEFAULT ''              COMMENT '单位',
    quantity           INT             DEFAULT 1               COMMENT '数量',
    purchase_date      DATE            DEFAULT NULL            COMMENT '购入日期',
    purchase_price     DECIMAL(12,2)   DEFAULT 0.00            COMMENT '购入价格',
    original_value     DECIMAL(12,2)   DEFAULT 0.00            COMMENT '原值',
    net_value          DECIMAL(12,2)   DEFAULT 0.00            COMMENT '净值',
    depreciation_value DECIMAL(12,2)   DEFAULT 0.00            COMMENT '已折旧金额',
    warranty_date      DATE            DEFAULT NULL            COMMENT '保修截止日期',
    supplier           VARCHAR(100)    DEFAULT ''              COMMENT '供应商',
    location           VARCHAR(200)    DEFAULT ''              COMMENT '存放位置',
    status             CHAR(1)         DEFAULT '0'             COMMENT '状态（0闲置 1在用 2维修 3报废）',
    user_id            BIGINT          DEFAULT NULL            COMMENT '使用人ID',
    user_name          VARCHAR(50)     DEFAULT ''              COMMENT '使用人姓名',
    dept_id            BIGINT          DEFAULT NULL            COMMENT '使用部门ID',
    dept_name          VARCHAR(50)     DEFAULT ''              COMMENT '使用部门名称',
    image              VARCHAR(255)    DEFAULT NULL            COMMENT '资产图片',
    remark             VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by          VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time        DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by          VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time        DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted            INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (asset_id),
    UNIQUE KEY uk_asset_no (asset_no),
    KEY idx_category_id (category_id),
    KEY idx_status (status),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='资产信息表';

-- =============================================
-- 资产领用表
-- =============================================
DROP TABLE IF EXISTS asset_use;
CREATE TABLE asset_use (
    use_id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '领用ID',
    use_no              VARCHAR(50)     NOT NULL                COMMENT '领用单号',
    asset_id            BIGINT          NOT NULL                COMMENT '资产ID',
    asset_no            VARCHAR(50)     DEFAULT ''              COMMENT '资产编号',
    asset_name          VARCHAR(100)    DEFAULT ''              COMMENT '资产名称',
    user_id             BIGINT          NOT NULL                COMMENT '领用人ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '领用人姓名',
    dept_id             BIGINT          DEFAULT NULL            COMMENT '领用部门ID',
    dept_name           VARCHAR(50)     DEFAULT ''              COMMENT '领用部门名称',
    use_date            DATE            NOT NULL                COMMENT '领用日期',
    expect_return_date  DATE            DEFAULT NULL            COMMENT '预计归还日期',
    actual_return_date  DATE            DEFAULT NULL            COMMENT '实际归还日期',
    return_status       CHAR(1)         DEFAULT '0'             COMMENT '归还状态（0未归还 1已归还）',
    return_remark       VARCHAR(500)    DEFAULT NULL            COMMENT '归还备注',
    purpose             VARCHAR(500)    DEFAULT NULL            COMMENT '领用用途',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待审批 1已审批 2已归还 3已拒绝）',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (use_id),
    UNIQUE KEY uk_use_no (use_no),
    KEY idx_asset_id (asset_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='资产领用表';

-- =============================================
-- 资产维修表
-- =============================================
DROP TABLE IF EXISTS asset_repair;
CREATE TABLE asset_repair (
    repair_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '维修ID',
    repair_no           VARCHAR(50)     NOT NULL                COMMENT '维修单号',
    asset_id            BIGINT          NOT NULL                COMMENT '资产ID',
    asset_no            VARCHAR(50)     DEFAULT ''              COMMENT '资产编号',
    asset_name          VARCHAR(100)    DEFAULT ''              COMMENT '资产名称',
    fault_desc          VARCHAR(500)    NOT NULL                COMMENT '故障描述',
    repair_type         VARCHAR(50)     DEFAULT ''              COMMENT '维修类型',
    repair_date         DATE            DEFAULT NULL            COMMENT '维修日期',
    complete_date       DATE            DEFAULT NULL            COMMENT '完成日期',
    repair_cost         DECIMAL(12,2)   DEFAULT 0.00            COMMENT '维修费用',
    repair_company      VARCHAR(100)    DEFAULT ''              COMMENT '维修单位',
    repair_result       VARCHAR(500)    DEFAULT NULL            COMMENT '维修结果',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待维修 1维修中 2已完成）',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (repair_id),
    UNIQUE KEY uk_repair_no (repair_no),
    KEY idx_asset_id (asset_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='资产维修表';

-- =============================================
-- 资产盘点表
-- =============================================
DROP TABLE IF EXISTS asset_inventory;
CREATE TABLE asset_inventory (
    inventory_id        BIGINT          NOT NULL AUTO_INCREMENT COMMENT '盘点ID',
    inventory_no        VARCHAR(50)     NOT NULL                COMMENT '盘点单号',
    inventory_name      VARCHAR(100)    NOT NULL                COMMENT '盘点名称',
    inventory_type      CHAR(1)         DEFAULT '1'             COMMENT '盘点类型（1全部盘点 2部分盘点）',
    start_date          DATE            NOT NULL                COMMENT '盘点开始日期',
    end_date            DATE            DEFAULT NULL            COMMENT '盘点结束日期',
    total_count         INT             DEFAULT 0               COMMENT '应盘数量',
    actual_count        INT             DEFAULT 0               COMMENT '实盘数量',
    profit_count        INT             DEFAULT 0               COMMENT '盘盈数量',
    loss_count          INT             DEFAULT 0               COMMENT '盘亏数量',
    operator_id         BIGINT          DEFAULT NULL            COMMENT '盘点人ID',
    operator_name       VARCHAR(50)     DEFAULT ''              COMMENT '盘点人姓名',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待盘点 1盘点中 2已完成）',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (inventory_id),
    UNIQUE KEY uk_inventory_no (inventory_no),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='资产盘点表';

-- =============================================
-- 盘点明细表
-- =============================================
DROP TABLE IF EXISTS asset_inventory_detail;
CREATE TABLE asset_inventory_detail (
    detail_id           BIGINT          NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    inventory_id        BIGINT          NOT NULL                COMMENT '盘点ID',
    asset_id            BIGINT          NOT NULL                COMMENT '资产ID',
    asset_no            VARCHAR(50)     DEFAULT ''              COMMENT '资产编号',
    asset_name          VARCHAR(100)    DEFAULT ''              COMMENT '资产名称',
    book_quantity       INT             DEFAULT 1               COMMENT '账面数量',
    actual_quantity     INT             DEFAULT 0               COMMENT '实际数量',
    diff_quantity       INT             DEFAULT 0               COMMENT '差异数量',
    diff_type           CHAR(1)         DEFAULT '0'             COMMENT '差异类型（0无差异 1盘盈 2盘亏）',
    diff_reason         VARCHAR(500)    DEFAULT NULL            COMMENT '差异原因',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0未盘点 1已盘点）',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    PRIMARY KEY (detail_id),
    KEY idx_inventory_id (inventory_id),
    KEY idx_asset_id (asset_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='盘点明细表';

-- =============================================
-- 资产报废表
-- =============================================
DROP TABLE IF EXISTS asset_scrap;
CREATE TABLE asset_scrap (
    scrap_id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '报废ID',
    scrap_no            VARCHAR(50)     NOT NULL                COMMENT '报废单号',
    asset_id            BIGINT          NOT NULL                COMMENT '资产ID',
    asset_no            VARCHAR(50)     DEFAULT ''              COMMENT '资产编号',
    asset_name          VARCHAR(100)    DEFAULT ''              COMMENT '资产名称',
    original_value      DECIMAL(12,2)   DEFAULT 0.00            COMMENT '原值',
    net_value           DECIMAL(12,2)   DEFAULT 0.00            COMMENT '净值',
    scrap_reason        VARCHAR(500)    NOT NULL                COMMENT '报废原因',
    scrap_date          DATE            DEFAULT NULL            COMMENT '报废日期',
    scrap_value         DECIMAL(12,2)   DEFAULT 0.00            COMMENT '残值',
    user_id             BIGINT          DEFAULT NULL            COMMENT '申请人ID',
    user_name           VARCHAR(50)     DEFAULT ''              COMMENT '申请人姓名',
    status              CHAR(1)         DEFAULT '0'             COMMENT '状态（0待审批 1已审批 2已处理）',
    approve_user_id     BIGINT          DEFAULT NULL            COMMENT '审批人ID',
    approve_time        DATETIME        DEFAULT NULL            COMMENT '审批时间',
    remark              VARCHAR(500)    DEFAULT NULL            COMMENT '备注',
    create_by           VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL            COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL            COMMENT '更新时间',
    deleted             INT             DEFAULT 0               COMMENT '删除标志',
    PRIMARY KEY (scrap_id),
    UNIQUE KEY uk_scrap_no (scrap_no),
    KEY idx_asset_id (asset_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='资产报废表';

-- =============================================
-- 初始化资产分类数据
-- =============================================
INSERT INTO asset_category VALUES(1, '办公设备', 0, '', 'office', 1, 'straight_line', 5, '0', NULL, 'admin', NOW(), '', NULL, 0);
INSERT INTO asset_category VALUES(2, '电子设备', 0, '', 'electronic', 2, 'straight_line', 3, '0', NULL, 'admin', NOW(), '', NULL, 0);
INSERT INTO asset_category VALUES(3, '交通运输设备', 0, '', 'transport', 3, 'straight_line', 10, '0', NULL, 'admin', NOW(), '', NULL, 0);
INSERT INTO asset_category VALUES(4, '办公家具', 0, '', 'furniture', 4, 'straight_line', 8, '0', NULL, 'admin', NOW(), '', NULL, 0);
INSERT INTO asset_category VALUES(5, '其他资产', 0, '', 'other', 5, 'straight_line', 5, '0', NULL, 'admin', NOW(), '', NULL, 0);

-- 子分类
INSERT INTO asset_category VALUES(6, '电脑', 2, '0,2', 'computer', 1, 'straight_line', 3, '0', NULL, 'admin', NOW(), '', NULL, 0);
INSERT INTO asset_category VALUES(7, '打印机', 2, '0,2', 'printer', 2, 'straight_line', 3, '0', NULL, 'admin', NOW(), '', NULL, 0);
INSERT INTO asset_category VALUES(8, '投影仪', 2, '0,2', 'projector', 3, 'straight_line', 3, '0', NULL, 'admin', NOW(), '', NULL, 0);