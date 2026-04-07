package com.clx.workflow.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.doc.domain.KnowledgeArticle;
import com.clx.workflow.doc.mapper.KnowledgeArticleMapper;
import com.clx.workflow.doc.service.IKnowledgeArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库文章Service实现
 */
@Service
public class KnowledgeArticleServiceImpl extends ServiceImpl<KnowledgeArticleMapper, KnowledgeArticle> implements IKnowledgeArticleService {

    @Override
    public Page<KnowledgeArticle> selectArticlePage(Page<KnowledgeArticle> page, KnowledgeArticle article) {
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        if (article.getTitle() != null && !article.getTitle().isEmpty()) {
            wrapper.like(KnowledgeArticle::getTitle, article.getTitle());
        }
        if (article.getCategoryId() != null) {
            wrapper.eq(KnowledgeArticle::getCategoryId, article.getCategoryId());
        }
        if (article.getArticleType() != null && !article.getArticleType().isEmpty()) {
            wrapper.eq(KnowledgeArticle::getArticleType, article.getArticleType());
        }
        if (article.getStatus() != null && !article.getStatus().isEmpty()) {
            wrapper.eq(KnowledgeArticle::getStatus, article.getStatus());
        }
        wrapper.orderByDesc(KnowledgeArticle::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<KnowledgeArticle> selectArticleList(KnowledgeArticle article) {
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        if (article.getTitle() != null && !article.getTitle().isEmpty()) {
            wrapper.like(KnowledgeArticle::getTitle, article.getTitle());
        }
        if (article.getStatus() != null && !article.getStatus().isEmpty()) {
            wrapper.eq(KnowledgeArticle::getStatus, article.getStatus());
        }
        wrapper.orderByDesc(KnowledgeArticle::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<KnowledgeArticle> selectHotArticles(int limit) {
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeArticle::getStatus, "1"); // Published
        wrapper.orderByDesc(KnowledgeArticle::getReadCount);
        wrapper.last("LIMIT " + limit);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int incrementReadCount(Long articleId) {
        LambdaUpdateWrapper<KnowledgeArticle> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(KnowledgeArticle::getArticleId, articleId);
        wrapper.setSql("read_count = read_count + 1");
        return update(wrapper) ? 1 : 0;
    }

    @Override
    @Transactional
    public int incrementLikeCount(Long articleId) {
        LambdaUpdateWrapper<KnowledgeArticle> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(KnowledgeArticle::getArticleId, articleId);
        wrapper.setSql("like_count = like_count + 1");
        return update(wrapper) ? 1 : 0;
    }

    @Override
    @Transactional
    public int publishArticle(Long articleId) {
        LambdaUpdateWrapper<KnowledgeArticle> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(KnowledgeArticle::getArticleId, articleId);
        wrapper.set(KnowledgeArticle::getStatus, "1"); // Published
        wrapper.set(KnowledgeArticle::getPublishTime, LocalDateTime.now());
        return update(wrapper) ? 1 : 0;
    }
}