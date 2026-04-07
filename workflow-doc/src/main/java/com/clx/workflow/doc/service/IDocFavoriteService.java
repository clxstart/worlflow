package com.clx.workflow.doc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.doc.domain.DocFavorite;

import java.util.List;

/**
 * 文档收藏Service接口
 */
public interface IDocFavoriteService extends IService<DocFavorite> {

    Page<DocFavorite> selectFavoritePage(Page<DocFavorite> page, Long userId);

    List<DocFavorite> selectFavoritesByUserId(Long userId);

    int addFavorite(Long docId, Long userId, String userName);

    int removeFavorite(Long docId, Long userId);

    boolean isFavorited(Long docId, Long userId);
}