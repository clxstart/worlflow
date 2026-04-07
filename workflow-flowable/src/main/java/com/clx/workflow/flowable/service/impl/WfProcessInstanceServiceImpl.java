package com.clx.workflow.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.flowable.domain.WfFlowRecord;
import com.clx.workflow.flowable.domain.WfProcessInstance;
import com.clx.workflow.flowable.domain.WfTodoTask;
import com.clx.workflow.flowable.mapper.WfProcessInstanceMapper;
import com.clx.workflow.flowable.service.IWfFlowRecordService;
import com.clx.workflow.flowable.service.IWfProcessInstanceService;
import com.clx.workflow.flowable.service.IWfTodoTaskService;
import com.clx.workflow.common.utils.SecurityUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流程实例 Service 实现
 *
 * @author clx
 */
@Service
public class WfProcessInstanceServiceImpl extends ServiceImpl<WfProcessInstanceMapper, WfProcessInstance>
        implements IWfProcessInstanceService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IWfTodoTaskService todoTaskService;

    @Autowired
    private IWfFlowRecordService flowRecordService;

    @Override
    public List<WfProcessInstance> selectInstanceList(WfProcessInstance instance) {
        LambdaQueryWrapper<WfProcessInstance> wrapper = new LambdaQueryWrapper<>();
        if (instance.getTitle() != null && !instance.getTitle().isEmpty()) {
            wrapper.like(WfProcessInstance::getTitle, instance.getTitle());
        }
        if (instance.getInitiatorId() != null) {
            wrapper.eq(WfProcessInstance::getInitiatorId, instance.getInitiatorId());
        }
        if (instance.getStatus() != null && !instance.getStatus().isEmpty()) {
            wrapper.eq(WfProcessInstance::getStatus, instance.getStatus());
        }
        wrapper.orderByDesc(WfProcessInstance::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startProcess(String processKey, String businessKey, String businessTable,
                               Long businessId, String title, Map<String, Object> variables) {
        // 启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                processKey, businessKey, variables);

        // 创建流程实例扩展记录
        WfProcessInstance instance = new WfProcessInstance();
        instance.setProcessInstanceId(processInstance.getId());
        instance.setBusinessKey(businessKey);
        instance.setBusinessTable(businessTable);
        instance.setBusinessId(businessId);
        instance.setTitle(title);
        instance.setInitiatorId(SecurityUtils.getUserId());
        instance.setInitiatorName(SecurityUtils.getUsername());
        instance.setStatus("running");
        this.save(instance);

        // 创建待办任务
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();
        for (Task task : tasks) {
            WfTodoTask todoTask = new WfTodoTask();
            todoTask.setProcessInstanceId(processInstance.getId());
            todoTask.setTaskId(task.getId());
            todoTask.setTaskName(task.getName());
            todoTask.setTaskKey(task.getTaskDefinitionKey());
            todoTask.setTitle(title);
            todoTask.setStatus("pending");
            todoTask.setCreateTime(LocalDateTime.now());
            if (task.getAssignee() != null) {
                todoTask.setAssigneeId(Long.parseLong(task.getAssignee()));
            }
            todoTaskService.save(todoTask);
        }

        return processInstance.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean terminateProcess(String processInstanceId, String reason) {
        // 删除流程实例
        runtimeService.deleteProcessInstance(processInstanceId, reason);

        // 更新流程实例状态
        WfProcessInstance instance = getByProcessInstanceId(processInstanceId);
        if (instance != null) {
            instance.setStatus("terminated");
            instance.setResult("rejected");
            instance.setEndTime(LocalDateTime.now());
            this.updateById(instance);

            // 删除待办任务
            todoTaskService.lambdaUpdate()
                    .eq(WfTodoTask::getProcessInstanceId, processInstanceId)
                    .remove();
        }
        return true;
    }

    @Override
    public WfProcessInstance getByProcessInstanceId(String processInstanceId) {
        return this.lambdaQuery()
                .eq(WfProcessInstance::getProcessInstanceId, processInstanceId)
                .one();
    }
}