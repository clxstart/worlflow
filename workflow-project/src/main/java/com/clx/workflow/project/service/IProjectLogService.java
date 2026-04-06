package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.ProjectLog;

import java.util.List;

/**
 * 项目日志 Service 接口
 *
 * @author clx
 */
public interface IProjectLogService extends IService<ProjectLog> {

    /**
     * 根据项目ID查询日志列表
     */
    List<ProjectLog> selectLogsByProjectId(Long projectId);
}