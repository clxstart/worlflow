package com.clx.workflow.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.finance.domain.FinanceReimbursementDetail;

import java.util.List;

/**
 * 报销明细 Service 接口
 *
 * @author clx
 */
public interface IFinanceReimbursementDetailService extends IService<FinanceReimbursementDetail> {

    /**
     * 查询报销明细列表
     */
    List<FinanceReimbursementDetail> selectDetailListByReimbursementId(Long reimbursementId);
}