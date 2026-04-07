package com.clx.workflow.flowable.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import com.clx.workflow.flowable.domain.WfTodoTask;
import com.clx.workflow.flowable.service.IWfTodoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务管理 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/flowable/task")
public class WfTaskController extends BaseController {

    @Autowired
    private IWfTodoTaskService todoTaskService;

    /**
     * 查询待办任务列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:task:todo')")
    @GetMapping("/todo")
    public AjaxResult todoList() {
        Long userId = SecurityUtils.getUserId();
        List<WfTodoTask> list = todoTaskService.selectTodoList(userId);
        return success(list);
    }

    /**
     * 查询已办任务列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:task:done')")
    @GetMapping("/done")
    public AjaxResult doneList() {
        Long userId = SecurityUtils.getUserId();
        List<WfTodoTask> list = todoTaskService.selectDoneList(userId);
        return success(list);
    }

    /**
     * 查询任务详情
     */
    @PreAuthorize("@ss.hasPermi('flowable:task:query')")
    @GetMapping("/{taskId}")
    public AjaxResult getInfo(@PathVariable String taskId) {
        WfTodoTask task = todoTaskService.lambdaQuery()
                .eq(WfTodoTask::getTaskId, taskId)
                .one();
        return success(task);
    }

    /**
     * 完成审批
     */
    @PreAuthorize("@ss.hasPermi('flowable:task:complete')")
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam String taskId,
                               @RequestParam String approveResult,
                               @RequestParam(required = false) String comment) {
        return toAjax(todoTaskService.completeTask(taskId, approveResult, comment));
    }

    /**
     * 拒绝审批
     */
    @PreAuthorize("@ss.hasPermi('flowable:task:reject')")
    @PutMapping("/reject")
    public AjaxResult reject(@RequestParam String taskId,
                             @RequestParam(required = false) String comment) {
        return toAjax(todoTaskService.rejectTask(taskId, comment));
    }
}