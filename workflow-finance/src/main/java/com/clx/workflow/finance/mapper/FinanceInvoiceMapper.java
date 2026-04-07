package com.clx.workflow.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.finance.domain.FinanceInvoice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发票管理 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface FinanceInvoiceMapper extends BaseMapper<FinanceInvoice> {
}