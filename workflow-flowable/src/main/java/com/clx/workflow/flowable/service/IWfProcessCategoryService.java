package com.clx.workflow.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.flowable.domain.WfProcessCategory;

import java.util.List;

/**
 * 流程分类 Service 接口
 *
 * @author clx
 */
public interface IWfProcessCategoryService extends IService<WfProcessCategory> {

    /**
     * 查询分类列表
     */
    List<WfProcessCategory> selectCategoryList(WfProcessCategory category);

    /**
     * 构建分类树
     */
    List<WfProcessCategory> buildCategoryTree(List<WfProcessCategory> categories);

    /**
     * 是否存在子分类
     */
    boolean hasChildById(Long categoryId);
}