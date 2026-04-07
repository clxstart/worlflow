package com.clx.workflow.finance.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.finance.domain.FinanceBudget;
import com.clx.workflow.finance.domain.FinanceBudgetDetail;
import com.clx.workflow.finance.service.IFinanceBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预算管理 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/finance/budget")
public class FinanceBudgetController extends BaseController {

    @Autowired
    private IFinanceBudgetService budgetService;

    /**
     * 查询预算列表
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:list')")
    @GetMapping("/list")
    public AjaxResult list(FinanceBudget budget) {
        List<FinanceBudget> list = budgetService.selectBudgetList(budget);
        return success(list);
    }

    /**
     * 查询预算详情（包含明细）
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:query')")
    @GetMapping("/{budgetId}")
    public AjaxResult getInfo(@PathVariable Long budgetId) {
        FinanceBudget budget = budgetService.getById(budgetId);
        List<FinanceBudgetDetail> details = budgetService.selectDetailList(budgetId);
        return success().put("budget", budget).put("details", details);
    }

    /**
     * 查询预算明细
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:query')")
    @GetMapping("/detail/{budgetId}")
    public AjaxResult getDetails(@PathVariable Long budgetId) {
        return success(budgetService.selectDetailList(budgetId));
    }

    /**
     * 新增预算
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinanceBudget budget) {
        return toAjax(budgetService.save(budget));
    }

    /**
     * 创建预算（包含明细）
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:add')")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody BudgetVO vo) {
        return toAjax(budgetService.createBudget(vo.getBudget(), vo.getDetails()));
    }

    /**
     * 修改预算
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FinanceBudget budget) {
        return toAjax(budgetService.updateById(budget));
    }

    /**
     * 生效预算
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:approve')")
    @PutMapping("/activate/{budgetId}")
    public AjaxResult activate(@PathVariable Long budgetId) {
        Long approveUserId = getUserId();
        return toAjax(budgetService.activateBudget(budgetId, approveUserId));
    }

    /**
     * 失效预算
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:edit')")
    @PutMapping("/deactivate/{budgetId}")
    public AjaxResult deactivate(@PathVariable Long budgetId) {
        FinanceBudget budget = budgetService.getById(budgetId);
        budget.setStatus("2"); // 已失效
        return toAjax(budgetService.updateById(budget));
    }

    /**
     * 检查预算余额
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:query')")
    @GetMapping("/check/{budgetId}")
    public AjaxResult checkBalance(@PathVariable Long budgetId, @RequestParam BigDecimal amount) {
        return success(budgetService.checkBudgetBalance(budgetId, amount));
    }

    /**
     * 使用预算
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:use')")
    @PutMapping("/use/{budgetId}")
    public AjaxResult use(@PathVariable Long budgetId,
                          @RequestParam BigDecimal amount,
                          @RequestParam(required = false) String expenseType) {
        return toAjax(budgetService.useBudget(budgetId, amount, expenseType));
    }

    /**
     * 删除预算
     */
    @PreAuthorize("@ss.hasPermi('finance:budget:remove')")
    @DeleteMapping("/{budgetIds}")
    public AjaxResult remove(@PathVariable Long[] budgetIds) {
        return toAjax(budgetService.removeByIds(java.util.Arrays.asList(budgetIds)));
    }

    /**
     * 预算VO（用于接收前端提交的数据）
     */
    public static class BudgetVO {
        private FinanceBudget budget;
        private List<FinanceBudgetDetail> details;

        public FinanceBudget getBudget() {
            return budget;
        }

        public void setBudget(FinanceBudget budget) {
            this.budget = budget;
        }

        public List<FinanceBudgetDetail> getDetails() {
            return details;
        }

        public void setDetails(List<FinanceBudgetDetail> details) {
            this.details = details;
        }
    }
}