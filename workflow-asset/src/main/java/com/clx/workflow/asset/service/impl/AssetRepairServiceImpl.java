package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetRepair;
import com.clx.workflow.asset.mapper.AssetRepairMapper;
import com.clx.workflow.asset.service.IAssetRepairService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 产维修Service实现
 *
 * @author clx
 */
@Service
public class AssetRepairServiceImpl extends ServiceImpl<AssetRepairMapper, AssetRepair> implements IAssetRepairService {

    @Override
    public Page<AssetRepair> selectRepairPage(Page<AssetRepair> page, AssetRepair repair) {
        LambdaQueryWrapper<AssetRepair> wrapper = new LambdaQueryWrapper<>();
        if (repair.getRepairNo() != null && !repair.getRepairNo().isEmpty()) {
            wrapper.like(AssetRepair::getRepairNo, repair.getRepairNo());
        }
        if (repair.getAssetId() != null) {
            wrapper.eq(AssetRepair::getAssetId, repair.getAssetId());
        }
        if (repair.getRepairType() != null && !repair.getRepairType().isEmpty()) {
            wrapper.eq(AssetRepair::getRepairType, repair.getRepairType());
        }
        if (repair.getStatus() != null && !repair.getStatus().isEmpty()) {
            wrapper.eq(AssetRepair::getStatus, repair.getStatus());
        }
        wrapper.orderByDesc(AssetRepair::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<AssetRepair> selectRepairList(AssetRepair repair) {
        LambdaQueryWrapper<AssetRepair> wrapper = new LambdaQueryWrapper<>();
        if (repair.getRepairNo() != null && !repair.getRepairNo().isEmpty()) {
            wrapper.like(AssetRepair::getRepairNo, repair.getRepairNo());
        }
        if (repair.getAssetId() != null) {
            wrapper.eq(AssetRepair::getAssetId, repair.getAssetId());
        }
        if (repair.getStatus() != null && !repair.getStatus().isEmpty()) {
            wrapper.eq(AssetRepair::getStatus, repair.getStatus());
        }
        wrapper.orderByDesc(AssetRepair::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<AssetRepair> selectRepairsByAssetId(Long assetId) {
        LambdaQueryWrapper<AssetRepair> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetRepair::getAssetId, assetId);
        wrapper.orderByDesc(AssetRepair::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int completeRepair(Long repairId, String repairResult) {
        LambdaUpdateWrapper<AssetRepair> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetRepair::getRepairId, repairId);
        wrapper.set(AssetRepair::getRepairResult, repairResult);
        wrapper.set(AssetRepair::getCompleteDate, LocalDate.now());
        wrapper.set(AssetRepair::getStatus, "2"); // 已完成
        return update(wrapper) ? 1 : 0;
    }
}