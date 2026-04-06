package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.ProjectStage;
import com.clx.workflow.project.mapper.ProjectStageMapper;
import com.clx.workflow.project.service.IProjectStageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目阶段 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectStageServiceImpl extends ServiceImpl<ProjectStageMapper, ProjectStage> implements IProjectStageService {

    @Override
    public List<ProjectStage> selectStagesByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectStage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectStage::getProjectId, projectId);
        wrapper.orderByAsc(ProjectStage::getStageSort);
        return baseMapper.selectList(wrapper);
    }
}