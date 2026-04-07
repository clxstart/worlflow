package com.clx.workflow.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.crm.domain.CrmContact;
import org.apache.ibatis.annotations.Mapper;

/**
 * 联系人 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface CrmContactMapper extends BaseMapper<CrmContact> {
}