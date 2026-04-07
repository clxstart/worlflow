package com.clx.workflow.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.flowable.domain.WfProcessDefinition;
import com.clx.workflow.flowable.mapper.WfProcessDefinitionMapper;
import com.clx.workflow.flowable.service.IWfProcessDefinitionService;
import com.clx.workflow.common.exception.ServiceException;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程定义 Service 实现
 *
 * @author clx
 */
@Service
public class WfProcessDefinitionServiceImpl extends ServiceImpl<WfProcessDefinitionMapper, WfProcessDefinition>
        implements IWfProcessDefinitionService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public List<WfProcessDefinition> selectDefinitionList(WfProcessDefinition definition) {
        LambdaQueryWrapper<WfProcessDefinition> wrapper = new LambdaQueryWrapper<>();
        if (definition.getProcessName() != null && !definition.getProcessName().isEmpty()) {
            wrapper.like(WfProcessDefinition::getProcessName, definition.getProcessName());
        }
        if (definition.getCategoryId() != null) {
            wrapper.eq(WfProcessDefinition::getCategoryId, definition.getCategoryId());
        }
        if (definition.getStatus() != null && !definition.getStatus().isEmpty()) {
            wrapper.eq(WfProcessDefinition::getStatus, definition.getStatus());
        }
        wrapper.orderByAsc(WfProcessDefinition::getSort);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean deployProcess(WfProcessDefinition definition) {
        if (definition.getBpmnXml() == null || definition.getBpmnXml().isEmpty()) {
            throw new ServiceException("BPMN XML内容不能为空");
        }
        // 部署流程
        Deployment deployment = repositoryService.createDeployment()
                .name(definition.getProcessName())
                .addString(definition.getProcessKey() + ".bpmn20.xml", definition.getBpmnXml())
                .deploy();
        // 更新部署ID
        definition.setDeploymentId(deployment.getId());
        definition.setSuspensionState("active");
        return this.saveOrUpdate(definition);
    }

    @Override
    public boolean suspendProcess(String processKey) {
        repositoryService.suspendProcessDefinitionByKey(processKey);
        // 更新数据库状态
        WfProcessDefinition definition = this.lambdaQuery()
                .eq(WfProcessDefinition::getProcessKey, processKey)
                .one();
        if (definition != null) {
            definition.setSuspensionState("suspended");
            this.updateById(definition);
        }
        return true;
    }

    @Override
    public boolean activateProcess(String processKey) {
        repositoryService.activateProcessDefinitionByKey(processKey);
        // 更新数据库状态
        WfProcessDefinition definition = this.lambdaQuery()
                .eq(WfProcessDefinition::getProcessKey, processKey)
                .one();
        if (definition != null) {
            definition.setSuspensionState("active");
            this.updateById(definition);
        }
        return true;
    }
}