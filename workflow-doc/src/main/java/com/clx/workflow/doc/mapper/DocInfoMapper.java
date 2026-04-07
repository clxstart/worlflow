package com.clx.workflow.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.doc.domain.DocInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档信息Mapper接口
 */
@Mapper
public interface DocInfoMapper extends BaseMapper<DocInfo> {
}