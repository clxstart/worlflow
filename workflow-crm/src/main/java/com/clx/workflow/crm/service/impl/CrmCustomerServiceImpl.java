package com.clx.workflow.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.crm.domain.CrmCustomer;
import com.clx.workflow.crm.mapper.CrmCustomerMapper;
import com.clx.workflow.crm.service.ICrmCustomerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户信息 Service 实现类
 *
 * @author clx
 */
@Service
public class CrmCustomerServiceImpl extends ServiceImpl<CrmCustomerMapper, CrmCustomer> implements ICrmCustomerService {

    @Override
    public List<CrmCustomer> selectCustomerList(CrmCustomer customer) {
        LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
        if (customer.getCustomerNo() != null && !customer.getCustomerNo().isEmpty()) {
            wrapper.like(CrmCustomer::getCustomerNo, customer.getCustomerNo());
        }
        if (customer.getCustomerName() != null && !customer.getCustomerName().isEmpty()) {
            wrapper.like(CrmCustomer::getCustomerName, customer.getCustomerName());
        }
        if (customer.getCustomerType() != null && !customer.getCustomerType().isEmpty()) {
            wrapper.eq(CrmCustomer::getCustomerType, customer.getCustomerType());
        }
        if (customer.getIndustry() != null && !customer.getIndustry().isEmpty()) {
            wrapper.eq(CrmCustomer::getIndustry, customer.getIndustry());
        }
        if (customer.getLevel() != null && !customer.getLevel().isEmpty()) {
            wrapper.eq(CrmCustomer::getLevel, customer.getLevel());
        }
        if (customer.getStatus() != null && !customer.getStatus().isEmpty()) {
            wrapper.eq(CrmCustomer::getStatus, customer.getStatus());
        }
        if (customer.getOwnerId() != null) {
            wrapper.eq(CrmCustomer::getOwnerId, customer.getOwnerId());
        }
        if (customer.getProvince() != null && !customer.getProvince().isEmpty()) {
            wrapper.eq(CrmCustomer::getProvince, customer.getProvince());
        }
        if (customer.getCity() != null && !customer.getCity().isEmpty()) {
            wrapper.eq(CrmCustomer::getCity, customer.getCity());
        }
        wrapper.orderByDesc(CrmCustomer::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CrmCustomer> selectMyCustomers(Long ownerId) {
        LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmCustomer::getOwnerId, ownerId);
        wrapper.orderByDesc(CrmCustomer::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean transferCustomer(Long customerId, Long newOwnerId, String newOwnerName) {
        LambdaUpdateWrapper<CrmCustomer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmCustomer::getCustomerId, customerId);
        wrapper.set(CrmCustomer::getOwnerId, newOwnerId);
        wrapper.set(CrmCustomer::getOwnerName, newOwnerName);
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public Map<String, Object> customerStatistics(Long ownerId) {
        Map<String, Object> result = new HashMap<>();

        LambdaQueryWrapper<CrmCustomer> baseWrapper = new LambdaQueryWrapper<>();
        if (ownerId != null) {
            baseWrapper.eq(CrmCustomer::getOwnerId, ownerId);
        }

        // 总客户数
        result.put("total", baseMapper.selectCount(baseWrapper));

        // 按状态统计
        Map<String, Long> statusCount = new HashMap<>();
        for (int i = 0; i <= 3; i++) {
            LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
            if (ownerId != null) {
                wrapper.eq(CrmCustomer::getOwnerId, ownerId);
            }
            wrapper.eq(CrmCustomer::getStatus, String.valueOf(i));
            statusCount.put(String.valueOf(i), baseMapper.selectCount(wrapper));
        }
        result.put("statusCount", statusCount);

        // 按等级统计
        Map<String, Long> levelCount = new HashMap<>();
        for (String level : new String[]{"A", "B", "C", "D"}) {
            LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
            if (ownerId != null) {
                wrapper.eq(CrmCustomer::getOwnerId, ownerId);
            }
            wrapper.eq(CrmCustomer::getLevel, level);
            levelCount.put(level, baseMapper.selectCount(wrapper));
        }
        result.put("levelCount", levelCount);

        return result;
    }
}