package com.clx.workflow.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.asset.domain.AssetInventory;
import com.clx.workflow.asset.domain.AssetInventoryDetail;
import com.clx.workflow.asset.service.IAssetInfoService;
import com.clx.workflow.asset.service.IAssetInventoryDetailService;
import com.clx.workflow.asset.service.IAssetInventoryService;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产盘点Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/asset/inventory")
public class AssetInventoryController extends BaseController {

    @Autowired
    private IAssetInventoryService assetInventoryService;

    @Autowired
    private IAssetInventoryDetailService assetInventoryDetailService;

    @Autowired
    private IAssetInfoService assetInfoService;

    /**
     * 分页查询盘点记录
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:list')")
    @GetMapping("/list")
    public AjaxResult list(AssetInventory inventory) {
        Page<AssetInventory> page = assetInventoryService.selectInventoryPage(getPage(), inventory);
        return AjaxResult.success(page);
    }

    /**
     * 获取盘点详情
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:query')")
    @GetMapping("/{inventoryId}")
    public AjaxResult getInfo(@PathVariable Long inventoryId) {
        AssetInventory inventory = assetInventoryService.getById(inventoryId);
        List<AssetInventoryDetail> details = assetInventoryDetailService.selectDetailsByInventoryId(inventoryId);
        inventory.setDetailList(details);
        return AjaxResult.success(inventory);
    }

    /**
     * 查询盘点明细
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:list')")
    @GetMapping("/detail/list")
    public AjaxResult detailList(AssetInventoryDetail detail) {
        Page<AssetInventoryDetail> page = assetInventoryDetailService.selectDetailPage(getPage(), detail);
        return AjaxResult.success(page);
    }

    /**
     * 新增盘点
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetInventory inventory) {
        inventory.setStatus("0"); // 待盘点
        inventory.setOperatorId(SecurityUtils.getUserId());
        return toAjax(assetInventoryService.save(inventory));
    }

    /**
     * 开始盘点
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:edit')")
    @PutMapping("/start/{inventoryId}")
    public AjaxResult start(@PathVariable Long inventoryId) {
        int result = assetInventoryService.startInventory(inventoryId);
        if (result > 0) {
            // 创建盘点明细（根据盘点范围）
            // TODO: 根据盘点类型获取资产列表，创建明细
        }
        return toAjax(result);
    }

    /**
     * 更新盘点明细
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:edit')")
    @PutMapping("/detail")
    public AjaxResult updateDetail(@RequestBody AssetInventoryDetail detail) {
        // 计算差异类型
        if (detail.getActualQuantity() != null && detail.getBookQuantity() != null) {
            int diff = detail.getActualQuantity() - detail.getBookQuantity();
            if (diff > 0) {
                detail.setDiffType("profit"); // 盘盈
            } else if (diff < 0) {
                detail.setDiffType("loss"); // 盘亏
            } else {
                detail.setDiffType("normal"); // 正常
            }
        }
        detail.setStatus("1"); // 已盘点
        return toAjax(assetInventoryDetailService.updateDetail(detail));
    }

    /**
     * 完成盘点
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:edit')")
    @PutMapping("/complete/{inventoryId}")
    public AjaxResult complete(@PathVariable Long inventoryId) {
        // 统计盘点结果
        List<AssetInventoryDetail> details = assetInventoryDetailService.selectDetailsByInventoryId(inventoryId);
        int profitCount = 0;
        int lossCount = 0;
        int actualCount = 0;

        for (AssetInventoryDetail detail : details) {
            actualCount += detail.getActualQuantity() != null ? detail.getActualQuantity() : 0;
            if ("profit".equals(detail.getDiffType())) {
                profitCount++;
            } else if ("loss".equals(detail.getDiffType())) {
                lossCount++;
            }
        }

        AssetInventory inventory = assetInventoryService.getById(inventoryId);
        inventory.setActualCount(actualCount);
        inventory.setProfitCount(profitCount);
        inventory.setLossCount(lossCount);
        assetInventoryService.updateById(inventory);

        return toAjax(assetInventoryService.completeInventory(inventoryId));
    }

    /**
     * 删除盘点记录
     */
    @PreAuthorize("@ss.hasPermi('asset:inventory:remove')")
    @DeleteMapping("/{inventoryIds}")
    public AjaxResult remove(@PathVariable Long[] inventoryIds) {
        return toAjax(assetInventoryService.removeBatchByIds(List.of(inventoryIds)));
    }
}