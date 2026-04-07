package com.clx.workflow.asset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.asset.domain.AssetUse;

import java.util.List;

/**
 * 资产领用Service接口
 *
 * @author clx
 */
public interface IAssetUseService extends IService<AssetUse> {

    /**
     * 分页查询领用记录
     */
    Page<AssetUse> selectUsePage(Page<AssetUse> page, AssetUse use);

    /**
     * 查询领用记录列表
     */
    List<AssetUse> selectUseList(AssetUse use);

    /**
     * 根据资产ID查询领用记录
     */
    List<AssetUse> selectUsesByAssetId(Long assetId);

    /**
     * 审批领用申请
     */
    int approveUse(Long useId, String status, Long approveUserId);

    /**
     * 归还资产
     */
    int returnAsset(Long useId, String returnStatus, String returnRemark);
}