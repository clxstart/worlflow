package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrAttendance;
import com.clx.workflow.hr.service.IHrAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤记录 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/attendance")
public class HrAttendanceController extends BaseController {

    @Autowired
    private IHrAttendanceService attendanceService;

    /**
     * 查询考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('hr:attendance:list')")
    @GetMapping("/list")
    public AjaxResult list(HrAttendance attendance) {
        List<HrAttendance> list = attendanceService.selectAttendanceList(attendance);
        return success(list);
    }

    /**
     * 查询考勤记录详情
     */
    @PreAuthorize("@ss.hasPermi('hr:attendance:query')")
    @GetMapping("/{attendanceId}")
    public AjaxResult getInfo(@PathVariable Long attendanceId) {
        return success(attendanceService.getById(attendanceId));
    }

    /**
     * 根据用户和日期查询考勤记录
     */
    @PreAuthorize("@ss.hasPermi('hr:attendance:query')")
    @GetMapping("/user/{userId}/date/{attendanceDate}")
    public AjaxResult getByUserAndDate(@PathVariable Long userId, @PathVariable String attendanceDate) {
        return success(attendanceService.selectAttendanceByUserIdAndDate(userId, attendanceDate));
    }

    /**
     * 新增考勤记录（打卡）
     */
    @PreAuthorize("@ss.hasPermi('hr:attendance:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrAttendance attendance) {
        return toAjax(attendanceService.save(attendance));
    }

    /**
     * 修改考勤记录
     */
    @PreAuthorize("@ss.hasPermi('hr:attendance:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrAttendance attendance) {
        return toAjax(attendanceService.updateById(attendance));
    }

    /**
     * 删除考勤记录
     */
    @PreAuthorize("@ss.hasPermi('hr:attendance:remove')")
    @DeleteMapping("/{attendanceIds}")
    public AjaxResult remove(@PathVariable Long[] attendanceIds) {
        return toAjax(attendanceService.removeByIds(java.util.Arrays.asList(attendanceIds)));
    }
}