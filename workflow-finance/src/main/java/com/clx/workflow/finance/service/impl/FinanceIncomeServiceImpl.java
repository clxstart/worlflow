package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceIncome;
import com.clx.workflow.finance.mapper.FinanceIncomeMapper;
import com.clx.workflow.finance.service.IFinanceIncomeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 收入记录 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceIncomeServiceImpl extends ServiceImpl<FinanceIncomeMapper, FinanceIncome> implements IFinanceIncomeService {

    @Override
    public List<FinanceIncome> selectIncomeList(FinanceIncome income) {
        LambdaQueryWrapper<FinanceIncome> wrapper = new LambdaQueryWrapper<>();
        if (income.getIncomeNo() != null && !income.getIncomeNo().isEmpty()) {
            wrapper.like(FinanceIncome::getIncomeNo, income.getIncomeNo());
        }
        if (income.getIncomeType() != null && !income.getIncomeType().isEmpty()) {
            wrapper.eq(FinanceIncome::getIncomeType, income.getIncomeType());
        }
        if (income.getIncomeSource() != null && !income.getIncomeSource().isEmpty()) {
            wrapper.like(FinanceIncome::getIncomeSource, income.getIncomeSource());
        }
        if (income.getIncomeDate() != null) {
            wrapper.eq(FinanceIncome::getIncomeDate, income.getIncomeDate());
        }
        if (income.getStatus() != null && !income.getStatus().isEmpty()) {
            wrapper.eq(FinanceIncome::getStatus, income.getStatus());
        }
        if (income.getHandlerId() != null) {
            wrapper.eq(FinanceIncome::getHandlerId, income.getHandlerId());
        }
        wrapper.orderByDesc(FinanceIncome::getIncomeDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BigDecimal sumIncomeAmount(String startDate, String endDate) {
        LambdaQueryWrapper<FinanceIncome> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinanceIncome::getStatus, "1"); // 已确认
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge(FinanceIncome::getIncomeDate, LocalDate.parse(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le(FinanceIncome::getIncomeDate, LocalDate.parse(endDate));
        }
        List<FinanceIncome> list = baseMapper.selectList(wrapper);
        return list.stream().map(FinanceIncome::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}