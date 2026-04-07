package com.clx.workflow.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.crm.domain.CrmFollow;
import com.clx.workflow.crm.mapper.CrmFollowMapper;
import com.clx.workflow.crm.service.ICrmFollowService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跟进记录 Service 实现类
 *
 * @author clx
 */
@Service
public class CrmFollowServiceImpl extends ServiceImpl<CrmFollowMapper, CrmFollow> implements ICrmFollowService {

    @Override
    public List<CrmFollow> selectFollowList(CrmFollow follow) {
        LambdaQueryWrapper<CrmFollow> wrapper = new LambdaQueryWrapper<>();
        if (follow.getCustomerId() != null) {
            wrapper.eq(CrmFollow::getCustomerId, follow.getCustomerId());
        }
        if (follow.getUserId() != null) {
            wrapper.eq(CrmFollow::getUserId, follow.getUserId());
        }
        if (follow.getFollowType() != null && !follow.getFollowType().isEmpty()) {
            wrapper.eq(CrmFollow::getFollowType, follow.getFollowType());
        }
        if (follow.getFollowStage() != null && !follow.getFollowStage().isEmpty()) {
            wrapper.eq(CrmFollow::getFollowStage, follow.getFollowStage());
        }
        wrapper.orderByDesc(CrmFollow::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CrmFollow> selectFollowsByCustomerId(Long customerId) {
        LambdaQueryWrapper<CrmFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmFollow::getCustomerId, customerId);
        wrapper.orderByDesc(CrmFollow::getCreateTime);
        return baseMapper.selectList(wrapper);
    }
}