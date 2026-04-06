package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.ProjectStage;
import com.clx.workflow.project.service.IProjectStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目阶段 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/stage")
public class ProjectStageController extends BaseController {

    @Autowired
    private IProjectStageService stageService;

    /**
     * 根据项目ID查询阶段列表
     */
    @GetMapping("/list/{projectId}")
    public AjaxResult list(@PathVariable Long projectId) {
        List<ProjectStage> list = stageService.selectStagesByProjectId(projectId);
        return success(list);
    }

    /**
     * 查询阶段详情
     */
    @GetMapping("/{stageId}")
    public AjaxResult getInfo(@PathVariable Long stageId) {
        return success(stageService.getById(stageId));
    }

    /**
     * 新增阶段
     */
    @PreAuthorize("@ss.hasPermi('project:stage:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ProjectStage stage) {
        return toAjax(stageService.save(stage));
    }

    /**
     * 修改阶段
     */
    @PreAuthorize("@ss.hasPermi('project:stage:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ProjectStage stage) {
        return toAjax(stageService.updateById(stage));
    }

    /**
     * 删除阶段
     */
    @PreAuthorize("@ss.hasPermi('project:stage:remove')")
    @DeleteMapping("/{stageIds}")
    public AjaxResult remove(@PathVariable Long[] stageIds) {
        return toAjax(stageService.removeByIds(java.util.Arrays.asList(stageIds)));
    }
}