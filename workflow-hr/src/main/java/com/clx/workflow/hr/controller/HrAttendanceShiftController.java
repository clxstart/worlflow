package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrAttendanceShift;
import com.clx.workflow.hr.service.IHrAttendanceShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤排班 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/shift")
public class HrAttendanceShiftController extends BaseController {

    @Autowired
    private IHrAttendanceShiftService shiftService;

    /**
     * 查询考勤排班列表
     */
    @PreAuthorize("@ss.hasPermi('hr:shift:list')")
    @GetMapping("/list")
    public AjaxResult list(HrAttendanceShift shift) {
        List<HrAttendanceShift> list = shiftService.selectShiftList(shift);
        return success(list);
    }

    /**
     * 查询考勤排班详情
     */
    @PreAuthorize("@ss.hasPermi('hr:shift:query')")
    @GetMapping("/{shiftId}")
    public AjaxResult getInfo(@PathVariable Long shiftId) {
        return success(shiftService.getById(shiftId));
    }

    /**
     * 新增考勤排班
     */
    @PreAuthorize("@ss.hasPermi('hr:shift:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrAttendanceShift shift) {
        return toAjax(shiftService.save(shift));
    }

    /**
     * 修改考勤排班
     */
    @PreAuthorize("@ss.hasPermi('hr:shift:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrAttendanceShift shift) {
        return toAjax(shiftService.updateById(shift));
    }

    /**
     * 删除考勤排班
     */
    @PreAuthorize("@ss.hasPermi('hr:shift:remove')")
    @DeleteMapping("/{shiftIds}")
    public AjaxResult remove(@PathVariable Long[] shiftIds) {
        return toAjax(shiftService.removeByIds(java.util.Arrays.asList(shiftIds)));
    }
}