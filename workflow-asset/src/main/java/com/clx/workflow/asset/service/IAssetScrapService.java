package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetScrap;

import java.util.List;

/**
 * 资产报废Service接口
 *
 * @author clx
 */
public interface IAssetScrapService extends IService<AssetScrap> {

    /**
     * 分页查询报废记录
     */
    Page<AssetScrap> selectScrapPage(Page<AssetScrap> page, AssetScrap scrap);

    /**
     * 查询报废记录列表
     */
    List<AssetScrap> selectScrapList(AssetScrap scrap);

    /**
     * 根据资产ID查询报废记录
     */
    AssetScrap selectScrapByAssetId(Long assetId);

    /**
     * 审批报废申请
     */
    int approveScrap(Long scrapId, String status, Long approveUserId);
}