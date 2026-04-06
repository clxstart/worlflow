package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.ProjectFavorite;
import com.clx.workflow.project.service.IProjectFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目收藏 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/favorite")
public class ProjectFavoriteController extends BaseController {

    @Autowired
    private IProjectFavoriteService favoriteService;

    /**
     * 根据用户ID查询收藏列表
     */
    @GetMapping("/list/{userId}")
    public AjaxResult list(@PathVariable Long userId) {
        List<ProjectFavorite> list = favoriteService.selectFavoritesByUserId(userId);
        return success(list);
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/check/{projectId}/{userId}")
    public AjaxResult check(@PathVariable Long projectId, @PathVariable Long userId) {
        return success(favoriteService.isFavorited(projectId, userId));
    }

    /**
     * 添加收藏
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProjectFavorite favorite) {
        if (favoriteService.isFavorited(favorite.getProjectId(), favorite.getUserId())) {
            return error("已收藏该项目");
        }
        favorite.setCreateTime(LocalDateTime.now());
        return toAjax(favoriteService.save(favorite));
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/{projectId}/{userId}")
    public AjaxResult remove(@PathVariable Long projectId, @PathVariable Long userId) {
        return toAjax(favoriteService.lambdaUpdate()
                .eq(ProjectFavorite::getProjectId, projectId)
                .eq(ProjectFavorite::getUserId, userId)
                .remove());
    }
}