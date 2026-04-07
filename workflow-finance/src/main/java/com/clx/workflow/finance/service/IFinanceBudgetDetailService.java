package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceBudgetDetail;

import java.util.List;

/**
 * 预算明细 Service 接口
 *
 * @author clx
 */
public interface IFinanceBudgetDetailService extends IService<FinanceBudgetDetail> {

    /**
     * 查询预算明细列表
     */
    List<FinanceBudgetDetail> selectDetailListByBudgetId(Long budgetId);
}