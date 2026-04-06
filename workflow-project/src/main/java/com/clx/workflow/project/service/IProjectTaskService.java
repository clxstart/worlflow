package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.ProjectTask;

import java.util.List;

/**
 * 项目任务 Service 接口
 *
 * @author clx
 */
public interface IProjectTaskService extends IService<ProjectTask> {

    /**
     * 查询任务列表
     */
    List<ProjectTask> selectTaskList(ProjectTask task);

    /**
     * 根据项目ID查询任务列表
     */
    List<ProjectTask> selectTasksByProjectId(Long projectId);
}