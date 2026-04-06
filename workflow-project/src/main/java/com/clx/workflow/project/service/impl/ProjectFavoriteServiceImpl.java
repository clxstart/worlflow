package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.ProjectFavorite;
import com.clx.workflow.project.mapper.ProjectFavoriteMapper;
import com.clx.workflow.project.service.IProjectFavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目收藏 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectFavoriteServiceImpl extends ServiceImpl<ProjectFavoriteMapper, ProjectFavorite> implements IProjectFavoriteService {

    @Override
    public List<ProjectFavorite> selectFavoritesByUserId(Long userId) {
        LambdaQueryWrapper<ProjectFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectFavorite::getUserId, userId);
        wrapper.orderByDesc(ProjectFavorite::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean isFavorited(Long projectId, Long userId) {
        LambdaQueryWrapper<ProjectFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectFavorite::getProjectId, projectId);
        wrapper.eq(ProjectFavorite::getUserId, userId);
        return baseMapper.selectCount(wrapper) > 0;
    }
}