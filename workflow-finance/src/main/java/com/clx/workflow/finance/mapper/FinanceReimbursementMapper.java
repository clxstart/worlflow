package com.clx.workflow.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.finance.domain.FinanceReimbursement;
import org.apache.ibatis.annotations.Mapper;

/**
 * 报销申请 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface FinanceReimbursementMapper extends BaseMapper<FinanceReimbursement> {
}