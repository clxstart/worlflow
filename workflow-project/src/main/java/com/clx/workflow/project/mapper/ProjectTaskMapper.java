package com.clx.workflow.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.project.domain.ProjectTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目任务 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface ProjectTaskMapper extends BaseMapper<ProjectTask> {
}