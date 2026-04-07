package com.clx.workflow.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.finance.domain.FinanceBudget;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预算 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface FinanceBudgetMapper extends BaseMapper<FinanceBudget> {
}