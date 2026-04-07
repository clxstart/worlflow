package com.clx.workflow.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.flowable.domain.WfFlowRecord;
import com.clx.workflow.flowable.mapper.WfFlowRecordMapper;
import com.clx.workflow.flowable.service.IWfFlowRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程流转记录 Service 实现
 *
 * @author clx
 */
@Service
public class WfFlowRecordServiceImpl extends ServiceImpl<WfFlowRecordMapper, WfFlowRecord>
        implements IWfFlowRecordService {

    @Override
    public List<WfFlowRecord> selectRecordList(String processInstanceId) {
        LambdaQueryWrapper<WfFlowRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfFlowRecord::getProcessInstanceId, processInstanceId)
                .orderByAsc(WfFlowRecord::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean saveRecord(WfFlowRecord record) {
        return this.save(record);
    }
}