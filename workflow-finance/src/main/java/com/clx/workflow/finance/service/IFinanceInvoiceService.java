package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceInvoice;

import java.util.List;

/**
 * 发票管理 Service 接口
 *
 * @author clx
 */
public interface IFinanceInvoiceService extends IService<FinanceInvoice> {

    /**
     * 查询发票列表
     */
    List<FinanceInvoice> selectInvoiceList(FinanceInvoice invoice);

    /**
     * 查验发票
     */
    boolean verifyInvoice(Long invoiceId);
}