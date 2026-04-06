package com.clx.workflow.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.project.domain.ProjectFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目收藏 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface ProjectFavoriteMapper extends BaseMapper<ProjectFavorite> {
}