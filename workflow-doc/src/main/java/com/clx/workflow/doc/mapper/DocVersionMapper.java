package com.clx.workflow.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.doc.domain.DocVersion;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档版本Mapper接口
 */
@Mapper
public interface DocVersionMapper extends BaseMapper<DocVersion> {
}