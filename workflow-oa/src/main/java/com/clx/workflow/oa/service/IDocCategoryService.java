package com.clx.workflow.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.oa.domain.DocCategory;

import java.util.List;

/**
 * 文档分类 Service 接口
 *
 * @author clx
 */
public interface IDocCategoryService extends IService<DocCategory> {

    /**
     * 查询文档分类列表
     *
     * @param category 文档分类
     * @return 文档分类集合
     */
    List<DocCategory> selectCategoryList(DocCategory category);

    /**
     * 查询文档分类树结构
     *
     * @param category 文档分类
     * @return 文档分类树
     */
    List<DocCategory> buildCategoryTree(DocCategory category);

    /**
     * 构建前端所需要树结构
     *
     * @param categories 分类列表
     * @return 树结构列表
     */
    List<DocCategory> buildCategoryTree(List<DocCategory> categories);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param categories 分类列表
     * @return 下拉树结构列表
     */
    List<DocCategory> buildCategoryTreeSelect(List<DocCategory> categories);

    /**
     * 是否存在子节点
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    boolean hasChildByCategoryId(Long categoryId);

    /**
     * 检查分类是否存在文档
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    boolean checkCategoryExistDocument(Long categoryId);
}
