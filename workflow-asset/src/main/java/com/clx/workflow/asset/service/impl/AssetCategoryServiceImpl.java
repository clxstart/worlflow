package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetCategory;
import com.clx.workflow.asset.mapper.AssetCategoryMapper;
import com.clx.workflow.asset.service.IAssetCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资产分类Service实现
 *
 * @author clx
 */
@Service
public class AssetCategoryServiceImpl extends ServiceImpl<AssetCategoryMapper, AssetCategory> implements IAssetCategoryService {

    @Override
    public List<AssetCategory> selectCategoryList(AssetCategory category) {
        LambdaQueryWrapper<AssetCategory> wrapper = new LambdaQueryWrapper<>();
        if (category.getCategoryName() != null && !category.getCategoryName().isEmpty()) {
            wrapper.like(AssetCategory::getCategoryName, category.getCategoryName());
        }
        if (category.getParentId() != null) {
            wrapper.eq(AssetCategory::getParentId, category.getParentId());
        }
        if (category.getStatus() != null && !category.getStatus().isEmpty()) {
            wrapper.eq(AssetCategory::getStatus, category.getStatus());
        }
        wrapper.orderByAsc(AssetCategory::getSort);
        return list(wrapper);
    }

    @Override
    public List<AssetCategory> buildCategoryTree(List<AssetCategory> categories) {
        List<AssetCategory> tree = new ArrayList<>();
        Map<Long, AssetCategory> categoryMap = categories.stream()
                .collect(Collectors.toMap(AssetCategory::getCategoryId, c -> c));

        for (AssetCategory category : categories) {
            if (category.getParentId() == null || category.getParentId() == 0L) {
                tree.add(category);
            } else {
                AssetCategory parent = categoryMap.get(category.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(category);
                }
            }
        }
        return tree;
    }

    @Override
    public List<AssetCategory> selectChildrenByParentId(Long parentId) {
        LambdaQueryWrapper<AssetCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetCategory::getParentId, parentId);
        wrapper.orderByAsc(AssetCategory::getSort);
        return list(wrapper);
    }
}