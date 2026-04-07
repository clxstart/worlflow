package com.clx.workflow.doc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.doc.domain.KnowledgeArticle;

import java.util.List;

/**
 * 知识库文章Service接口
 */
public interface IKnowledgeArticleService extends IService<KnowledgeArticle> {

    Page<KnowledgeArticle> selectArticlePage(Page<KnowledgeArticle> page, KnowledgeArticle article);

    List<KnowledgeArticle> selectArticleList(KnowledgeArticle article);

    List<KnowledgeArticle> selectHotArticles(int limit);

    int incrementReadCount(Long articleId);

    int incrementLikeCount(Long articleId);

    int publishArticle(Long articleId);
}