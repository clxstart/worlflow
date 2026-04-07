package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetInventoryDetail;
import com.clx.workflow.asset.mapper.AssetInventoryDetailMapper;
import com.clx.workflow.asset.service.IAssetInventoryDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 盘点明细Service实现
 *
 * @author clx
 */
@Service
public class AssetInventoryDetailServiceImpl extends ServiceImpl<AssetInventoryDetailMapper, AssetInventoryDetail> implements IAssetInventoryDetailService {

    @Override
    public Page<AssetInventoryDetail> selectDetailPage(Page<AssetInventoryDetail> page, AssetInventoryDetail detail) {
        LambdaQueryWrapper<AssetInventoryDetail> wrapper = new LambdaQueryWrapper<>();
        if (detail.getInventoryId() != null) {
            wrapper.eq(AssetInventoryDetail::getInventoryId, detail.getInventoryId());
        }
        if (detail.getAssetId() != null) {
            wrapper.eq(AssetInventoryDetail::getAssetId, detail.getAssetId());
        }
        if (detail.getDiffType() != null && !detail.getDiffType().isEmpty()) {
            wrapper.eq(AssetInventoryDetail::getDiffType, detail.getDiffType());
        }
        if (detail.getStatus() != null && !detail.getStatus().isEmpty()) {
            wrapper.eq(AssetInventoryDetail::getStatus, detail.getStatus());
        }
        wrapper.orderByAsc(AssetInventoryDetail::getDetailId);
        return page(page, wrapper);
    }

    @Override
    public List<AssetInventoryDetail> selectDetailsByInventoryId(Long inventoryId) {
        LambdaQueryWrapper<AssetInventoryDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetInventoryDetail::getInventoryId, inventoryId);
        wrapper.orderByAsc(AssetInventoryDetail::getDetailId);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int updateDetail(AssetInventoryDetail detail) {
        LambdaUpdateWrapper<AssetInventoryDetail> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetInventoryDetail::getDetailId, detail.getDetailId());
        if (detail.getActualQuantity() != null) {
            wrapper.set(AssetInventoryDetail::getActualQuantity, detail.getActualQuantity());
            // 计算差异数量
            if (detail.getBookQuantity() != null) {
                wrapper.set(AssetInventoryDetail::getDiffQuantity, detail.getActualQuantity() - detail.getBookQuantity());
            }
        }
        if (detail.getDiffType() != null) {
            wrapper.set(AssetInventoryDetail::getDiffType, detail.getDiffType());
        }
        if (detail.getDiffReason() != null) {
            wrapper.set(AssetInventoryDetail::getDiffReason, detail.getDiffReason());
        }
        if (detail.getStatus() != null) {
            wrapper.set(AssetInventoryDetail::getStatus, detail.getStatus());
        }
        wrapper.set(AssetInventoryDetail::getUpdateTime, LocalDateTime.now());
        return update(wrapper) ? 1 : 0;
    }
}