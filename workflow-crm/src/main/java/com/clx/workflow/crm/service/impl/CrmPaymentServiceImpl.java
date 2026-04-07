package com.clx.workflow.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.crm.domain.CrmPayment;
import com.clx.workflow.crm.mapper.CrmPaymentMapper;
import com.clx.workflow.crm.service.ICrmContractService;
import com.clx.workflow.crm.service.ICrmPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 回款记录 Service 实现类
 *
 * @author clx
 */
@Service
public class CrmPaymentServiceImpl extends ServiceImpl<CrmPaymentMapper, CrmPayment> implements ICrmPaymentService {

    @Autowired
    private ICrmContractService contractService;

    @Override
    public List<CrmPayment> selectPaymentList(CrmPayment payment) {
        LambdaQueryWrapper<CrmPayment> wrapper = new LambdaQueryWrapper<>();
        if (payment.getPaymentNo() != null && !payment.getPaymentNo().isEmpty()) {
            wrapper.like(CrmPayment::getPaymentNo, payment.getPaymentNo());
        }
        if (payment.getContractId() != null) {
            wrapper.eq(CrmPayment::getContractId, payment.getContractId());
        }
        if (payment.getCustomerId() != null) {
            wrapper.eq(CrmPayment::getCustomerId, payment.getCustomerId());
        }
        if (payment.getStatus() != null && !payment.getStatus().isEmpty()) {
            wrapper.eq(CrmPayment::getStatus, payment.getStatus());
        }
        wrapper.orderByDesc(CrmPayment::getPaymentDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CrmPayment> selectPaymentsByContractId(Long contractId) {
        LambdaQueryWrapper<CrmPayment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmPayment::getContractId, contractId);
        wrapper.orderByDesc(CrmPayment::getPaymentDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean confirmPayment(Long paymentId, Long approveUserId) {
        CrmPayment payment = getById(paymentId);
        if (payment == null) {
            return false;
        }

        LambdaUpdateWrapper<CrmPayment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmPayment::getPaymentId, paymentId);
        wrapper.set(CrmPayment::getStatus, "1"); // 已确认
        wrapper.set(CrmPayment::getApproveUserId, approveUserId);
        wrapper.set(CrmPayment::getApproveTime, LocalDateTime.now());
        boolean result = baseMapper.update(null, wrapper) > 0;

        if (result) {
            // 更新合同回款金额
            contractService.updateReceivedAmount(payment.getContractId(), payment.getPaymentAmount());
        }
        return result;
    }

    @Override
    public boolean cancelPayment(Long paymentId) {
        LambdaUpdateWrapper<CrmPayment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmPayment::getPaymentId, paymentId);
        wrapper.set(CrmPayment::getStatus, "2"); // 已作废
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public BigDecimal sumPaymentAmount(Long customerId, String startDate, String endDate) {
        LambdaQueryWrapper<CrmPayment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmPayment::getStatus, "1"); // 已确认
        if (customerId != null) {
            wrapper.eq(CrmPayment::getCustomerId, customerId);
        }
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge(CrmPayment::getPaymentDate, LocalDate.parse(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le(CrmPayment::getPaymentDate, LocalDate.parse(endDate));
        }
        List<CrmPayment> list = baseMapper.selectList(wrapper);
        return list.stream()
                .map(p -> p.getPaymentAmount() != null ? p.getPaymentAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}