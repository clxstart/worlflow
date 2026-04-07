package com.clx.workflow.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.flowable.domain.WfProcessInstance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程实例 Mapper
 *
 * @author clx
 */
@Mapper
public interface WfProcessInstanceMapper extends BaseMapper<WfProcessInstance> {
}