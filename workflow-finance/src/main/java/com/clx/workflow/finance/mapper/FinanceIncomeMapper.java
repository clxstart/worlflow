package com.clx.workflow.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.finance.domain.FinanceIncome;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收入记录 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface FinanceIncomeMapper extends BaseMapper<FinanceIncome> {
}