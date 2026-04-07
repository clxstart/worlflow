package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceBudget;
import com.clx.workflow.finance.domain.FinanceBudgetDetail;
import com.clx.workflow.finance.mapper.FinanceBudgetMapper;
import com.clx.workflow.finance.service.IFinanceBudgetDetailService;
import com.clx.workflow.finance.service.IFinanceBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预算 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceBudgetServiceImpl extends ServiceImpl<FinanceBudgetMapper, FinanceBudget> implements IFinanceBudgetService {

    @Autowired
    private IFinanceBudgetDetailService detailService;

    @Override
    public List<FinanceBudget> selectBudgetList(FinanceBudget budget) {
        LambdaQueryWrapper<FinanceBudget> wrapper = new LambdaQueryWrapper<>();
        if (budget.getBudgetNo() != null && !budget.getBudgetNo().isEmpty()) {
            wrapper.like(FinanceBudget::getBudgetNo, budget.getBudgetNo());
        }
        if (budget.getBudgetName() != null && !budget.getBudgetName().isEmpty()) {
            wrapper.like(FinanceBudget::getBudgetName, budget.getBudgetName());
        }
        if (budget.getBudgetType() != null && !budget.getBudgetType().isEmpty()) {
            wrapper.eq(FinanceBudget::getBudgetType, budget.getBudgetType());
        }
        if (budget.getDeptId() != null) {
            wrapper.eq(FinanceBudget::getDeptId, budget.getDeptId());
        }
        if (budget.getProjectId() != null) {
            wrapper.eq(FinanceBudget::getProjectId, budget.getProjectId());
        }
        if (budget.getBudgetYear() != null) {
            wrapper.eq(FinanceBudget::getBudgetYear, budget.getBudgetYear());
        }
        if (budget.getBudgetMonth() != null) {
            wrapper.eq(FinanceBudget::getBudgetMonth, budget.getBudgetMonth());
        }
        if (budget.getStatus() != null && !budget.getStatus().isEmpty()) {
            wrapper.eq(FinanceBudget::getStatus, budget.getStatus());
        }
        wrapper.orderByDesc(FinanceBudget::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FinanceBudgetDetail> selectDetailList(Long budgetId) {
        return detailService.selectDetailListByBudgetId(budgetId);
    }

    @Override
    @Transactional
    public boolean createBudget(FinanceBudget budget, List<FinanceBudgetDetail> details) {
        budget.setStatus("0"); // 草稿
        budget.setUsedAmount(BigDecimal.ZERO);
        budget.setRemainingAmount(budget.getTotalAmount());
        budget.setFreezeAmount(BigDecimal.ZERO);
        boolean result = save(budget);
        if (result && details != null && !details.isEmpty()) {
            for (FinanceBudgetDetail detail : details) {
                detail.setBudgetId(budget.getBudgetId());
                detail.setUsedAmount(BigDecimal.ZERO);
                detail.setCreateTime(java.time.LocalDateTime.now());
            }
            detailService.saveBatch(details);
        }
        return result;
    }

    @Override
    public boolean activateBudget(Long budgetId, Long approveUserId) {
        LambdaUpdateWrapper<FinanceBudget> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FinanceBudget::getBudgetId, budgetId);
        wrapper.set(FinanceBudget::getStatus, "1"); // 已生效
        wrapper.set(FinanceBudget::getApproveUserId, approveUserId);
        wrapper.set(FinanceBudget::getApproveTime, java.time.LocalDateTime.now());
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public boolean checkBudgetBalance(Long budgetId, BigDecimal amount) {
        FinanceBudget budget = getById(budgetId);
        if (budget == null || !"1".equals(budget.getStatus())) {
            return false;
        }
        return budget.getRemainingAmount().compareTo(amount) >= 0;
    }

    @Override
    @Transactional
    public boolean useBudget(Long budgetId, BigDecimal amount, String expenseType) {
        FinanceBudget budget = getById(budgetId);
        if (budget == null || !checkBudgetBalance(budgetId, amount)) {
            return false;
        }
        // 更新预算主表
        LambdaUpdateWrapper<FinanceBudget> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FinanceBudget::getBudgetId, budgetId);
        wrapper.set(FinanceBudget::getUsedAmount, budget.getUsedAmount().add(amount));
        wrapper.set(FinanceBudget::getRemainingAmount, budget.getRemainingAmount().subtract(amount));
        boolean result = baseMapper.update(null, wrapper) > 0;

        // 更新预算明细
        if (result && expenseType != null && !expenseType.isEmpty()) {
            LambdaQueryWrapper<FinanceBudgetDetail> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(FinanceBudgetDetail::getBudgetId, budgetId);
            detailWrapper.eq(FinanceBudgetDetail::getExpenseType, expenseType);
            FinanceBudgetDetail detail = detailService.getBaseMapper().selectOne(detailWrapper);
            if (detail != null) {
                LambdaUpdateWrapper<FinanceBudgetDetail> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(FinanceBudgetDetail::getDetailId, detail.getDetailId());
                updateWrapper.set(FinanceBudgetDetail::getUsedAmount,
                    detail.getUsedAmount() != null ? detail.getUsedAmount().add(amount) : amount);
                updateWrapper.set(FinanceBudgetDetail::getUpdateTime, java.time.LocalDateTime.now());
                detailService.getBaseMapper().update(null, updateWrapper);
            }
        }
        return result;
    }
}