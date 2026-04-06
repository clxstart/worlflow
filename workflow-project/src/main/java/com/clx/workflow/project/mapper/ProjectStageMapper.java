package com.clx.workflow.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.project.domain.ProjectStage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目阶段 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface ProjectStageMapper extends BaseMapper<ProjectStage> {
}