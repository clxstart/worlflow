package com.clx.workflow.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.asset.domain.AssetUse;
import com.clx.workflow.asset.service.IAssetInfoService;
import com.clx.workflow.asset.service.IAssetUseService;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产领用Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/asset/use")
public class AssetUseController extends BaseController {

    @Autowired
    private IAssetUseService assetUseService;

    @Autowired
    private IAssetInfoService assetInfoService;

    /**
     * 分页查询领用记录
     */
    @PreAuthorize("@ss.hasPermi('asset:use:list')")
    @GetMapping("/list")
    public AjaxResult list(AssetUse use) {
        Page<AssetUse> page = assetUseService.selectUsePage(getPage(), use);
        return AjaxResult.success(page);
    }

    /**
     * 获取领用详情
     */
    @PreAuthorize("@ss.hasPermi('asset:use:query')")
    @GetMapping("/{useId}")
    public AjaxResult getInfo(@PathVariable Long useId) {
        return AjaxResult.success(assetUseService.getById(useId));
    }

    /**
     * 新增领用申请
     */
    @PreAuthorize("@ss.hasPermi('asset:use:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetUse use) {
        use.setStatus("0"); // 待审批
        use.setUserId(SecurityUtils.getUserId());
        return toAjax(assetUseService.save(use));
    }

    /**
     * 审批领用申请
     */
    @PreAuthorize("@ss.hasPermi('asset:use:approve')")
    @PutMapping("/approve")
    public AjaxResult approve(@RequestParam Long useId, @RequestParam String status) {
        Long approveUserId = SecurityUtils.getUserId();
        int result = assetUseService.approveUse(useId, status, approveUserId);
        if (result > 0 && "1".equals(status)) {
            // 审批通过，更新资产状态为领用
            AssetUse use = assetUseService.getById(useId);
            assetInfoService.updateAssetStatus(use.getAssetId(), "2"); // 领用中
        }
        return toAjax(result);
    }

    /**
     * 归还资产
     */
    @PreAuthorize("@ss.hasPermi('asset:use:return')")
    @PutMapping("/return")
    public AjaxResult returnAsset(@RequestParam Long useId,
                                  @RequestParam String returnStatus,
                                  @RequestParam(required = false) String returnRemark) {
        int result = assetUseService.returnAsset(useId, returnStatus, returnRemark);
        if (result > 0) {
            // 更新资产状态为正常
            AssetUse use = assetUseService.getById(useId);
            assetInfoService.updateAssetStatus(use.getAssetId(), "1"); // 正常
        }
        return toAjax(result);
    }

    /**
     * 删除领用记录
     */
    @PreAuthorize("@ss.hasPermi('asset:use:remove')")
    @DeleteMapping("/{useIds}")
    public AjaxResult remove(@PathVariable Long[] useIds) {
        return toAjax(assetUseService.removeBatchByIds(List.of(useIds)));
    }
}