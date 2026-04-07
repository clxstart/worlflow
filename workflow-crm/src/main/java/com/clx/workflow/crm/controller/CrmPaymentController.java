package com.clx.workflow.crm.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.crm.domain.CrmPayment;
import com.clx.workflow.crm.service.ICrmPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 回款记录 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/crm/payment")
public class CrmPaymentController extends BaseController {

    @Autowired
    private ICrmPaymentService paymentService;

    /**
     * 查询回款记录列表
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:list')")
    @GetMapping("/list")
    public AjaxResult list(CrmPayment payment) {
        List<CrmPayment> list = paymentService.selectPaymentList(payment);
        return success(list);
    }

    /**
     * 查询合同的回款记录
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:list')")
    @GetMapping("/contract/{contractId}")
    public AjaxResult getByContract(@PathVariable Long contractId) {
        List<CrmPayment> list = paymentService.selectPaymentsByContractId(contractId);
        return success(list);
    }

    /**
     * 查询回款记录详情
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:query')")
    @GetMapping("/{paymentId}")
    public AjaxResult getInfo(@PathVariable Long paymentId) {
        return success(paymentService.getById(paymentId));
    }

    /**
     * 新增回款记录
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CrmPayment payment) {
        payment.setStatus("0"); // 待确认
        return toAjax(paymentService.save(payment));
    }

    /**
     * 修改回款记录
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody CrmPayment payment) {
        return toAjax(paymentService.updateById(payment));
    }

    /**
     * 确认回款
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:confirm')")
    @PutMapping("/confirm/{paymentId}")
    public AjaxResult confirm(@PathVariable Long paymentId) {
        return toAjax(paymentService.confirmPayment(paymentId, getUserId()));
    }

    /**
     * 作废回款
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:edit')")
    @PutMapping("/cancel/{paymentId}")
    public AjaxResult cancel(@PathVariable Long paymentId) {
        return toAjax(paymentService.cancelPayment(paymentId));
    }

    /**
     * 删除回款记录
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:remove')")
    @DeleteMapping("/{paymentIds}")
    public AjaxResult remove(@PathVariable Long[] paymentIds) {
        return toAjax(paymentService.removeByIds(java.util.Arrays.asList(paymentIds)));
    }

    /**
     * 统计回款金额
     */
    @PreAuthorize("@ss.hasPermi('crm:payment:query')")
    @GetMapping("/sum")
    public AjaxResult sumPaymentAmount(@RequestParam(required = false) Long customerId,
                                       @RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate) {
        BigDecimal amount = paymentService.sumPaymentAmount(customerId, startDate, endDate);
        return success(amount);
    }
}