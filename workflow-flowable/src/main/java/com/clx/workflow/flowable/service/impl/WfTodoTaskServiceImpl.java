package com.clx.workflow.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.flowable.domain.WfFlowRecord;
import com.clx.workflow.flowable.domain.WfTodoTask;
import com.clx.workflow.flowable.mapper.WfTodoTaskMapper;
import com.clx.workflow.flowable.service.IWfFlowRecordService;
import com.clx.workflow.flowable.service.IWfTodoTaskService;
import com.clx.workflow.common.utils.SecurityUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办任务 Service 实现
 *
 * @author clx
 */
@Service
public class WfTodoTaskServiceImpl extends ServiceImpl<WfTodoTaskMapper, WfTodoTask>
        implements IWfTodoTaskService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private IWfFlowRecordService flowRecordService;

    @Override
    public List<WfTodoTask> selectTodoList(Long userId) {
        LambdaQueryWrapper<WfTodoTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfTodoTask::getAssigneeId, userId)
                .eq(WfTodoTask::getStatus, "pending")
                .orderByDesc(WfTodoTask::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<WfTodoTask> selectDoneList(Long userId) {
        LambdaQueryWrapper<WfTodoTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfTodoTask::getAssigneeId, userId)
                .eq(WfTodoTask::getStatus, "completed")
                .orderByDesc(WfTodoTask::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeTask(String taskId, String approveResult, String comment) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new ServiceException("任务不存在");
        }

        // 设置审批变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("approveResult", approveResult);
        variables.put("comment", comment);

        // 完成任务
        taskService.complete(taskId, variables);

        // 保存审批记录
        WfFlowRecord record = new WfFlowRecord();
        record.setProcessInstanceId(task.getProcessInstanceId());
        record.setTaskId(taskId);
        record.setTaskName(task.getName());
        record.setTaskKey(task.getTaskDefinitionKey());
        record.setAssigneeId(SecurityUtils.getUserId());
        record.setAssigneeName(SecurityUtils.getUsername());
        record.setEndTime(LocalDateTime.now());
        record.setApproveResult(approveResult);
        record.setComment(comment);
        record.setCreateTime(LocalDateTime.now());
        flowRecordService.save(record);

        // 删除待办任务
        deleteByTaskId(taskId);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rejectTask(String taskId, String comment) {
        return completeTask(taskId, "rejected", comment);
    }

    @Override
    public boolean deleteByTaskId(String taskId) {
        return this.lambdaUpdate()
                .eq(WfTodoTask::getTaskId, taskId)
                .remove();
    }
}