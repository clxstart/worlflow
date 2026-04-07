package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetInventory;

import java.util.List;

/**
 * 资产盘点Service接口
 *
 * @author clx
 */
public interface IAssetInventoryService extends IService<AssetInventory> {

    /**
     * 分页查询盘点记录
     */
    Page<AssetInventory> selectInventoryPage(Page<AssetInventory> page, AssetInventory inventory);

    /**
     * 查询盘点记录列表
     */
    List<AssetInventory> selectInventoryList(AssetInventory inventory);

    /**
     * 开始盘点
     */
    int startInventory(Long inventoryId);

    /**
     * 完成盘点
     */
    int completeInventory(Long inventoryId);
}