package com.clx.workflow.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.doc.domain.DocCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档分类Mapper接口
 */
@Mapper
public interface DocCategoryMapper extends BaseMapper<DocCategory> {
}