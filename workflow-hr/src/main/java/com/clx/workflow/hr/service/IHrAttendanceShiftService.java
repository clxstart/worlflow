package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrAttendanceShift;

import java.util.List;

/**
 * 考勤排班 Service 接口
 *
 * @author clx
 */
public interface IHrAttendanceShiftService extends IService<HrAttendanceShift> {

    /**
     * 查询考勤排班列表
     */
    List<HrAttendanceShift> selectShiftList(HrAttendanceShift shift);
}