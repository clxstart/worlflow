package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetUse;
import com.clx.workflow.asset.mapper.AssetUseMapper;
import com.clx.workflow.asset.service.IAssetUseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 资产领用Service实现
 *
 * @author clx
 */
@Service
public class AssetUseServiceImpl extends ServiceImpl<AssetUseMapper, AssetUse> implements IAssetUseService {

    @Override
    public Page<AssetUse> selectUsePage(Page<AssetUse> page, AssetUse use) {
        LambdaQueryWrapper<AssetUse> wrapper = new LambdaQueryWrapper<>();
        if (use.getUseNo() != null && !use.getUseNo().isEmpty()) {
            wrapper.like(AssetUse::getUseNo, use.getUseNo());
        }
        if (use.getAssetId() != null) {
            wrapper.eq(AssetUse::getAssetId, use.getAssetId());
        }
        if (use.getUserId() != null) {
            wrapper.eq(AssetUse::getUserId, use.getUserId());
        }
        if (use.getStatus() != null && !use.getStatus().isEmpty()) {
            wrapper.eq(AssetUse::getStatus, use.getStatus());
        }
        if (use.getReturnStatus() != null && !use.getReturnStatus().isEmpty()) {
            wrapper.eq(AssetUse::getReturnStatus, use.getReturnStatus());
        }
        wrapper.orderByDesc(AssetUse::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<AssetUse> selectUseList(AssetUse use) {
        LambdaQueryWrapper<AssetUse> wrapper = new LambdaQueryWrapper<>();
        if (use.getUseNo() != null && !use.getUseNo().isEmpty()) {
            wrapper.like(AssetUse::getUseNo, use.getUseNo());
        }
        if (use.getAssetId() != null) {
            wrapper.eq(AssetUse::getAssetId, use.getAssetId());
        }
        if (use.getUserId() != null) {
            wrapper.eq(AssetUse::getUserId, use.getUserId());
        }
        if (use.getStatus() != null && !use.getStatus().isEmpty()) {
            wrapper.eq(AssetUse::getStatus, use.getStatus());
        }
        wrapper.orderByDesc(AssetUse::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<AssetUse> selectUsesByAssetId(Long assetId) {
        LambdaQueryWrapper<AssetUse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetUse::getAssetId, assetId);
        wrapper.orderByDesc(AssetUse::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int approveUse(Long useId, String status, Long approveUserId) {
        LambdaUpdateWrapper<AssetUse> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetUse::getUseId, useId);
        wrapper.set(AssetUse::getStatus, status);
        wrapper.set(AssetUse::getApproveUserId, approveUserId);
        wrapper.set(AssetUse::getApproveTime, LocalDateTime.now());
        return update(wrapper) ? 1 : 0;
    }

    @Override
    @Transactional
    public int returnAsset(Long useId, String returnStatus, String returnRemark) {
        LambdaUpdateWrapper<AssetUse> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetUse::getUseId, useId);
        wrapper.set(AssetUse::getReturnStatus, returnStatus);
        wrapper.set(AssetUse::getReturnRemark, returnRemark);
        wrapper.set(AssetUse::getActualReturnDate, java.time.LocalDate.now());
        return update(wrapper) ? 1 : 0;
    }
}