package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.Project;
import com.clx.workflow.project.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/project")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('project:project:list')")
    @GetMapping("/list")
    public AjaxResult list(Project project) {
        List<Project> list = projectService.selectProjectList(project);
        return success(list);
    }

    /**
     * 查询项目详情
     */
    @PreAuthorize("@ss.hasPermi('project:project:query')")
    @GetMapping("/{projectId}")
    public AjaxResult getInfo(@PathVariable Long projectId) {
        return success(projectService.getById(projectId));
    }

    /**
     * 根据用户ID查询参与的项目
     */
    @GetMapping("/user/{userId}")
    public AjaxResult getByUser(@PathVariable Long userId) {
        return success(projectService.selectProjectsByUserId(userId));
    }

    /**
     * 新增项目
     */
    @PreAuthorize("@ss.hasPermi('project:project:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Project project) {
        project.setStatus("0"); // 规划状态
        return toAjax(projectService.save(project));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('project:project:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Project project) {
        return toAjax(projectService.updateById(project));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('project:project:remove')")
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds) {
        return toAjax(projectService.removeByIds(java.util.Arrays.asList(projectIds)));
    }
}