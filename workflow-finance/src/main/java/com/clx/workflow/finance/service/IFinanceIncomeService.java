package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceIncome;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收入记录 Service 接口
 *
 * @author clx
 */
public interface IFinanceIncomeService extends IService<FinanceIncome> {

    /**
     * 查询收入记录列表
     */
    List<FinanceIncome> selectIncomeList(FinanceIncome income);

    /**
     * 统计收入总额
     */
    BigDecimal sumIncomeAmount(String startDate, String endDate);
}