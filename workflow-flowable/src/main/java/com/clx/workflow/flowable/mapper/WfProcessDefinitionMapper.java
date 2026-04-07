package com.clx.workflow.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.flowable.domain.WfProcessDefinition;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程定义 Mapper
 *
 * @author clx
 */
@Mapper
public interface WfProcessDefinitionMapper extends BaseMapper<WfProcessDefinition> {
}