package com.clx.workflow.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.asset.domain.AssetScrap;
import com.clx.workflow.asset.service.IAssetInfoService;
import com.clx.workflow.asset.service.IAssetScrapService;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产报废Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/asset/scrap")
public class AssetScrapController extends BaseController {

    @Autowired
    private IAssetScrapService assetScrapService;

    @Autowired
    private IAssetInfoService assetInfoService;

    /**
     * 分页查询报废记录
     */
    @PreAuthorize("@ss.hasPermi('asset:scrap:list')")
    @GetMapping("/list")
    public AjaxResult list(AssetScrap scrap) {
        Page<AssetScrap> page = assetScrapService.selectScrapPage(getPage(), scrap);
        return AjaxResult.success(page);
    }

    /**
     * 获取报废详情
     */
    @PreAuthorize("@ss.hasPermi('asset:scrap:query')")
    @GetMapping("/{scrapId}")
    public AjaxResult getInfo(@PathVariable Long scrapId) {
        return AjaxResult.success(assetScrapService.getById(scrapId));
    }

    /**
     * 新增报废申请
     */
    @PreAuthorize("@ss.hasPermi('asset:scrap:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetScrap scrap) {
        scrap.setStatus("0"); // 待审批
        scrap.setUserId(SecurityUtils.getUserId());
        return toAjax(assetScrapService.save(scrap));
    }

    /**
     * 审批报废申请
     */
    @PreAuthorize("@ss.hasPermi('asset:scrap:approve')")
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long scrapId, @RequestParam String status) {
        Long approveUserId = SecurityUtils.getUserId();
        int result = assetScrapService.approveScrap(scrapId, status, approveUserId);
        if (result > 0 && "1".equals(status)) {
            // 审批通过，更新资产状态为已报废
            AssetScrap scrap = assetScrapService.getById(scrapId);
            assetInfoService.updateAssetStatus(scrap.getAssetId(), "4"); // 已报废
        }
        return toAjax(result);
    }

    /**
     * 删除报废记录
     */
    @PreAuthorize("@ss.hasPermi('asset:scrap:remove')")
    @DeleteMapping("/{scrapIds}")
    public AjaxResult remove(@PathVariable Long[] scrapIds) {
        return toAjax(assetScrapService.removeBatchByIds(List.of(scrapIds)));
    }
}