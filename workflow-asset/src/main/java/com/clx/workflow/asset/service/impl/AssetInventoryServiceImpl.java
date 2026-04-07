package com.clx.workflow.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.asset.domain.AssetInventory;
import com.clx.workflow.asset.mapper.AssetInventoryMapper;
import com.clx.workflow.asset.service.IAssetInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资产盘点Service实现
 *
 * @author clx
 */
@Service
public class AssetInventoryServiceImpl extends ServiceImpl<AssetInventoryMapper, AssetInventory> implements IAssetInventoryService {

    @Override
    public Page<AssetInventory> selectInventoryPage(Page<AssetInventory> page, AssetInventory inventory) {
        LambdaQueryWrapper<AssetInventory> wrapper = new LambdaQueryWrapper<>();
        if (inventory.getInventoryNo() != null && !inventory.getInventoryNo().isEmpty()) {
            wrapper.like(AssetInventory::getInventoryNo, inventory.getInventoryNo());
        }
        if (inventory.getInventoryName() != null && !inventory.getInventoryName().isEmpty()) {
            wrapper.like(AssetInventory::getInventoryName, inventory.getInventoryName());
        }
        if (inventory.getInventoryType() != null && !inventory.getInventoryType().isEmpty()) {
            wrapper.eq(AssetInventory::getInventoryType, inventory.getInventoryType());
        }
        if (inventory.getStatus() != null && !inventory.getStatus().isEmpty()) {
            wrapper.eq(AssetInventory::getStatus, inventory.getStatus());
        }
        wrapper.orderByDesc(AssetInventory::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<AssetInventory> selectInventoryList(AssetInventory inventory) {
        LambdaQueryWrapper<AssetInventory> wrapper = new LambdaQueryWrapper<>();
        if (inventory.getInventoryNo() != null && !inventory.getInventoryNo().isEmpty()) {
            wrapper.like(AssetInventory::getInventoryNo, inventory.getInventoryNo());
        }
        if (inventory.getInventoryName() != null && !inventory.getInventoryName().isEmpty()) {
            wrapper.like(AssetInventory::getInventoryName, inventory.getInventoryName());
        }
        if (inventory.getStatus() != null && !inventory.getStatus().isEmpty()) {
            wrapper.eq(AssetInventory::getStatus, inventory.getStatus());
        }
        wrapper.orderByDesc(AssetInventory::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int startInventory(Long inventoryId) {
        LambdaUpdateWrapper<AssetInventory> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetInventory::getInventoryId, inventoryId);
        wrapper.set(AssetInventory::getStatus, "1"); // 进行中
        return update(wrapper) ? 1 : 0;
    }

    @Override
    @Transactional
    public int completeInventory(Long inventoryId) {
        LambdaUpdateWrapper<AssetInventory> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetInventory::getInventoryId, inventoryId);
        wrapper.set(AssetInventory::getStatus, "2"); // 已完成
        return update(wrapper) ? 1 : 0;
    }
}