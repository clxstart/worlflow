package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrLeaveType;
import com.clx.workflow.hr.service.IHrLeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请假类型 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/leaveType")
public class HrLeaveTypeController extends BaseController {

    @Autowired
    private IHrLeaveTypeService leaveTypeService;

    /**
     * 查询请假类型列表
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveType:list')")
    @GetMapping("/list")
    public AjaxResult list(HrLeaveType leaveType) {
        List<HrLeaveType> list = leaveTypeService.selectLeaveTypeList(leaveType);
        return success(list);
    }

    /**
     * 查询请假类型详情
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveType:query')")
    @GetMapping("/{typeId}")
    public AjaxResult getInfo(@PathVariable Long typeId) {
        return success(leaveTypeService.getById(typeId));
    }

    /**
     * 根据类型编码查询请假类型
     */
    @GetMapping("/code/{typeCode}")
    public AjaxResult getByCode(@PathVariable String typeCode) {
        return success(leaveTypeService.selectLeaveTypeByCode(typeCode));
    }

    /**
     * 新增请假类型
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveType:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrLeaveType leaveType) {
        return toAjax(leaveTypeService.save(leaveType));
    }

    /**
     * 修改请假类型
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveType:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrLeaveType leaveType) {
        return toAjax(leaveTypeService.updateById(leaveType));
    }

    /**
     * 删除请假类型
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveType:remove')")
    @DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds) {
        return toAjax(leaveTypeService.removeByIds(java.util.Arrays.asList(typeIds)));
    }
}