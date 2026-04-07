package com.clx.workflow.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.doc.domain.DocCategory;
import com.clx.workflow.doc.mapper.DocCategoryMapper;
import com.clx.workflow.doc.service.IDocCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文档分类Service实现
 */
@Service
public class DocCategoryServiceImpl extends ServiceImpl<DocCategoryMapper, DocCategory> implements IDocCategoryService {

    @Override
    public List<DocCategory> selectCategoryList(DocCategory category) {
        LambdaQueryWrapper<DocCategory> wrapper = new LambdaQueryWrapper<>();
        if (category.getCategoryName() != null && !category.getCategoryName().isEmpty()) {
            wrapper.like(DocCategory::getCategoryName, category.getCategoryName());
        }
        if (category.getParentId() != null) {
            wrapper.eq(DocCategory::getParentId, category.getParentId());
        }
        if (category.getCategoryType() != null && !category.getCategoryType().isEmpty()) {
            wrapper.eq(DocCategory::getCategoryType, category.getCategoryType());
        }
        if (category.getStatus() != null && !category.getStatus().isEmpty()) {
            wrapper.eq(DocCategory::getStatus, category.getStatus());
        }
        wrapper.orderByAsc(DocCategory::getSort);
        return list(wrapper);
    }

    @Override
    public List<DocCategory> buildCategoryTree(List<DocCategory> categories) {
        List<DocCategory> tree = new ArrayList<>();
        Map<Long, DocCategory> categoryMap = categories.stream()
                .collect(Collectors.toMap(DocCategory::getCategoryId, c -> c));

        for (DocCategory category : categories) {
            if (category.getParentId() == null || category.getParentId() == 0L) {
                tree.add(category);
            } else {
                DocCategory parent = categoryMap.get(category.getParentId());
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
    public List<DocCategory> selectChildrenByParentId(Long parentId) {
        LambdaQueryWrapper<DocCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocCategory::getParentId, parentId);
        wrapper.orderByAsc(DocCategory::getSort);
        return list(wrapper);
    }
}