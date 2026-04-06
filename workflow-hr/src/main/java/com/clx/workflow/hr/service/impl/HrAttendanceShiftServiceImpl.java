package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrAttendanceShift;
import com.clx.workflow.hr.mapper.HrAttendanceShiftMapper;
import com.clx.workflow.hr.service.IHrAttendanceShiftService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考勤排班 Service 实现类
 *
 * @author clx
 */
@Service
public class HrAttendanceShiftServiceImpl extends ServiceImpl<HrAttendanceShiftMapper, HrAttendanceShift> implements IHrAttendanceShiftService {

    @Override
    public List<HrAttendanceShift> selectShiftList(HrAttendanceShift shift) {
        LambdaQueryWrapper<HrAttendanceShift> wrapper = new LambdaQueryWrapper<>();
        if (shift.getShiftName() != null && !shift.getShiftName().isEmpty()) {
            wrapper.like(HrAttendanceShift::getShiftName, shift.getShiftName());
        }
        if (shift.getStatus() != null && !shift.getStatus().isEmpty()) {
            wrapper.eq(HrAttendanceShift::getStatus, shift.getStatus());
        }
        wrapper.orderByAsc(HrAttendanceShift::getShiftId);
        return baseMapper.selectList(wrapper);
    }
}