package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceBudget;
import com.clx.workflow.finance.domain.FinanceBudgetDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预算 Service 接口
 *
 * @author clx
 */
public interface IFinanceBudgetService extends IService<FinanceBudget> {

    /**
     * 查询预算列表
     */
    List<FinanceBudget> selectBudgetList(FinanceBudget budget);

    /**
     * 查询预算明细
     */
    List<FinanceBudgetDetail> selectDetailList(Long budgetId);

    /**
     * 创建预算（包含明细）
     */
    boolean createBudget(FinanceBudget budget, List<FinanceBudgetDetail> details);

    /**
     * 生效预算
     */
    boolean activateBudget(Long budgetId, Long approveUserId);

    /**
     * 检查预算余额
     */
    boolean checkBudgetBalance(Long budgetId, BigDecimal amount);

    /**
     * 使用预算
     */
    boolean useBudget(Long budgetId, BigDecimal amount, String expenseType);
}