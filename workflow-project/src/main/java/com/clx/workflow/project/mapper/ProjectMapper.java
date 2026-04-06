package com.clx.workflow.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.project.domain.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}