package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.ProjectLog;
import com.clx.workflow.project.mapper.ProjectLogMapper;
import com.clx.workflow.project.service.IProjectLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目日志 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectLogServiceImpl extends ServiceImpl<ProjectLogMapper, ProjectLog> implements IProjectLogService {

    @Override
    public List<ProjectLog> selectLogsByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectLog::getProjectId, projectId);
        wrapper.orderByDesc(ProjectLog::getCreateTime);
        return baseMapper.selectList(wrapper);
    }
}