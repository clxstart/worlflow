package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetInfo;
import com.clx.workflow.asset.mapper.AssetInfoMapper;
import com.clx.workflow.asset.service.IAssetInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资产信息Service实现
 *
 * @author clx
 */
@Service
public class AssetInfoServiceImpl extends ServiceImpl<AssetInfoMapper, AssetInfo> implements IAssetInfoService {

    @Override
    public Page<AssetInfo> selectAssetPage(Page<AssetInfo> page, AssetInfo asset) {
        LambdaQueryWrapper<AssetInfo> wrapper = new LambdaQueryWrapper<>();
        if (asset.getAssetNo() != null && !asset.getAssetNo().isEmpty()) {
            wrapper.like(AssetInfo::getAssetNo, asset.getAssetNo());
        }
        if (asset.getAssetName() != null && !asset.getAssetName().isEmpty()) {
            wrapper.like(AssetInfo::getAssetName, asset.getAssetName());
        }
        if (asset.getCategoryId() != null) {
            wrapper.eq(AssetInfo::getCategoryId, asset.getCategoryId());
        }
        if (asset.getStatus() != null && !asset.getStatus().isEmpty()) {
            wrapper.eq(AssetInfo::getStatus, asset.getStatus());
        }
        if (asset.getDeptId() != null) {
            wrapper.eq(AssetInfo::getDeptId, asset.getDeptId());
        }
        wrapper.orderByDesc(AssetInfo::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<AssetInfo> selectAssetList(AssetInfo asset) {
        LambdaQueryWrapper<AssetInfo> wrapper = new LambdaQueryWrapper<>();
        if (asset.getAssetNo() != null && !asset.getAssetNo().isEmpty()) {
            wrapper.like(AssetInfo::getAssetNo, asset.getAssetNo());
        }
        if (asset.getAssetName() != null && !asset.getAssetName().isEmpty()) {
            wrapper.like(AssetInfo::getAssetName, asset.getAssetName());
        }
        if (asset.getCategoryId() != null) {
            wrapper.eq(AssetInfo::getCategoryId, asset.getCategoryId());
        }
        if (asset.getStatus() != null && !asset.getStatus().isEmpty()) {
            wrapper.eq(AssetInfo::getStatus, asset.getStatus());
        }
        wrapper.orderByDesc(AssetInfo::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<AssetInfo> selectAssetsByCategory(Long categoryId) {
        LambdaQueryWrapper<AssetInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetInfo::getCategoryId, categoryId);
        wrapper.orderByDesc(AssetInfo::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int updateAssetStatus(Long assetId, String status) {
        LambdaUpdateWrapper<AssetInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetInfo::getAssetId, assetId);
        wrapper.set(AssetInfo::getStatus, status);
        return update(wrapper) ? 1 : 0;
    }
}