package com.clx.workflow.doc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import com.clx.workflow.doc.domain.KnowledgeArticle;
import com.clx.workflow.doc.service.IKnowledgeArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识库文章Controller
 */
@RestController
@RequestMapping("/doc/knowledge")
public class KnowledgeArticleController extends BaseController {

    @Autowired
    private IKnowledgeArticleService knowledgeArticleService;

    @PreAuthorize("@ss.hasPermi('doc:knowledge:list')")
    @GetMapping("/list")
    public AjaxResult list(KnowledgeArticle article) {
        Page<KnowledgeArticle> page = knowledgeArticleService.selectArticlePage(getPage(), article);
        return AjaxResult.success(page);
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:query')")
    @GetMapping("/{articleId}")
    public AjaxResult getInfo(@PathVariable Long articleId) {
        KnowledgeArticle article = knowledgeArticleService.getById(articleId);
        if (article != null) {
            knowledgeArticleService.incrementReadCount(articleId);
        }
        return AjaxResult.success(article);
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:list')")
    @GetMapping("/hot")
    public AjaxResult getHotArticles(@RequestParam(defaultValue = "10") int limit) {
        List<KnowledgeArticle> list = knowledgeArticleService.selectHotArticles(limit);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody KnowledgeArticle article) {
        article.setAuthorId(SecurityUtils.getUserId());
        article.setAuthorName(SecurityUtils.getUsername());
        article.setReadCount(0);
        article.setLikeCount(0);
        article.setCommentCount(0);
        article.setStatus("0"); // Draft
        return toAjax(knowledgeArticleService.save(article));
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody KnowledgeArticle article) {
        return toAjax(knowledgeArticleService.updateById(article));
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:publish')")
    @PutMapping("/publish/{articleId}")
    public AjaxResult publish(@PathVariable Long articleId) {
        return toAjax(knowledgeArticleService.publishArticle(articleId));
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:like')")
    @PutMapping("/like/{articleId}")
    public AjaxResult like(@PathVariable Long articleId) {
        return toAjax(knowledgeArticleService.incrementLikeCount(articleId));
    }

    @PreAuthorize("@ss.hasPermi('doc:knowledge:remove')")
    @DeleteMapping("/{articleIds}")
    public AjaxResult remove(@PathVariable Long[] articleIds) {
        return toAjax(knowledgeArticleService.removeBatchByIds(List.of(articleIds)));
    }
}