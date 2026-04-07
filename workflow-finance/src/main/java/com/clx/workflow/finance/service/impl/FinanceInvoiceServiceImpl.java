package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceInvoice;
import com.clx.workflow.finance.mapper.FinanceInvoiceMapper;
import com.clx.workflow.finance.service.IFinanceInvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 发票管理 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceInvoiceServiceImpl extends ServiceImpl<FinanceInvoiceMapper, FinanceInvoice> implements IFinanceInvoiceService {

    @Override
    public List<FinanceInvoice> selectInvoiceList(FinanceInvoice invoice) {
        LambdaQueryWrapper<FinanceInvoice> wrapper = new LambdaQueryWrapper<>();
        if (invoice.getInvoiceNo() != null && !invoice.getInvoiceNo().isEmpty()) {
            wrapper.like(FinanceInvoice::getInvoiceNo, invoice.getInvoiceNo());
        }
        if (invoice.getInvoiceType() != null && !invoice.getInvoiceType().isEmpty()) {
            wrapper.eq(FinanceInvoice::getInvoiceType, invoice.getInvoiceType());
        }
        if (invoice.getInvoiceCategory() != null && !invoice.getInvoiceCategory().isEmpty()) {
            wrapper.eq(FinanceInvoice::getInvoiceCategory, invoice.getInvoiceCategory());
        }
        if (invoice.getCompanyName() != null && !invoice.getCompanyName().isEmpty()) {
            wrapper.like(FinanceInvoice::getCompanyName, invoice.getCompanyName());
        }
        if (invoice.getInvoiceDate() != null) {
            wrapper.eq(FinanceInvoice::getInvoiceDate, invoice.getInvoiceDate());
        }
        if (invoice.getVerifyStatus() != null && !invoice.getVerifyStatus().isEmpty()) {
            wrapper.eq(FinanceInvoice::getVerifyStatus, invoice.getVerifyStatus());
        }
        if (invoice.getStatus() != null && !invoice.getStatus().isEmpty()) {
            wrapper.eq(FinanceInvoice::getStatus, invoice.getStatus());
        }
        wrapper.orderByDesc(FinanceInvoice::getInvoiceDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean verifyInvoice(Long invoiceId) {
        LambdaUpdateWrapper<FinanceInvoice> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FinanceInvoice::getInvoiceId, invoiceId);
        wrapper.set(FinanceInvoice::getVerifyStatus, "1"); // 已查验
        wrapper.set(FinanceInvoice::getVerifyTime, java.time.LocalDateTime.now());
        return baseMapper.update(null, wrapper) > 0;
    }
}