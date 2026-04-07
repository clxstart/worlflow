package com.clx.workflow.flowable.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.flowable.domain.WfProcessCategory;
import com.clx.workflow.flowable.service.IWfProcessCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程分类 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/flowable/category")
public class WfProcessCategoryController extends BaseController {

    @Autowired
    private IWfProcessCategoryService categoryService;

    /**
     * 查询流程分类列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:category:list')")
    @GetMapping("/list")
    public AjaxResult list(WfProcessCategory category) {
        List<WfProcessCategory> categories = categoryService.selectCategoryList(category);
        List<WfProcessCategory> tree = categoryService.buildCategoryTree(categories);
        return success(tree);
    }

    /**
     * 查询流程分类详情
     */
    @PreAuthorize("@ss.hasPermi('flowable:category:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(categoryService.getById(id));
    }

    /**
     * 新增流程分类
     */
    @PreAuthorize("@ss.hasPermi('flowable:category:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WfProcessCategory category) {
        if (category.getParentId() == null) {
            category.setParentId(0L);
        }
        if (category.getParentId() != 0L) {
            WfProcessCategory parent = categoryService.getById(category.getParentId());
            if (parent != null) {
                category.setAncestors(parent.getAncestors() + "," + category.getParentId());
            }
        } else {
            category.setAncestors("0");
        }
        return toAjax(categoryService.save(category));
    }

    /**
     * 修改流程分类
     */
    @PreAuthorize("@ss.hasPermi('flowable:category:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WfProcessCategory category) {
        return toAjax(categoryService.updateById(category));
    }

    /**
     * 删除流程分类
     */
    @PreAuthorize("@ss.hasPermi('flowable:category:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        if (categoryService.hasChildById(id)) {
            return error("存在子分类,不允许删除");
        }
        return toAjax(categoryService.removeById(id));
    }
}