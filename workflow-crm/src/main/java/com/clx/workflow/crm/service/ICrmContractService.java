package com.clx.workflow.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.crm.domain.CrmContract;
import com.clx.workflow.crm.domain.CrmContractProduct;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合同 Service 接口
 *
 * @author clx
 */
public interface ICrmContractService extends IService<CrmContract> {

    /**
     * 查询合同列表
     */
    List<CrmContract> selectContractList(CrmContract contract);

    /**
     * 查询合同产品明细
     */
    List<CrmContractProduct> selectProductsByContractId(Long contractId);

    /**
     * 创建合同（含产品明细）
     */
    boolean createContract(CrmContract contract, List<CrmContractProduct> products);

    /**
     * 提交审批
     */
    boolean submitApproval(Long contractId);

    /**
     * 审批合同
     */
    boolean approveContract(Long contractId, String status, Long approveUserId);

    /**
     * 作废合同
     */
    boolean cancelContract(Long contractId);

    /**
     * 更新回款金额
     */
    boolean updateReceivedAmount(Long contractId, BigDecimal amount);

    /**
     * 合同统计
     */
    java.util.Map<String, Object> contractStatistics(Long ownerId);
}