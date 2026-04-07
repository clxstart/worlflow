package com.clx.workflow.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.crm.domain.CrmCustomer;

import java.util.List;
import java.util.Map;

/**
 * 客户信息 Service 接口
 *
 * @author clx
 */
public interface ICrmCustomerService extends IService<CrmCustomer> {

    /**
     * 查询客户列表
     */
    List<CrmCustomer> selectCustomerList(CrmCustomer customer);

    /**
     * 查询我的客户
     */
    List<CrmCustomer> selectMyCustomers(Long ownerId);

    /**
     * 转移客户
     */
    boolean transferCustomer(Long customerId, Long newOwnerId, String newOwnerName);

    /**
     * 客户统计
     */
    Map<String, Object> customerStatistics(Long ownerId);
}