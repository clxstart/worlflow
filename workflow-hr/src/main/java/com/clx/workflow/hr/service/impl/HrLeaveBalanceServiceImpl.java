package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrLeaveBalance;
import com.clx.workflow.hr.mapper.HrLeaveBalanceMapper;
import com.clx.workflow.hr.service.IHrLeaveBalanceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 假期余额 Service 实现类
 *
 * @author clx
 */
@Service
public class HrLeaveBalanceServiceImpl extends ServiceImpl<HrLeaveBalanceMapper, HrLeaveBalance> implements IHrLeaveBalanceService {

    @Override
    public List<HrLeaveBalance> selectLeaveBalanceList(HrLeaveBalance leaveBalance) {
        LambdaQueryWrapper<HrLeaveBalance> wrapper = new LambdaQueryWrapper<>();
        if (leaveBalance.getUserId() != null) {
            wrapper.eq(HrLeaveBalance::getUserId, leaveBalance.getUserId());
        }
        if (leaveBalance.getTypeId() != null) {
            wrapper.eq(HrLeaveBalance::getTypeId, leaveBalance.getTypeId());
        }
        if (leaveBalance.getYear() != null) {
            wrapper.eq(HrLeaveBalance::getYear, leaveBalance.getYear());
        }
        wrapper.orderByDesc(HrLeaveBalance::getYear);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public HrLeaveBalance selectLeaveBalanceByUserIdAndTypeId(Long userId, Long typeId, Integer year) {
        LambdaQueryWrapper<HrLeaveBalance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HrLeaveBalance::getUserId, userId);
        wrapper.eq(HrLeaveBalance::getTypeId, typeId);
        wrapper.eq(HrLeaveBalance::getYear, year);
        return baseMapper.selectOne(wrapper);
    }
}