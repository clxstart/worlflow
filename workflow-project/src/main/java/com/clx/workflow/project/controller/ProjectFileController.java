package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.ProjectFile;
import com.clx.workflow.project.service.IProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目文件 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/file")
public class ProjectFileController extends BaseController {

    @Autowired
    private IProjectFileService fileService;

    /**
     * 根据项目ID查询文件列表
     */
    @GetMapping("/list/{projectId}")
    public AjaxResult list(@PathVariable Long projectId) {
        List<ProjectFile> list = fileService.selectFilesByProjectId(projectId);
        return success(list);
    }

    /**
     * 查询文件详情
     */
    @GetMapping("/{fileId}")
    public AjaxResult getInfo(@PathVariable Long fileId) {
        return success(fileService.getById(fileId));
    }

    /**
     * 新增文件
     */
    @PreAuthorize("@ss.hasPermi('project:file:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ProjectFile file) {
        file.setUploadTime(LocalDateTime.now());
        return toAjax(fileService.save(file));
    }

    /**
     * 删除文件
     */
    @PreAuthorize("@ss.hasPermi('project:file:remove')")
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds) {
        return toAjax(fileService.removeByIds(java.util.Arrays.asList(fileIds)));
    }
}