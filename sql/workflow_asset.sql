-- 资产分类表
DROP TABLE IF EXISTS asset_category;
CREATE TABLE asset_category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    ancestors VARCHAR(200) DEFAULT '' COMMENT '祖级列表',
    category_code VARCHAR(50) DEFAULT '' COMMENT '分类编码',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    depreciation_method VARCHAR(20) DEFAULT '' COMMENT '折旧方式',
    depreciation_years INT DEFAULT 0 COMMENT '折旧年限',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB COMMENT='资产分类表';

-- 资产信息表
DROP TABLE IF EXISTS asset_info;
CREATE TABLE asset_info (
    asset_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '资产ID',
    asset_no VARCHAR(50) NOT NULL COMMENT '资产编号',
    asset_name VARCHAR(100) NOT NULL COMMENT '资产名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    category_name VARCHAR(50) DEFAULT '' COMMENT '分类名称',
    brand VARCHAR(100) DEFAULT '' COMMENT '品牌',
    model VARCHAR(100) DEFAULT '' COMMENT '型号',
    spec VARCHAR(200) DEFAULT '' COMMENT '规格',
    unit VARCHAR(20) DEFAULT '' COMMENT '单位',
    quantity INT DEFAULT 1 COMMENT '数量',
    purchase_date DATE DEFAULT NULL COMMENT '采购日期',
    purchase_price DECIMAL(12,2) DEFAULT 0 COMMENT '采购价格',
    original_value DECIMAL(12,2) DEFAULT 0 COMMENT '原值',
    net_value DECIMAL(12,2) DEFAULT 0 COMMENT '净值',
    depreciation_value DECIMAL(12,2) DEFAULT 0 COMMENT '折旧值',
    warranty_date DATE DEFAULT NULL COMMENT '保修截止日期',
    supplier VARCHAR(100) DEFAULT '' COMMENT '供应商',
    location VARCHAR(200) DEFAULT '' COMMENT '存放位置',
    status CHAR(1) DEFAULT '1' COMMENT '状态（0待入库 1正常 2领用中 3维修中 4已报废）',
    user_id BIGINT DEFAULT NULL COMMENT '使用人ID',
    user_name VARCHAR(50) DEFAULT '' COMMENT '使用人姓名',
    dept_id BIGINT DEFAULT NULL COMMENT '部门ID',
    dept_name VARCHAR(100) DEFAULT '' COMMENT '部门名称',
    image VARCHAR(500) DEFAULT '' COMMENT '资产图片',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB COMMENT='资产信息表';

-- 资产领用表
DROP TABLE IF EXISTS asset_use;
CREATE TABLE asset_use (
    use_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '领用ID',
    use_no VARCHAR(50) NOT NULL COMMENT '领用单号',
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    asset_no VARCHAR(50) DEFAULT '' COMMENT '资产编号',
    asset_name VARCHAR(100) DEFAULT '' COMMENT '资产名称',
    user_id BIGINT NOT NULL COMMENT '领用人ID',
    user_name VARCHAR(50) DEFAULT '' COMMENT '领用人姓名',
    dept_id BIGINT DEFAULT NULL COMMENT '部门ID',
    dept_name VARCHAR(100) DEFAULT '' COMMENT '部门名称',
    use_date DATE NOT NULL COMMENT '领用日期',
    expect_return_date DATE DEFAULT NULL COMMENT '预计归还日期',
    actual_return_date DATE DEFAULT NULL COMMENT '实际归还日期',
    return_status CHAR(1) DEFAULT '0' COMMENT '归还状态（0未归还 1已归还）',
    return_remark VARCHAR(500) DEFAULT '' COMMENT '归还备注',
    purpose VARCHAR(500) DEFAULT '' COMMENT '领用目的',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0待审批 1已审批 2已拒绝）',
    approve_user_id BIGINT DEFAULT NULL COMMENT '审批人ID',
    approve_time DATETIME DEFAULT NULL COMMENT '审批时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB COMMENT='资产领用表';

-- 资产维修表
DROP TABLE IF EXISTS asset_repair;
CREATE TABLE asset_repair (
    repair_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '维修ID',
    repair_no VARCHAR(50) NOT NULL COMMENT '维修单号',
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    asset_no VARCHAR(50) DEFAULT '' COMMENT '资产编号',
    asset_name VARCHAR(100) DEFAULT '' COMMENT '资产名称',
    fault_desc VARCHAR(500) NOT NULL COMMENT '故障描述',
    repair_type VARCHAR(20) DEFAULT '' COMMENT '维修类型',
    repair_date DATE DEFAULT NULL COMMENT '维修日期',
    complete_date DATE DEFAULT NULL COMMENT '完成日期',
    repair_cost DECIMAL(12,2) DEFAULT 0 COMMENT '维修费用',
    repair_company VARCHAR(100) DEFAULT '' COMMENT '维修单位',
    repair_result VARCHAR(500) DEFAULT '' COMMENT '维修结果',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0待维修 1维修中 2已完成）',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB COMMENT='资产维修表';

-- 资产盘点表
DROP TABLE IF EXISTS asset_inventory;
CREATE TABLE asset_inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '盘点ID',
    inventory_no VARCHAR(50) NOT NULL COMMENT '盘点单号',
    inventory_name VARCHAR(100) NOT NULL COMMENT '盘点名称',
    inventory_type VARCHAR(20) DEFAULT '' COMMENT '盘点类型',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE DEFAULT NULL COMMENT '结束日期',
    total_count INT DEFAULT 0 COMMENT '应盘数量',
    actual_count INT DEFAULT 0 COMMENT '实盘数量',
    profit_count INT DEFAULT 0 COMMENT '盘盈数量',
    loss_count INT DEFAULT 0 COMMENT '盘亏数量',
    operator_id BIGINT DEFAULT NULL COMMENT '盘点人ID',
    operator_name VARCHAR(50) DEFAULT '' COMMENT '盘点人姓名',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0待盘点 1盘点中 2已完成）',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB COMMENT='资产盘点表';

-- 盘点明细表
DROP TABLE IF EXISTS asset_inventory_detail;
CREATE TABLE asset_inventory_detail (
    detail_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '明细ID',
    inventory_id BIGINT NOT NULL COMMENT '盘点ID',
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    asset_no VARCHAR(50) DEFAULT '' COMMENT '资产编号',
    asset_name VARCHAR(100) DEFAULT '' COMMENT '资产名称',
    book_quantity INT DEFAULT 1 COMMENT '账面数量',
    actual_quantity INT DEFAULT NULL COMMENT '实盘数量',
    diff_quantity INT DEFAULT 0 COMMENT '差异数量',
    diff_type VARCHAR(20) DEFAULT '' COMMENT '差异类型（profit盘盈 loss盘亏 normal正常）',
    diff_reason VARCHAR(500) DEFAULT '' COMMENT '差异原因',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0未盘点 1已盘点）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='盘点明细表';

-- 资产报废表
DROP TABLE IF EXISTS asset_scrap;
CREATE TABLE asset_scrap (
    scrap_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '报废ID',
    scrap_no VARCHAR(50) NOT NULL COMMENT '报废单号',
    asset_id BIGINT NOT NULL COMMENT '资产ID',
    asset_no VARCHAR(50) DEFAULT '' COMMENT '资产编号',
    asset_name VARCHAR(100) DEFAULT '' COMMENT '资产名称',
    original_value DECIMAL(12,2) DEFAULT 0 COMMENT '原值',
    net_value DECIMAL(12,2) DEFAULT 0 COMMENT '净值',
    scrap_reason VARCHAR(500) NOT NULL COMMENT '报废原因',
    scrap_date DATE DEFAULT NULL COMMENT '报废日期',
    scrap_value DECIMAL(12,2) DEFAULT 0 COMMENT '报废残值',
    user_id BIGINT DEFAULT NULL COMMENT '申请人ID',
    user_name VARCHAR(50) DEFAULT '' COMMENT '申请人姓名',
    status CHAR(1) DEFAULT '0' COMMENT '状态（0待审批 1已审批 2已拒绝）',
    approve_user_id BIGINT DEFAULT NULL COMMENT '审批人ID',
    approve_time DATETIME DEFAULT NULL COMMENT '审批时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注'
) ENGINE=InnoDB COMMENT='资产报废表';

-- 插入测试数据
INSERT INTO asset_category (category_name, parent_id, ancestors, category_code, sort, depreciation_method, depreciation_years, status) VALUES
('办公设备', 0, '0', 'BG', 1, 'straight_line', 5, '0'),
('电子设备', 0, '0', 'DZ', 2, 'straight_line', 3, '0'),
('交通工具', 0, '0', 'JT', 3, 'straight_line', 10, '0');

INSERT INTO asset_category (category_name, parent_id, ancestors, category_code, sort, depreciation_method, depreciation_years, status) VALUES
('电脑', 1, '0,1', 'BG-PC', 1, 'straight_line', 5, '0'),
('打印机', 1, '0,1', 'BG-PRINT', 2, 'straight_line', 5, '0'),
('办公桌椅', 1, '0,1', 'BG-DESK', 3, 'straight_line', 5, '0');

INSERT INTO asset_info (asset_no, asset_name, category_id, category_name, brand, model, unit, quantity, purchase_date, purchase_price, original_value, net_value, status, location) VALUES
('BG-2024-001', 'ThinkPad T490', 4, '电脑', 'Lenovo', 'T490', '台', 1, '2024-01-15', 8000.00, 8000.00, 7200.00, '1', '办公室A区'),
('BG-2024-002', 'HP LaserJet Pro', 5, '打印机', 'HP', 'M404dn', '台', 1, '2024-02-20', 3500.00, 3500.00, 3150.00, '1', '办公室B区'),
('BG-2024-003', '办公桌', 6, '办公桌椅', '得力', 'DL-880', '张', 10, '2024-03-10', 800.00, 8000.00, 7200.00, '1', '办公室C区');