package com.clx.workflow.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.asset.domain.AssetCategory;
import com.clx.workflow.asset.service.IAssetCategoryService;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产分类Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/asset/category")
public class AssetCategoryController extends BaseController {

    @Autowired
    private IAssetCategoryService assetCategoryService;

    /**
     * 查询分类列表
     */
    @PreAuthorize("@ss.hasPermi('asset:category:list')")
    @GetMapping("/list")
    public AjaxResult list(AssetCategory category) {
        List<AssetCategory> list = assetCategoryService.selectCategoryList(category);
        return AjaxResult.success(list);
    }

    /**
     * 查询分类树结构
     */
    @PreAuthorize("@ss.hasPermi('asset:category:list')")
    @GetMapping("/tree")
    public AjaxResult tree(AssetCategory category) {
        List<AssetCategory> categories = assetCategoryService.selectCategoryList(category);
        List<AssetCategory> tree = assetCategoryService.buildCategoryTree(categories);
        return AjaxResult.success(tree);
    }

    /**
     * 获取分类详情
     */
    @PreAuthorize("@ss.hasPermi('asset:category:query')")
    @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@PathVariable Long categoryId) {
        return AjaxResult.success(assetCategoryService.getById(categoryId));
    }

    /**
     * 新增分类
     */
    @PreAuthorize("@ss.hasPermi('asset:category:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetCategory category) {
        return toAjax(assetCategoryService.save(category));
    }

    /**
     * 修改分类
     */
    @PreAuthorize("@ss.hasPermi('asset:category:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AssetCategory category) {
        return toAjax(assetCategoryService.updateById(category));
    }

    /**
     * 删除分类
     */
    @PreAuthorize("@ss.hasPermi('asset:category:remove')")
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        // 检查是否有子分类
        for (Long categoryId : categoryIds) {
            List<AssetCategory> children = assetCategoryService.selectChildrenByParentId(categoryId);
            if (children != null && !children.isEmpty()) {
                return AjaxResult.error("分类[" + categoryId + "]存在子分类，不能删除");
            }
        }
        return toAjax(assetCategoryService.removeBatchByIds(List.of(categoryIds)));
    }
}