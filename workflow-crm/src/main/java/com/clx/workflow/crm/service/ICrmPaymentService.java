package com.clx.workflow.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.crm.domain.CrmPayment;

import java.math.BigDecimal;
import java.util.List;

/**
 * 回款记录 Service 接口
 *
 * @author clx
 */
public interface ICrmPaymentService extends IService<CrmPayment> {

    /**
     * 查询回款记录列表
     */
    List<CrmPayment> selectPaymentList(CrmPayment payment);

    /**
     * 查询合同的回款记录
     */
    List<CrmPayment> selectPaymentsByContractId(Long contractId);

    /**
     * 确认回款
     */
    boolean confirmPayment(Long paymentId, Long approveUserId);

    /**
     * 作废回款
     */
    boolean cancelPayment(Long paymentId);

    /**
     * 统计回款金额
     */
    BigDecimal sumPaymentAmount(Long customerId, String startDate, String endDate);
}