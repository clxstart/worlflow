package com.clx.workflow.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.doc.domain.KnowledgeArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文章Mapper接口
 */
@Mapper
public interface KnowledgeArticleMapper extends BaseMapper<KnowledgeArticle> {
}