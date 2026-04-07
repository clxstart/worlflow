package com.clx.workflow.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.asset.domain.AssetInfo;
import com.clx.workflow.asset.service.IAssetInfoService;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产信息Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/asset/info")
public class AssetInfoController extends BaseController {

    @Autowired
    private IAssetInfoService assetInfoService;

    /**
     * 分页查询资产列表
     */
    @PreAuthorize("@ss.hasPermi('asset:info:list')")
    @GetMapping("/list")
    public AjaxResult list(AssetInfo asset) {
        Page<AssetInfo> page = assetInfoService.selectAssetPage(getPage(), asset);
        return AjaxResult.success(page);
    }

    /**
     * 获取资产详情
     */
    @PreAuthorize("@ss.hasPermi('asset:info:query')")
    @GetMapping("/{assetId}")
    public AjaxResult getInfo(@PathVariable Long assetId) {
        return AjaxResult.success(assetInfoService.getById(assetId));
    }

    /**
     * 根据分类查询资产
     */
    @PreAuthorize("@ss.hasPermi('asset:info:list')")
    @GetMapping("/category/{categoryId}")
    public AjaxResult getByCategory(@PathVariable Long categoryId) {
        List<AssetInfo> list = assetInfoService.selectAssetsByCategory(categoryId);
        return AjaxResult.success(list);
    }

    /**
     * 新增资产
     */
    @PreAuthorize("@ss.hasPermi('asset:info:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetInfo asset) {
        return toAjax(assetInfoService.save(asset));
    }

    /**
     * 修改资产
     */
    @PreAuthorize("@ss.hasPermi('asset:info:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AssetInfo asset) {
        return toAjax(assetInfoService.updateById(asset));
    }

    /**
     * 删除资产
     */
    @PreAuthorize("@ss.hasPermi('asset:info:remove')")
    @DeleteMapping("/{assetIds}")
    public AjaxResult remove(@PathVariable Long[] assetIds) {
        return toAjax(assetInfoService.removeBatchByIds(List.of(assetIds)));
    }

    /**
     * 更新资产状态
     */
    @PreAuthorize("@ss.hasPermi('asset:info:edit')")
    @PutMapping("/status")
    public AjaxResult updateStatus(@RequestParam Long assetId, @RequestParam String status) {
        return toAjax(assetInfoService.updateAssetStatus(assetId, status));
    }
}