package com.clx.workflow.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.project.domain.ProjectFavorite;

import java.util.List;

/**
 * 项目收藏 Service 接口
 *
 * @author clx
 */
public interface IProjectFavoriteService extends IService<ProjectFavorite> {

    /**
     * 根据用户ID查询收藏列表
     */
    List<ProjectFavorite> selectFavoritesByUserId(Long userId);

    /**
     * 检查是否已收藏
     */
    boolean isFavorited(Long projectId, Long userId);
}