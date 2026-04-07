package com.clx.workflow.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.doc.domain.DocFavorite;
import com.clx.workflow.doc.mapper.DocFavoriteMapper;
import com.clx.workflow.doc.service.IDocFavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档收藏Service实现
 */
@Service
public class DocFavoriteServiceImpl extends ServiceImpl<DocFavoriteMapper, DocFavorite> implements IDocFavoriteService {

    @Override
    public Page<DocFavorite> selectFavoritePage(Page<DocFavorite> page, Long userId) {
        LambdaQueryWrapper<DocFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocFavorite::getUserId, userId);
        wrapper.orderByDesc(DocFavorite::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<DocFavorite> selectFavoritesByUserId(Long userId) {
        LambdaQueryWrapper<DocFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocFavorite::getUserId, userId);
        wrapper.orderByDesc(DocFavorite::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int addFavorite(Long docId, Long userId, String userName) {
        if (isFavorited(docId, userId)) {
            return 0;
        }
        DocFavorite favorite = new DocFavorite();
        favorite.setDocId(docId);
        favorite.setUserId(userId);
        favorite.setUserName(userName);
        favorite.setCreateTime(LocalDateTime.now());
        return save(favorite) ? 1 : 0;
    }

    @Override
    @Transactional
    public int removeFavorite(Long docId, Long userId) {
        LambdaQueryWrapper<DocFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocFavorite::getDocId, docId);
        wrapper.eq(DocFavorite::getUserId, userId);
        return remove(wrapper) ? 1 : 0;
    }

    @Override
    public boolean isFavorited(Long docId, Long userId) {
        LambdaQueryWrapper<DocFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocFavorite::getDocId, docId);
        wrapper.eq(DocFavorite::getUserId, userId);
        return count(wrapper) > 0;
    }
}