package com.clx.workflow.finance.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.finance.domain.FinanceInvoice;
import com.clx.workflow.finance.service.IFinanceInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发票管理 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/finance/invoice")
public class FinanceInvoiceController extends BaseController {

    @Autowired
    private IFinanceInvoiceService invoiceService;

    /**
     * 查询发票列表
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:list')")
    @GetMapping("/list")
    public AjaxResult list(FinanceInvoice invoice) {
        List<FinanceInvoice> list = invoiceService.selectInvoiceList(invoice);
        return success(list);
    }

    /**
     * 查询发票详情
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:query')")
    @GetMapping("/{invoiceId}")
    public AjaxResult getInfo(@PathVariable Long invoiceId) {
        return success(invoiceService.getById(invoiceId));
    }

    /**
     * 新增发票
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinanceInvoice invoice) {
        invoice.setVerifyStatus("0"); // 未查验
        invoice.setStatus("0"); // 有效
        return toAjax(invoiceService.save(invoice));
    }

    /**
     * 修改发票
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FinanceInvoice invoice) {
        return toAjax(invoiceService.updateById(invoice));
    }

    /**
     * 查验发票
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:verify')")
    @PutMapping("/verify/{invoiceId}")
    public AjaxResult verify(@PathVariable Long invoiceId) {
        return toAjax(invoiceService.verifyInvoice(invoiceId));
    }

    /**
     * 作废发票
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:edit')")
    @PutMapping("/cancel/{invoiceId}")
    public AjaxResult cancel(@PathVariable Long invoiceId) {
        FinanceInvoice invoice = invoiceService.getById(invoiceId);
        invoice.setStatus("1"); // 作废
        return toAjax(invoiceService.updateById(invoice));
    }

    /**
     * 红冲发票
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:edit')")
    @PutMapping("/red/{invoiceId}")
    public AjaxResult red(@PathVariable Long invoiceId) {
        FinanceInvoice invoice = invoiceService.getById(invoiceId);
        invoice.setStatus("2"); // 红冲
        return toAjax(invoiceService.updateById(invoice));
    }

    /**
     * 删除发票
     */
    @PreAuthorize("@ss.hasPermi('finance:invoice:remove')")
    @DeleteMapping("/{invoiceIds}")
    public AjaxResult remove(@PathVariable Long[] invoiceIds) {
        return toAjax(invoiceService.removeByIds(java.util.Arrays.asList(invoiceIds)));
    }
}