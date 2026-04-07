package com.clx.workflow.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.flowable.domain.WfProcessDefinition;

import java.util.List;

/**
 * 流程定义 Service 接口
 *
 * @author clx
 */
public interface IWfProcessDefinitionService extends IService<WfProcessDefinition> {

    /**
     * 查询流程定义列表
     */
    List<WfProcessDefinition> selectDefinitionList(WfProcessDefinition definition);

    /**
     * 部署流程定义
     */
    boolean deployProcess(WfProcessDefinition definition);

    /**
     * 挂起流程定义
     */
    boolean suspendProcess(String processKey);

    /**
     * 激活流程定义
     */
    boolean activateProcess(String processKey);
}