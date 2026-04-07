package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetInventoryDetail;

import java.util.List;

/**
 * 盘点明细Service接口
 *
 * @author clx
 */
public interface IAssetInventoryDetailService extends IService<AssetInventoryDetail> {

    /**
     * 分页查询盘点明细
     */
    Page<AssetInventoryDetail> selectDetailPage(Page<AssetInventoryDetail> page, AssetInventoryDetail detail);

    /**
     * 根据盘点ID查询明细列表
     */
    List<AssetInventoryDetail> selectDetailsByInventoryId(Long inventoryId);

    /**
     * 更新盘点明细
     */
    int updateDetail(AssetInventoryDetail detail);
}