package com.clx.workflow.finance.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.finance.domain.FinanceExpense;
import com.clx.workflow.finance.service.IFinanceExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支出记录 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/finance/expense")
public class FinanceExpenseController extends BaseController {

    @Autowired
    private IFinanceExpenseService expenseService;

    /**
     * 查询支出记录列表
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:list')")
    @GetMapping("/list")
    public AjaxResult list(FinanceExpense expense) {
        List<FinanceExpense> list = expenseService.selectExpenseList(expense);
        return success(list);
    }

    /**
     * 查询支出记录详情
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:query')")
    @GetMapping("/{expenseId}")
    public AjaxResult getInfo(@PathVariable Long expenseId) {
        return success(expenseService.getById(expenseId));
    }

    /**
     * 统计支出总额
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:query')")
    @GetMapping("/sum")
    public AjaxResult sumExpenseAmount(@RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate) {
        BigDecimal amount = expenseService.sumExpenseAmount(startDate, endDate);
        return success(amount);
    }

    /**
     * 新增支出记录
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinanceExpense expense) {
        expense.setStatus("0"); // 待审批
        return toAjax(expenseService.save(expense));
    }

    /**
     * 修改支出记录
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FinanceExpense expense) {
        return toAjax(expenseService.updateById(expense));
    }

    /**
     * 审批支出
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:approve')")
    @PutMapping("/approve/{expenseId}")
    public AjaxResult approve(@PathVariable Long expenseId, @RequestParam String status) {
        Long approveUserId = getUserId();
        return toAjax(expenseService.approveExpense(expenseId, approveUserId, status));
    }

    /**
     * 删除支出记录
     */
    @PreAuthorize("@ss.hasPermi('finance:expense:remove')")
    @DeleteMapping("/{expenseIds}")
    public AjaxResult remove(@PathVariable Long[] expenseIds) {
        return toAjax(expenseService.removeByIds(java.util.Arrays.asList(expenseIds)));
    }
}