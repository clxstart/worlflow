package com.clx.workflow.doc.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.doc.domain.DocCategory;
import com.clx.workflow.doc.service.IDocCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档分类Controller
 */
@RestController
@RequestMapping("/doc/category")
public class DocCategoryController extends BaseController {

    @Autowired
    private IDocCategoryService docCategoryService;

    @PreAuthorize("@ss.hasPermi('doc:category:list')")
    @GetMapping("/list")
    public AjaxResult list(DocCategory category) {
        List<DocCategory> list = docCategoryService.selectCategoryList(category);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('doc:category:list')")
    @GetMapping("/tree")
    public AjaxResult tree(DocCategory category) {
        List<DocCategory> categories = docCategoryService.selectCategoryList(category);
        List<DocCategory> tree = docCategoryService.buildCategoryTree(categories);
        return AjaxResult.success(tree);
    }

    @PreAuthorize("@ss.hasPermi('doc:category:query')")
    @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@PathVariable Long categoryId) {
        return AjaxResult.success(docCategoryService.getById(categoryId));
    }

    @PreAuthorize("@ss.hasPermi('doc:category:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DocCategory category) {
        return toAjax(docCategoryService.save(category));
    }

    @PreAuthorize("@ss.hasPermi('doc:category:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DocCategory category) {
        return toAjax(docCategoryService.updateById(category));
    }

    @PreAuthorize("@ss.hasPermi('doc:category:remove')")
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        for (Long categoryId : categoryIds) {
            List<DocCategory> children = docCategoryService.selectChildrenByParentId(categoryId);
            if (children != null && !children.isEmpty()) {
                return AjaxResult.error("Category has children, cannot delete");
            }
        }
        return toAjax(docCategoryService.removeBatchByIds(List.of(categoryIds)));
    }
}