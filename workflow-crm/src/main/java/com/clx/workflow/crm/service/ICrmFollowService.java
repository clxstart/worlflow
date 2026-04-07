package com.clx.workflow.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.crm.domain.CrmFollow;

import java.util.List;

/**
 * 跟进记录 Service 接口
 *
 * @author clx
 */
public interface ICrmFollowService extends IService<CrmFollow> {

    /**
     * 查询跟进记录列表
     */
    List<CrmFollow> selectFollowList(CrmFollow follow);

    /**
     * 查询客户的跟进记录
     */
    List<CrmFollow> selectFollowsByCustomerId(Long customerId);
}