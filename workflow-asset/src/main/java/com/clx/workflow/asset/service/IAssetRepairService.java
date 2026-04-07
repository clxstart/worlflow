package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetRepair;

import java.util.List;

/**
 * 资产维修Service接口
 *
 * @author clx
 */
public interface IAssetRepairService extends IService<AssetRepair> {

    /**
     * 分页查询维修记录
     */
    Page<AssetRepair> selectRepairPage(Page<AssetRepair> page, AssetRepair repair);

    /**
     * 查询维修记录列表
     */
    List<AssetRepair> selectRepairList(AssetRepair repair);

    /**
     * 根据资产ID查询维修历史
     */
    List<AssetRepair> selectRepairsByAssetId(Long assetId);

    /**
     * 完成维修
     */
    int completeRepair(Long repairId, String repairResult);
}