package com.clx.workflow.finance.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.finance.domain.FinanceIncome;
import com.clx.workflow.finance.service.IFinanceIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收入记录 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/finance/income")
public class FinanceIncomeController extends BaseController {

    @Autowired
    private IFinanceIncomeService incomeService;

    /**
     * 查询收入记录列表
     */
    @PreAuthorize("@ss.hasPermi('finance:income:list')")
    @GetMapping("/list")
    public AjaxResult list(FinanceIncome income) {
        List<FinanceIncome> list = incomeService.selectIncomeList(income);
        return success(list);
    }

    /**
     * 查询收入记录详情
     */
    @PreAuthorize("@ss.hasPermi('finance:income:query')")
    @GetMapping("/{incomeId}")
    public AjaxResult getInfo(@PathVariable Long incomeId) {
        return success(incomeService.getById(incomeId));
    }

    /**
     * 统计收入总额
     */
    @PreAuthorize("@ss.hasPermi('finance:income:query')")
    @GetMapping("/sum")
    public AjaxResult sumIncomeAmount(@RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate) {
        BigDecimal amount = incomeService.sumIncomeAmount(startDate, endDate);
        return success(amount);
    }

    /**
     * 新增收入记录
     */
    @PreAuthorize("@ss.hasPermi('finance:income:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinanceIncome income) {
        return toAjax(incomeService.save(income));
    }

    /**
     * 修改收入记录
     */
    @PreAuthorize("@ss.hasPermi('finance:income:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FinanceIncome income) {
        return toAjax(incomeService.updateById(income));
    }

    /**
     * 确认收入
     */
    @PreAuthorize("@ss.hasPermi('finance:income:edit')")
    @PutMapping("/confirm/{incomeId}")
    public AjaxResult confirm(@PathVariable Long incomeId) {
        FinanceIncome income = incomeService.getById(incomeId);
        income.setStatus("1"); // 已确认
        return toAjax(incomeService.updateById(income));
    }

    /**
     * 取消收入
     */
    @PreAuthorize("@ss.hasPermi('finance:income:edit')")
    @PutMapping("/cancel/{incomeId}")
    public AjaxResult cancel(@PathVariable Long incomeId) {
        FinanceIncome income = incomeService.getById(incomeId);
        income.setStatus("2"); // 已取消
        return toAjax(incomeService.updateById(income));
    }

    /**
     * 删除收入记录
     */
    @PreAuthorize("@ss.hasPermi('finance:income:remove')")
    @DeleteMapping("/{incomeIds}")
    public AjaxResult remove(@PathVariable Long[] incomeIds) {
        return toAjax(incomeService.removeByIds(java.util.Arrays.asList(incomeIds)));
    }
}