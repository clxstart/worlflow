package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.Project;
import com.clx.workflow.project.mapper.ProjectMapper;
import com.clx.workflow.project.service.IProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Override
    public List<Project> selectProjectList(Project project) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        if (project.getProjectName() != null && !project.getProjectName().isEmpty()) {
            wrapper.like(Project::getProjectName, project.getProjectName());
        }
        if (project.getProjectCode() != null && !project.getProjectCode().isEmpty()) {
            wrapper.eq(Project::getProjectCode, project.getProjectCode());
        }
        if (project.getOwnerId() != null) {
            wrapper.eq(Project::getOwnerId, project.getOwnerId());
        }
        if (project.getDeptId() != null) {
            wrapper.eq(Project::getDeptId, project.getDeptId());
        }
        if (project.getStatus() != null && !project.getStatus().isEmpty()) {
            wrapper.eq(Project::getStatus, project.getStatus());
        }
        wrapper.orderByDesc(Project::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Project> selectProjectsByUserId(Long userId) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getOwnerId, userId);
        wrapper.orderByDesc(Project::getCreateTime);
        return baseMapper.selectList(wrapper);
    }
}