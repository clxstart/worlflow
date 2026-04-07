package com.clx.workflow.oa.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.oa.domain.DocCategory;
import com.clx.workflow.oa.service.IDocCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档分类 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/oa/doc/category")
public class DocCategoryController extends BaseController {

    @Autowired
    private IDocCategoryService categoryService;

    /**
     * 获取文档分类列表
     */
    @PreAuthorize("@ss.hasPermi('oa:doc:category:list')")
    @GetMapping("/list")
    public AjaxResult list(DocCategory category) {
        List<DocCategory> categories = categoryService.selectCategoryList(category);
        return success(categories);
    }

    /**
     * 查询文档分类列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('oa:doc:category:list')")
    @GetMapping("/list/exclude/{categoryId}")
    public AjaxResult excludeChild(@PathVariable Long categoryId) {
        List<DocCategory> categories = categoryService.selectCategoryList(new DocCategory());
        categories.removeIf(c -> c.getCategoryId().equals(categoryId)
                || c.getAncestors() != null && c.getAncestors().contains(String.valueOf(categoryId)));
        return success(categories);
    }

    /**
     * 根据分类ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:doc:category:query')")
    @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@PathVariable Long categoryId) {
        return success(categoryService.getById(categoryId));
    }

    /**
     * 获取分类下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(DocCategory category) {
        List<DocCategory> categories = categoryService.selectCategoryList(category);
        return success(categoryService.buildCategoryTreeSelect(categories));
    }

    /**
     * 新增文档分类
     */
    @PreAuthorize("@ss.hasPermi('oa:doc:category:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DocCategory category) {
        return toAjax(categoryService.save(category));
    }

    /**
     * 修改文档分类
     */
    @PreAuthorize("@ss.hasPermi('oa:doc:category:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DocCategory category) {
        return toAjax(categoryService.updateById(category));
    }

    /**
     * 删除文档分类
     */
    @PreAuthorize("@ss.hasPermi('oa:doc:category:remove')")
    @DeleteMapping("/{categoryId}")
    public AjaxResult remove(@PathVariable Long categoryId) {
        if (categoryService.hasChildByCategoryId(categoryId)) {
            return error("存在子分类,不允许删除");
        }
        if (categoryService.checkCategoryExistDocument(categoryId)) {
            return error("分类下存在文档,不允许删除");
        }
        return toAjax(categoryService.removeById(categoryId));
    }
}
