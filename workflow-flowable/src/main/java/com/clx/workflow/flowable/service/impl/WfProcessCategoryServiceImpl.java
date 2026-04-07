package com.clx.workflow.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.flowable.domain.WfProcessCategory;
import com.clx.workflow.flowable.mapper.WfProcessCategoryMapper;
import com.clx.workflow.flowable.service.IWfProcessCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程分类 Service 实现
 *
 * @author clx
 */
@Service
public class WfProcessCategoryServiceImpl extends ServiceImpl<WfProcessCategoryMapper, WfProcessCategory>
        implements IWfProcessCategoryService {

    @Override
    public List<WfProcessCategory> selectCategoryList(WfProcessCategory category) {
        LambdaQueryWrapper<WfProcessCategory> wrapper = new LambdaQueryWrapper<>();
        if (category.getCategoryName() != null && !category.getCategoryName().isEmpty()) {
            wrapper.like(WfProcessCategory::getCategoryName, category.getCategoryName());
        }
        if (category.getStatus() != null && !category.getStatus().isEmpty()) {
            wrapper.eq(WfProcessCategory::getStatus, category.getStatus());
        }
        wrapper.orderByAsc(WfProcessCategory::getSort);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<WfProcessCategory> buildCategoryTree(List<WfProcessCategory> categories) {
        List<WfProcessCategory> tree = new ArrayList<>();
        for (WfProcessCategory category : categories) {
            if (category.getParentId() == null || category.getParentId() == 0) {
                tree.add(category);
                buildChildren(category, categories);
            }
        }
        return tree;
    }

    private void buildChildren(WfProcessCategory parent, List<WfProcessCategory> categories) {
        List<WfProcessCategory> children = categories.stream()
                .filter(c -> c.getParentId() != null && c.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        parent.setChildren(children);
        for (WfProcessCategory child : children) {
            buildChildren(child, categories);
        }
    }

    @Override
    public boolean hasChildById(Long categoryId) {
        int count = this.lambdaQuery()
                .eq(WfProcessCategory::getParentId, categoryId)
                .count()
                .intValue();
        return count > 0;
    }
}