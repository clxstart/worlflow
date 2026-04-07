package com.clx.workflow.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.flowable.domain.WfProcessInstance;

import java.util.List;
import java.util.Map;

/**
 * 流程实例 Service 接口
 *
 * @author clx
 */
public interface IWfProcessInstanceService extends IService<WfProcessInstance> {

    /**
     * 查询流程实例列表
     */
    List<WfProcessInstance> selectInstanceList(WfProcessInstance instance);

    /**
     * 启动流程实例
     */
    String startProcess(String processKey, String businessKey, String businessTable,
                        Long businessId, String title, Map<String, Object> variables);

    /**
     * 终止流程实例
     */
    boolean terminateProcess(String processInstanceId, String reason);

    /**
     * 根据流程实例ID查询
     */
    WfProcessInstance getByProcessInstanceId(String processInstanceId);
}