package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceReimbursement;
import com.clx.workflow.finance.domain.FinanceReimbursementDetail;

import java.util.List;

/**
 * 报销申请 Service 接口
 *
 * @author clx
 */
public interface IFinanceReimbursementService extends IService<FinanceReimbursement> {

    /**
     * 查询报销申请列表
     */
    List<FinanceReimbursement> selectReimbursementList(FinanceReimbursement reimbursement);

    /**
     * 查询报销明细
     */
    List<FinanceReimbursementDetail> selectDetailList(Long reimbursementId);

    /**
     * 提交报销申请（包含明细）
     */
    boolean submitReimbursement(FinanceReimbursement reimbursement, List<FinanceReimbursementDetail> details);

    /**
     * 审批报销
     */
    boolean approveReimbursement(Long reimbursementId, String approveStatus);

    /**
     * 支付报销
     */
    boolean payReimbursement(Long reimbursementId);
}