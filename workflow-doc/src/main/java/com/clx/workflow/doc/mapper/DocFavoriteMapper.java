package com.clx.workflow.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.doc.domain.DocFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档收藏Mapper接口
 */
@Mapper
public interface DocFavoriteMapper extends BaseMapper<DocFavorite> {
}