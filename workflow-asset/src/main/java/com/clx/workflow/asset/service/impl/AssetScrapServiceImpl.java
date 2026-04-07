package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetScrap;
import com.clx.workflow.asset.mapper.AssetScrapMapper;
import com.clx.workflow.asset.service.IAssetScrapService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 资产报废Service实现
 *
 * @author clx
 */
@Service
public class AssetScrapServiceImpl extends ServiceImpl<AssetScrapMapper, AssetScrap> implements IAssetScrapService {

    @Override
    public Page<AssetScrap> selectScrapPage(Page<AssetScrap> page, AssetScrap scrap) {
        LambdaQueryWrapper<AssetScrap> wrapper = new LambdaQueryWrapper<>();
        if (scrap.getScrapNo() != null && !scrap.getScrapNo().isEmpty()) {
            wrapper.like(AssetScrap::getScrapNo, scrap.getScrapNo());
        }
        if (scrap.getAssetId() != null) {
            wrapper.eq(AssetScrap::getAssetId, scrap.getAssetId());
        }
        if (scrap.getUserId() != null) {
            wrapper.eq(AssetScrap::getUserId, scrap.getUserId());
        }
        if (scrap.getStatus() != null && !scrap.getStatus().isEmpty()) {
            wrapper.eq(AssetScrap::getStatus, scrap.getStatus());
        }
        wrapper.orderByDesc(AssetScrap::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<AssetScrap> selectScrapList(AssetScrap scrap) {
        LambdaQueryWrapper<AssetScrap> wrapper = new LambdaQueryWrapper<>();
        if (scrap.getScrapNo() != null && !scrap.getScrapNo().isEmpty()) {
            wrapper.like(AssetScrap::getScrapNo, scrap.getScrapNo());
        }
        if (scrap.getAssetId() != null) {
            wrapper.eq(AssetScrap::getAssetId, scrap.getAssetId());
        }
        if (scrap.getStatus() != null && !scrap.getStatus().isEmpty()) {
            wrapper.eq(AssetScrap::getStatus, scrap.getStatus());
        }
        wrapper.orderByDesc(AssetScrap::getCreateTime);
        return list(wrapper);
    }

    @Override
    public AssetScrap selectScrapByAssetId(Long assetId) {
        LambdaQueryWrapper<AssetScrap> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetScrap::getAssetId, assetId);
        wrapper.orderByDesc(AssetScrap::getCreateTime);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }

    @Override
    @Transactional
    public int approveScrap(Long scrapId, String status, Long approveUserId) {
        LambdaUpdateWrapper<AssetScrap> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetScrap::getScrapId, scrapId);
        wrapper.set(AssetScrap::getStatus, status);
        wrapper.set(AssetScrap::getApproveUserId, approveUserId);
        wrapper.set(AssetScrap::getApproveTime, LocalDateTime.now());
        return update(wrapper) ? 1 : 0;
    }
}