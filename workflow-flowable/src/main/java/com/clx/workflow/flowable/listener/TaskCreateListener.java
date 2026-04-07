package com.clx.workflow.flowable.listener;

import com.clx.workflow.flowable.domain.WfTodoTask;
import com.clx.workflow.flowable.service.IWfTodoTaskService;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 任务创建监听器
 *
 * @author clx
 */
@Component
public class TaskCreateListener implements TaskListener {

    @Autowired
    private IWfTodoTaskService todoTaskService;

    @Override
    public void notify(DelegateTask task) {
        // 创建待办任务记录
        WfTodoTask todoTask = new WfTodoTask();
        todoTask.setProcessInstanceId(task.getProcessInstanceId());
        todoTask.setTaskId(task.getId());
        todoTask.setTaskName(task.getName());
        todoTask.setTaskKey(task.getTaskDefinitionKey());
        todoTask.setStatus("pending");
        todoTask.setCreateTime(LocalDateTime.now());

        // 设置审批人
        if (task.getAssignee() != null) {
            todoTask.setAssigneeId(Long.parseLong(task.getAssignee()));
        }

        todoTaskService.save(todoTask);
    }
}