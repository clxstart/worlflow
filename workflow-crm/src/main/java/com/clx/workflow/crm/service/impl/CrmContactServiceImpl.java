package com.clx.workflow.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.crm.domain.CrmContact;
import com.clx.workflow.crm.mapper.CrmContactMapper;
import com.clx.workflow.crm.service.ICrmContactService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 联系人 Service 实现类
 *
 * @author clx
 */
@Service
public class CrmContactServiceImpl extends ServiceImpl<CrmContactMapper, CrmContact> implements ICrmContactService {

    @Override
    public List<CrmContact> selectContactList(CrmContact contact) {
        LambdaQueryWrapper<CrmContact> wrapper = new LambdaQueryWrapper<>();
        if (contact.getCustomerId() != null) {
            wrapper.eq(CrmContact::getCustomerId, contact.getCustomerId());
        }
        if (contact.getContactName() != null && !contact.getContactName().isEmpty()) {
            wrapper.like(CrmContact::getContactName, contact.getContactName());
        }
        if (contact.getPhone() != null && !contact.getPhone().isEmpty()) {
            wrapper.like(CrmContact::getPhone, contact.getPhone());
        }
        wrapper.orderByDesc(CrmContact::getIsPrimary);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CrmContact> selectContactsByCustomerId(Long customerId) {
        LambdaQueryWrapper<CrmContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmContact::getCustomerId, customerId);
        wrapper.orderByDesc(CrmContact::getIsPrimary);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean setPrimaryContact(Long contactId) {
        CrmContact contact = getById(contactId);
        if (contact == null) {
            return false;
        }

        // 先取消该客户的其他主要联系人
        LambdaUpdateWrapper<CrmContact> clearWrapper = new LambdaUpdateWrapper<>();
        clearWrapper.eq(CrmContact::getCustomerId, contact.getCustomerId());
        clearWrapper.set(CrmContact::getIsPrimary, "0");
        baseMapper.update(null, clearWrapper);

        // 设置新的主要联系人
        LambdaUpdateWrapper<CrmContact> setWrapper = new LambdaUpdateWrapper<>();
        setWrapper.eq(CrmContact::getContactId, contactId);
        setWrapper.set(CrmContact::getIsPrimary, "1");
        return baseMapper.update(null, setWrapper) > 0;
    }
}