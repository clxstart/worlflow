package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.ProjectMember;

import java.util.List;

/**
 * 项目成员 Service 接口
 *
 * @author clx
 */
public interface IProjectMemberService extends IService<ProjectMember> {

    /**
     * 根据项目ID查询成员列表
     */
    List<ProjectMember> selectMembersByProjectId(Long projectId);
}