package com.clx.workflow.doc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.doc.domain.DocCategory;

import java.util.List;

/**
 * 文档分类Service接口
 */
public interface IDocCategoryService extends IService<DocCategory> {

    List<DocCategory> selectCategoryList(DocCategory category);

    List<DocCategory> buildCategoryTree(List<DocCategory> categories);

    List<DocCategory> selectChildrenByParentId(Long parentId);
}