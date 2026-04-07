package com.clx.workflow.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.finance.domain.FinanceExpense;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支出记录 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface FinanceExpenseMapper extends BaseMapper<FinanceExpense> {
}