package com.clx.workflow.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.flowable.domain.WfTodoTask;

import java.util.List;

/**
 * 待办任务 Service 接口
 *
 * @author clx
 */
public interface IWfTodoTaskService extends IService<WfTodoTask> {

    /**
     * 查询待办任务列表
     */
    List<WfTodoTask> selectTodoList(Long userId);

    /**
     * 查询已办任务列表
     */
    List<WfTodoTask> selectDoneList(Long userId);

    /**
     * 完成任务
     */
    boolean completeTask(String taskId, String approveResult, String comment);

    /**
     * 拒绝任务
     */
    boolean rejectTask(String taskId, String comment);

    /**
     * 根据任务ID删除
     */
    boolean deleteByTaskId(String taskId);
}