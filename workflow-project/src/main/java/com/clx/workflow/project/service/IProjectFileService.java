package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.ProjectFile;

import java.util.List;

/**
 * 项目文件 Service 接口
 *
 * @author clx
 */
public interface IProjectFileService extends IService<ProjectFile> {

    /**
     * 根据项目ID查询文件列表
     */
    List<ProjectFile> selectFilesByProjectId(Long projectId);
}