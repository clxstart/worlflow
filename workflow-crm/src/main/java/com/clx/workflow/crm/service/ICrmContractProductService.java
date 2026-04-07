package com.clx.workflow.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.crm.domain.CrmContractProduct;

import java.util.List;

/**
 * 合同产品明细 Service 接口
 *
 * @author clx
 */
public interface ICrmContractProductService extends IService<CrmContractProduct> {

    /**
     * 查询合同产品明细
     */
    List<CrmContractProduct> selectProductsByContractId(Long contractId);
}