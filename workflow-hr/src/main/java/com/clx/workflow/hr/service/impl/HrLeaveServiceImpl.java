package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrLeave;
import com.clx.workflow.hr.mapper.HrLeaveMapper;
import com.clx.workflow.hr.service.IHrLeaveService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 请假申请 Service 实现类
 *
 * @author clx
 */
@Service
public class HrLeaveServiceImpl extends ServiceImpl<HrLeaveMapper, HrLeave> implements IHrLeaveService {

    @Override
    public List<HrLeave> selectLeaveList(HrLeave leave) {
        LambdaQueryWrapper<HrLeave> wrapper = new LambdaQueryWrapper<>();
        if (leave.getUserId() != null) {
            wrapper.eq(HrLeave::getUserId, leave.getUserId());
        }
        if (leave.getUserName() != null && !leave.getUserName().isEmpty()) {
            wrapper.like(HrLeave::getUserName, leave.getUserName());
        }
        if (leave.getDeptId() != null) {
            wrapper.eq(HrLeave::getDeptId, leave.getDeptId());
        }
        if (leave.getTypeId() != null) {
            wrapper.eq(HrLeave::getTypeId, leave.getTypeId());
        }
        if (leave.getStatus() != null && !leave.getStatus().isEmpty()) {
            wrapper.eq(HrLeave::getStatus, leave.getStatus());
        }
        wrapper.orderByDesc(HrLeave::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean approveLeave(HrLeave leave) {
        HrLeave entity = this.getById(leave.getLeaveId());
        if (entity == null) {
            return false;
        }
        entity.setStatus(leave.getStatus());
        entity.setApproveUserId(leave.getApproveUserId());
        entity.setApproveUserName(leave.getApproveUserName());
        entity.setApproveTime(LocalDateTime.now());
        entity.setApproveRemark(leave.getApproveRemark());
        return this.updateById(entity);
    }

    @Override
    public boolean cancelLeave(Long leaveId) {
        HrLeave entity = this.getById(leaveId);
        if (entity == null || !"0".equals(entity.getStatus())) {
            return false;
        }
        entity.setStatus("3");
        return this.updateById(entity);
    }
}