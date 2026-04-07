package com.clx.workflow.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.flowable.domain.WfFlowRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程流转记录 Mapper
 *
 * @author clx
 */
@Mapper
public interface WfFlowRecordMapper extends BaseMapper<WfFlowRecord> {
}