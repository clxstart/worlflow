package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.ProjectMember;
import com.clx.workflow.project.mapper.ProjectMemberMapper;
import com.clx.workflow.project.service.IProjectMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目成员 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectMemberServiceImpl extends ServiceImpl<ProjectMemberMapper, ProjectMember> implements IProjectMemberService {

    @Override
    public List<ProjectMember> selectMembersByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectMember::getProjectId, projectId);
        wrapper.orderByDesc(ProjectMember::getRoleType);
        return baseMapper.selectList(wrapper);
    }
}