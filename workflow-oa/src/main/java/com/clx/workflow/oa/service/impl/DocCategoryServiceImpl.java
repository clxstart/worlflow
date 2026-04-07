package com.clx.workflow.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.oa.domain.DocCategory;
import com.clx.workflow.oa.mapper.DocCategoryMapper;
import com.clx.workflow.oa.service.IDocCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档分类 Service 实现类
 *
 * @author clx
 */
@Service
public class DocCategoryServiceImpl extends ServiceImpl<DocCategoryMapper, DocCategory> implements IDocCategoryService {

    @Autowired
    private DocCategoryMapper categoryMapper;

    @Override
    public List<DocCategory> selectCategoryList(DocCategory category) {
        LambdaQueryWrapper<DocCategory> wrapper = new LambdaQueryWrapper<>();
        if (category.getCategoryName() != null && !category.getCategoryName().isEmpty()) {
            wrapper.like(DocCategory::getCategoryName, category.getCategoryName());
        }
        if (category.getStatus() != null && !category.getStatus().isEmpty()) {
            wrapper.eq(DocCategory::getStatus, category.getStatus());
        }
        wrapper.orderByAsc(DocCategory::getSort);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public List<DocCategory> buildCategoryTree(DocCategory category) {
        List<DocCategory> categories = selectCategoryList(category);
        return buildCategoryTree(categories);
    }

    @Override
    public List<DocCategory> buildCategoryTree(List<DocCategory> categories) {
        List<DocCategory> returnList = new ArrayList<>();
        List<Long> tempList = categories.stream().map(DocCategory::getCategoryId).collect(Collectors.toList());
        for (DocCategory cat : categories) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(cat.getParentId())) {
                recursionFn(categories, cat);
                returnList.add(cat);
            }
        }
        if (returnList.isEmpty()) {
            returnList = categories;
        }
        return returnList;
    }

    @Override
    public List<DocCategory> buildCategoryTreeSelect(List<DocCategory> categories) {
        return buildCategoryTree(categories);
    }

    @Override
    public boolean hasChildByCategoryId(Long categoryId) {
        int result = this.lambdaQuery()
                .eq(DocCategory::getParentId, categoryId)
                .count()
                .intValue();
        return result > 0;
    }

    @Override
    public boolean checkCategoryExistDocument(Long categoryId) {
        // TODO: 实现检查分类下是否存在文档
        // 需要等 DocDocument 表创建后实现
        return false;
    }

    /**
     * 检查分类名称是否唯一
     */
    public boolean checkCategoryNameUnique(DocCategory category) {
        Long categoryId = category.getCategoryId() == null ? -1L : category.getCategoryId();
        DocCategory info = this.lambdaQuery()
                .eq(DocCategory::getCategoryName, category.getCategoryName())
                .eq(DocCategory::getParentId, category.getParentId())
                .one();
        if (info != null && !info.getCategoryId().equals(categoryId)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(DocCategory category) {
        if (!checkCategoryNameUnique(category)) {
            throw new ServiceException("新增分类'" + category.getCategoryName() + "'失败，分类名称已存在");
        }
        DocCategory parentCategory = this.getById(category.getParentId());
        // 设置祖级列表
        if (parentCategory != null) {
            category.setAncestors(parentCategory.getAncestors() + "," + category.getParentId());
        } else {
            category.setAncestors("0");
        }
        return super.save(category);
    }

    @Override
    public boolean updateById(DocCategory category) {
        if (!checkCategoryNameUnique(category)) {
            throw new ServiceException("修改分类'" + category.getCategoryName() + "'失败，分类名称已存在");
        }
        DocCategory parentCategory = this.getById(category.getParentId());
        DocCategory oldCategory = this.getById(category.getCategoryId());
        if (parentCategory != null && oldCategory != null) {
            String newAncestors = parentCategory.getAncestors() + "," + category.getParentId();
            String oldAncestors = oldCategory.getAncestors();
            category.setAncestors(newAncestors);
            updateCategoryChildren(category.getCategoryId(), newAncestors, oldAncestors);
        }
        return super.updateById(category);
    }

    /**
     * 删除分类
     */
    public void deleteCategoryById(Long categoryId) {
        if (hasChildByCategoryId(categoryId)) {
            throw new ServiceException("存在子分类,不允许删除");
        }
        if (checkCategoryExistDocument(categoryId)) {
            throw new ServiceException("分类下存在文档,不允许删除");
        }
        categoryMapper.deleteById(categoryId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<DocCategory> list, DocCategory t) {
        // 得到子节点列表
        List<DocCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (DocCategory tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<DocCategory> getChildList(List<DocCategory> list, DocCategory t) {
        List<DocCategory> tlist = new ArrayList<>();
        for (DocCategory n : list) {
            if (n.getParentId() != null && n.getParentId().equals(t.getCategoryId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<DocCategory> list, DocCategory t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 修改子分类的祖级列表
     */
    private void updateCategoryChildren(Long categoryId, String newAncestors, String oldAncestors) {
        List<DocCategory> children = this.lambdaQuery()
                .likeRight(DocCategory::getAncestors, oldAncestors + "," + categoryId)
                .list();
        for (DocCategory child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        this.updateBatchById(children);
    }
}
