package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceBudgetDetail;
import com.clx.workflow.finance.mapper.FinanceBudgetDetailMapper;
import com.clx.workflow.finance.service.IFinanceBudgetDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预算明细 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceBudgetDetailServiceImpl extends ServiceImpl<FinanceBudgetDetailMapper, FinanceBudgetDetail> implements IFinanceBudgetDetailService {

    @Override
    public List<FinanceBudgetDetail> selectDetailListByBudgetId(Long budgetId) {
        LambdaQueryWrapper<FinanceBudgetDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinanceBudgetDetail::getBudgetId, budgetId);
        return baseMapper.selectList(wrapper);
    }
}