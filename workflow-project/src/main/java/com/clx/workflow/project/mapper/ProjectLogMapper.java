package com.clx.workflow.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.project.domain.ProjectLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目日志 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface ProjectLogMapper extends BaseMapper<ProjectLog> {
}