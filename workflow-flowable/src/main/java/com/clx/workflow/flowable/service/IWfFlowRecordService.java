package com.clx.workflow.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.flowable.domain.WfFlowRecord;

import java.util.List;

/**
 * 流程流转记录 Service 接口
 *
 * @author clx
 */
public interface IWfFlowRecordService extends IService<WfFlowRecord> {

    /**
     * 查询流程流转记录
     */
    List<WfFlowRecord> selectRecordList(String processInstanceId);

    /**
     * 保存审批记录
     */
    boolean saveRecord(WfFlowRecord record);
}