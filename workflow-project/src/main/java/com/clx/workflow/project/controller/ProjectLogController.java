package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.ProjectLog;
import com.clx.workflow.project.service.IProjectLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目日志 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/log")
public class ProjectLogController extends BaseController {

    @Autowired
    private IProjectLogService logService;

    /**
     * 根据项目ID查询日志列表
     */
    @GetMapping("/list/{projectId}")
    public AjaxResult list(@PathVariable Long projectId) {
        List<ProjectLog> list = logService.selectLogsByProjectId(projectId);
        return success(list);
    }

    /**
     * 查询日志详情
     */
    @GetMapping("/{logId}")
    public AjaxResult getInfo(@PathVariable Long logId) {
        return success(logService.getById(logId));
    }
}