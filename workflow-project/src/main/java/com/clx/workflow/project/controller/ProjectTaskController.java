package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.ProjectTask;
import com.clx.workflow.project.service.IProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目任务 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/task")
public class ProjectTaskController extends BaseController {

    @Autowired
    private IProjectTaskService taskService;

    /**
     * 查询任务列表
     */
    @PreAuthorize("@ss.hasPermi('project:task:list')")
    @GetMapping("/list")
    public AjaxResult list(ProjectTask task) {
        List<ProjectTask> list = taskService.selectTaskList(task);
        return success(list);
    }

    /**
     * 根据项目ID查询任务列表
     */
    @GetMapping("/project/{projectId}")
    public AjaxResult getByProject(@PathVariable Long projectId) {
        List<ProjectTask> list = taskService.selectTasksByProjectId(projectId);
        return success(list);
    }

    /**
     * 查询任务详情
     */
    @GetMapping("/{taskId}")
    public AjaxResult getInfo(@PathVariable Long taskId) {
        return success(taskService.getById(taskId));
    }

    /**
     * 新增任务
     */
    @PreAuthorize("@ss.hasPermi('project:task:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ProjectTask task) {
        task.setStatus("0"); // 待办状态
        return toAjax(taskService.save(task));
    }

    /**
     * 修改任务
     */
    @PreAuthorize("@ss.hasPermi('project:task:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ProjectTask task) {
        return toAjax(taskService.updateById(task));
    }

    /**
     * 更新任务进度
     */
    @PreAuthorize("@ss.hasPermi('project:task:edit')")
    @PutMapping("/progress/{taskId}/{progress}")
    public AjaxResult updateProgress(@PathVariable Long taskId, @PathVariable Integer progress) {
        ProjectTask task = taskService.getById(taskId);
        if (task == null) {
            return error("任务不存在");
        }
        task.setProgress(progress);
        if (progress >= 100) {
            task.setStatus("2"); // 已完成
        }
        return toAjax(taskService.updateById(task));
    }

    /**
     * 删除任务
     */
    @PreAuthorize("@ss.hasPermi('project:task:remove')")
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds) {
        return toAjax(taskService.removeByIds(java.util.Arrays.asList(taskIds)));
    }
}