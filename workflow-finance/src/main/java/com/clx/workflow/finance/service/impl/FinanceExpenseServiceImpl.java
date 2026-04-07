package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceExpense;
import com.clx.workflow.finance.mapper.FinanceExpenseMapper;
import com.clx.workflow.finance.service.IFinanceExpenseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 支出记录 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceExpenseServiceImpl extends ServiceImpl<FinanceExpenseMapper, FinanceExpense> implements IFinanceExpenseService {

    @Override
    public List<FinanceExpense> selectExpenseList(FinanceExpense expense) {
        LambdaQueryWrapper<FinanceExpense> wrapper = new LambdaQueryWrapper<>();
        if (expense.getExpenseNo() != null && !expense.getExpenseNo().isEmpty()) {
            wrapper.like(FinanceExpense::getExpenseNo, expense.getExpenseNo());
        }
        if (expense.getExpenseType() != null && !expense.getExpenseType().isEmpty()) {
            wrapper.eq(FinanceExpense::getExpenseType, expense.getExpenseType());
        }
        if (expense.getExpenseCategory() != null && !expense.getExpenseCategory().isEmpty()) {
            wrapper.eq(FinanceExpense::getExpenseCategory, expense.getExpenseCategory());
        }
        if (expense.getExpenseDate() != null) {
            wrapper.eq(FinanceExpense::getExpenseDate, expense.getExpenseDate());
        }
        if (expense.getStatus() != null && !expense.getStatus().isEmpty()) {
            wrapper.eq(FinanceExpense::getStatus, expense.getStatus());
        }
        if (expense.getHandlerId() != null) {
            wrapper.eq(FinanceExpense::getHandlerId, expense.getHandlerId());
        }
        wrapper.orderByDesc(FinanceExpense::getExpenseDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BigDecimal sumExpenseAmount(String startDate, String endDate) {
        LambdaQueryWrapper<FinanceExpense> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinanceExpense::getStatus, "2"); // 已支付
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge(FinanceExpense::getExpenseDate, LocalDate.parse(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le(FinanceExpense::getExpenseDate, LocalDate.parse(endDate));
        }
        List<FinanceExpense> list = baseMapper.selectList(wrapper);
        return list.stream().map(FinanceExpense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean approveExpense(Long expenseId, Long approveUserId, String status) {
        LambdaUpdateWrapper<FinanceExpense> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FinanceExpense::getExpenseId, expenseId);
        wrapper.set(FinanceExpense::getApproveUserId, approveUserId);
        wrapper.set(FinanceExpense::getApproveTime, java.time.LocalDateTime.now());
        wrapper.set(FinanceExpense::getStatus, status);
        return baseMapper.update(null, wrapper) > 0;
    }
}