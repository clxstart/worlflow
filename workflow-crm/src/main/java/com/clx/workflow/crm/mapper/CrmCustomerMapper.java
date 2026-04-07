package com.clx.workflow.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.crm.domain.CrmCustomer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户信息 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface CrmCustomerMapper extends BaseMapper<CrmCustomer> {
}