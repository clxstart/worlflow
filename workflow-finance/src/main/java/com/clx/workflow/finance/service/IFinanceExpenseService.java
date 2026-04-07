package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceExpense;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支出记录 Service 接口
 *
 * @author clx
 */
public interface IFinanceExpenseService extends IService<FinanceExpense> {

    /**
     * 查询支出记录列表
     */
    List<FinanceExpense> selectExpenseList(FinanceExpense expense);

    /**
     * 统计支出总额
     */
    BigDecimal sumExpenseAmount(String startDate, String endDate);

    /**
     * 审批支出
     */
    boolean approveExpense(Long expenseId, Long approveUserId, String status);
}