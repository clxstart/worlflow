package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetInfo;

import java.util.List;

/**
 * 资产信息Service接口
 *
 * @author clx
 */
public interface IAssetInfoService extends IService<AssetInfo> {

    /**
     * 分页查询资产列表
     */
    Page<AssetInfo> selectAssetPage(Page<AssetInfo> page, AssetInfo asset);

    /**
     * 查询资产列表
     */
    List<AssetInfo> selectAssetList(AssetInfo asset);

    /**
     * 根据分类查询资产
     */
    List<AssetInfo> selectAssetsByCategory(Long categoryId);

    /**
     * 更新资产状态
     */
    int updateAssetStatus(Long assetId, String status);
}