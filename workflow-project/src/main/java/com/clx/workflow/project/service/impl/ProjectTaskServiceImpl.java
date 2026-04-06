package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.ProjectTask;
import com.clx.workflow.project.mapper.ProjectTaskMapper;
import com.clx.workflow.project.service.IProjectTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目任务 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectTaskServiceImpl extends ServiceImpl<ProjectTaskMapper, ProjectTask> implements IProjectTaskService {

    @Override
    public List<ProjectTask> selectTaskList(ProjectTask task) {
        LambdaQueryWrapper<ProjectTask> wrapper = new LambdaQueryWrapper<>();
        if (task.getProjectId() != null) {
            wrapper.eq(ProjectTask::getProjectId, task.getProjectId());
        }
        if (task.getStageId() != null) {
            wrapper.eq(ProjectTask::getStageId, task.getStageId());
        }
        if (task.getParentId() != null) {
            wrapper.eq(ProjectTask::getParentId, task.getParentId());
        }
        if (task.getTaskName() != null && !task.getTaskName().isEmpty()) {
            wrapper.like(ProjectTask::getTaskName, task.getTaskName());
        }
        if (task.getAssigneeId() != null) {
            wrapper.eq(ProjectTask::getAssigneeId, task.getAssigneeId());
        }
        if (task.getStatus() != null && !task.getStatus().isEmpty()) {
            wrapper.eq(ProjectTask::getStatus, task.getStatus());
        }
        wrapper.orderByDesc(ProjectTask::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<ProjectTask> selectTasksByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectTask::getProjectId, projectId);
        wrapper.orderByAsc(ProjectTask::getStageId);
        return baseMapper.selectList(wrapper);
    }
}