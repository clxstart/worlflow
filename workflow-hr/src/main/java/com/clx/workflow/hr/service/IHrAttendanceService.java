package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrAttendance;

import java.util.List;

/**
 * 考勤记录 Service 接口
 *
 * @author clx
 */
public interface IHrAttendanceService extends IService<HrAttendance> {

    /**
     * 查询考勤记录列表
     */
    List<HrAttendance> selectAttendanceList(HrAttendance attendance);

    /**
     * 根据用户ID和日期查询考勤记录
     */
    HrAttendance selectAttendanceByUserIdAndDate(Long userId, String attendanceDate);
}