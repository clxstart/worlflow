package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrLeaveType;
import com.clx.workflow.hr.mapper.HrLeaveTypeMapper;
import com.clx.workflow.hr.service.IHrLeaveTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请假类型 Service 实现类
 *
 * @author clx
 */
@Service
public class HrLeaveTypeServiceImpl extends ServiceImpl<HrLeaveTypeMapper, HrLeaveType> implements IHrLeaveTypeService {

    @Override
    public List<HrLeaveType> selectLeaveTypeList(HrLeaveType leaveType) {
        LambdaQueryWrapper<HrLeaveType> wrapper = new LambdaQueryWrapper<>();
        if (leaveType.getTypeName() != null && !leaveType.getTypeName().isEmpty()) {
            wrapper.like(HrLeaveType::getTypeName, leaveType.getTypeName());
        }
        if (leaveType.getTypeCode() != null && !leaveType.getTypeCode().isEmpty()) {
            wrapper.eq(HrLeaveType::getTypeCode, leaveType.getTypeCode());
        }
        if (leaveType.getStatus() != null && !leaveType.getStatus().isEmpty()) {
            wrapper.eq(HrLeaveType::getStatus, leaveType.getStatus());
        }
        wrapper.orderByAsc(HrLeaveType::getTypeId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public HrLeaveType selectLeaveTypeByCode(String typeCode) {
        LambdaQueryWrapper<HrLeaveType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HrLeaveType::getTypeCode, typeCode);
        return baseMapper.selectOne(wrapper);
    }
}