package com.clx.workflow.flowable.listener;

import com.clx.workflow.flowable.domain.WfFlowRecord;
import com.clx.workflow.flowable.domain.WfTodoTask;
import com.clx.workflow.flowable.service.IWfFlowRecordService;
import com.clx.workflow.flowable.service.IWfTodoTaskService;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 任务完成监听器
 *
 * @author clx
 */
@Component
public class TaskCompleteListener implements TaskListener {

    @Autowired
    private IWfFlowRecordService flowRecordService;

    @Autowired
    private IWfTodoTaskService todoTaskService;

    @Override
    public void notify(DelegateTask task) {
        // 保存审批记录
        WfFlowRecord record = new WfFlowRecord();
        record.setProcessInstanceId(task.getProcessInstanceId());
        record.setTaskId(task.getId());
        record.setTaskName(task.getName());
        record.setTaskKey(task.getTaskDefinitionKey());
        record.setEndTime(LocalDateTime.now());
        record.setCreateTime(LocalDateTime.now());

        // 设置审批人信息
        if (task.getAssignee() != null) {
            record.setAssigneeId(Long.parseLong(task.getAssignee()));
        }

        // 获取审批结果
        Object approveResult = task.getVariable("approveResult");
        if (approveResult != null) {
            record.setApproveResult(approveResult.toString());
        }

        Object comment = task.getVariable("comment");
        if (comment != null) {
            record.setComment(comment.toString());
        }

        flowRecordService.save(record);

        // 删除待办任务
        todoTaskService.deleteByTaskId(task.getId());
    }
}