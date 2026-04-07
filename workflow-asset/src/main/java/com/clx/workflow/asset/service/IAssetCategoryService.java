package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetCategory;

import java.util.List;

/**
 * 资产分类Service接口
 *
 * @author clx
 */
public interface IAssetCategoryService extends IService<AssetCategory> {

    /**
     * 查询分类列表
     */
    List<AssetCategory> selectCategoryList(AssetCategory category);

    /**
     * 查询分类树结构
     */
    List<AssetCategory> buildCategoryTree(List<AssetCategory> categories);

    /**
     * 根据父ID查询子分类
     */
    List<AssetCategory> selectChildrenByParentId(Long parentId);
}