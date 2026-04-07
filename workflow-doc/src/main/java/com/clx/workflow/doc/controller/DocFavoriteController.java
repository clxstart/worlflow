package com.clx.workflow.doc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import com.clx.workflow.doc.domain.DocFavorite;
import com.clx.workflow.doc.service.IDocFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档收藏Controller
 */
@RestController
@RequestMapping("/doc/favorite")
public class DocFavoriteController extends BaseController {

    @Autowired
    private IDocFavoriteService docFavoriteService;

    @PreAuthorize("@ss.hasPermi('doc:favorite:list')")
    @GetMapping("/list")
    public AjaxResult list() {
        Long userId = SecurityUtils.getUserId();
        Page<DocFavorite> page = docFavoriteService.selectFavoritePage(getPage(), userId);
        return AjaxResult.success(page);
    }

    @PreAuthorize("@ss.hasPermi('doc:favorite:query')")
    @GetMapping("/check/{docId}")
    public AjaxResult checkFavorited(@PathVariable Long docId) {
        boolean favorited = docFavoriteService.isFavorited(docId, SecurityUtils.getUserId());
        return AjaxResult.success(favorited);
    }

    @PreAuthorize("@ss.hasPermi('doc:favorite:add')")
    @PostMapping("/{docId}")
    public AjaxResult add(@PathVariable Long docId) {
        Long userId = SecurityUtils.getUserId();
        int result = docFavoriteService.addFavorite(docId, userId, SecurityUtils.getUsername());
        return toAjax(result);
    }

    @PreAuthorize("@ss.hasPermi('doc:favorite:remove')")
    @DeleteMapping("/{docId}")
    public AjaxResult remove(@PathVariable Long docId) {
        int result = docFavoriteService.removeFavorite(docId, SecurityUtils.getUserId());
        return toAjax(result);
    }
}