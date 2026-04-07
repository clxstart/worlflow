package com.clx.workflow.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.asset.domain.AssetRepair;
import com.clx.workflow.asset.service.IAssetInfoService;
import com.clx.workflow.asset.service.IAssetRepairService;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产维修Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/asset/repair")
public class AssetRepairController extends BaseController {

    @Autowired
    private IAssetRepairService assetRepairService;

    @Autowired
    private IAssetInfoService assetInfoService;

    /**
     * 分页查询维修记录
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:list')")
    @GetMapping("/list")
    public AjaxResult list(AssetRepair repair) {
        Page<AssetRepair> page = assetRepairService.selectRepairPage(getPage(), repair);
        return AjaxResult.success(page);
    }

    /**
     * 获取维修详情
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:query')")
    @GetMapping("/{repairId}")
    public AjaxResult getInfo(@PathVariable Long repairId) {
        return AjaxResult.success(assetRepairService.getById(repairId));
    }

    /**
     * 查询资产维修历史
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:list')")
    @GetMapping("/history/{assetId}")
    public AjaxResult getHistory(@PathVariable Long assetId) {
        List<AssetRepair> list = assetRepairService.selectRepairsByAssetId(assetId);
        return AjaxResult.success(list);
    }

    /**
     * 新增维修申请
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AssetRepair repair) {
        repair.setStatus("0"); // 待维修
        return toAjax(assetRepairService.save(repair));
    }

    /**
     * 开始维修
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:edit')")
    @PutMapping("/start/{repairId}")
    public AjaxResult start(@PathVariable Long repairId) {
        AssetRepair repair = assetRepairService.getById(repairId);
        if (repair != null) {
            repair.setStatus("1"); // 维修中
            assetInfoService.updateAssetStatus(repair.getAssetId(), "3"); // 维修中
            return toAjax(assetRepairService.updateById(repair));
        }
        return AjaxResult.error("维修记录不存在");
    }

    /**
     * 完成维修
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:edit')")
    @PutMapping("/complete")
    public AjaxResult complete(@RequestParam Long repairId, @RequestParam String repairResult) {
        int result = assetRepairService.completeRepair(repairId, repairResult);
        if (result > 0) {
            // 更新资产状态为正常
            AssetRepair repair = assetRepairService.getById(repairId);
            assetInfoService.updateAssetStatus(repair.getAssetId(), "1"); // 正常
        }
        return toAjax(result);
    }

    /**
     * 删除维修记录
     */
    @PreAuthorize("@ss.hasPermi('asset:repair:remove')")
    @DeleteMapping("/{repairIds}")
    public AjaxResult remove(@PathVariable Long[] repairIds) {
        return toAjax(assetRepairService.removeBatchByIds(List.of(repairIds)));
    }
}