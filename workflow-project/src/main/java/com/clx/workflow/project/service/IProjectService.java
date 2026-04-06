package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.Project;

import java.util.List;

/**
 * 项目 Service 接口
 *
 * @author clx
 */
public interface IProjectService extends IService<Project> {

    /**
     * 查询项目列表
     */
    List<Project> selectProjectList(Project project);

    /**
     * 根据用户ID查询参与的项目
     */
    List<Project> selectProjectsByUserId(Long userId);
}