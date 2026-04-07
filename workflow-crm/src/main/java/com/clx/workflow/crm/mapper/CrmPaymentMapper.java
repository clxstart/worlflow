package com.clx.workflow.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.crm.domain.CrmPayment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 回款记录 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface CrmPaymentMapper extends BaseMapper<CrmPayment> {
}