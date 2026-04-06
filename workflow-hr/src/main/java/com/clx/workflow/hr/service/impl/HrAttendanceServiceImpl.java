package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrAttendance;
import com.clx.workflow.hr.mapper.HrAttendanceMapper;
import com.clx.workflow.hr.service.IHrAttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 考勤记录 Service 实现类
 *
 * @author clx
 */
@Service
public class HrAttendanceServiceImpl extends ServiceImpl<HrAttendanceMapper, HrAttendance> implements IHrAttendanceService {

    @Override
    public List<HrAttendance> selectAttendanceList(HrAttendance attendance) {
        LambdaQueryWrapper<HrAttendance> wrapper = new LambdaQueryWrapper<>();
        if (attendance.getUserId() != null) {
            wrapper.eq(HrAttendance::getUserId, attendance.getUserId());
        }
        if (attendance.getUserName() != null && !attendance.getUserName().isEmpty()) {
            wrapper.like(HrAttendance::getUserName, attendance.getUserName());
        }
        if (attendance.getDeptId() != null) {
            wrapper.eq(HrAttendance::getDeptId, attendance.getDeptId());
        }
        if (attendance.getAttendanceDate() != null) {
            wrapper.eq(HrAttendance::getAttendanceDate, attendance.getAttendanceDate());
        }
        if (attendance.getStatus() != null && !attendance.getStatus().isEmpty()) {
            wrapper.eq(HrAttendance::getStatus, attendance.getStatus());
        }
        wrapper.orderByDesc(HrAttendance::getAttendanceDate);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public HrAttendance selectAttendanceByUserIdAndDate(Long userId, String attendanceDate) {
        LambdaQueryWrapper<HrAttendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HrAttendance::getUserId, userId);
        wrapper.eq(HrAttendance::getAttendanceDate, LocalDate.parse(attendanceDate));
        return baseMapper.selectOne(wrapper);
    }
}