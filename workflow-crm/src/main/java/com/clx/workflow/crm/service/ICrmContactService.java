package com.clx.workflow.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.crm.domain.CrmContact;

import java.util.List;

/**
 * 联系人 Service 接口
 *
 * @author clx
 */
public interface ICrmContactService extends IService<CrmContact> {

    /**
     * 查询联系人列表
     */
    List<CrmContact> selectContactList(CrmContact contact);

    /**
     * 查询客户的联系人列表
     */
    List<CrmContact> selectContactsByCustomerId(Long customerId);

    /**
     * 设置主要联系人
     */
    boolean setPrimaryContact(Long contactId);
}