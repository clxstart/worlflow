package com.clx.workflow.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.project.domain.ProjectFile;
import com.clx.workflow.project.mapper.ProjectFileMapper;
import com.clx.workflow.project.service.IProjectFileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目文件 Service 实现类
 *
 * @author clx
 */
@Service
public class ProjectFileServiceImpl extends ServiceImpl<ProjectFileMapper, ProjectFile> implements IProjectFileService {

    @Override
    public List<ProjectFile> selectFilesByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectFile::getProjectId, projectId);
        wrapper.orderByDesc(ProjectFile::getUploadTime);
        return baseMapper.selectList(wrapper);
    }
}