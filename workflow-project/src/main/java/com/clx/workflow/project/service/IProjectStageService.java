package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.ProjectStage;

import java.util.List;

/**
 * 项目阶段 Service 接口
 *
 * @author clx
 */
public interface IProjectStageService extends IService<ProjectStage> {

    /**
     * 根据项目ID查询阶段列表
     */
    List<ProjectStage> selectStagesByProjectId(Long projectId);
}