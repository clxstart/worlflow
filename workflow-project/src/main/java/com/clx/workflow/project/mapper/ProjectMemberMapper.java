package com.clx.workflow.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.project.domain.ProjectMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目成员 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {
}