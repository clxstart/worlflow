package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrLeave;
import com.clx.workflow.hr.service.IHrLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请假申请 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/leave")
public class HrLeaveController extends BaseController {

    @Autowired
    private IHrLeaveService leaveService;

    /**
     * 查询请假申请列表
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:list')")
    @GetMapping("/list")
    public AjaxResult list(HrLeave leave) {
        List<HrLeave> list = leaveService.selectLeaveList(leave);
        return success(list);
    }

    /**
     * 查询请假申请详情
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:query')")
    @GetMapping("/{leaveId}")
    public AjaxResult getInfo(@PathVariable Long leaveId) {
        return success(leaveService.getById(leaveId));
    }

    /**
     * 新增请假申请
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrLeave leave) {
        leave.setStatus("0"); // 待审批
        return toAjax(leaveService.save(leave));
    }

    /**
     * 修改请假申请
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrLeave leave) {
        return toAjax(leaveService.updateById(leave));
    }

    /**
     * 审批请假申请
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:approve')")
    @PutMapping("/approve")
    public AjaxResult approve(@RequestBody HrLeave leave) {
        return toAjax(leaveService.approveLeave(leave));
    }

    /**
     * 撤销请假申请
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:cancel')")
    @PutMapping("/cancel/{leaveId}")
    public AjaxResult cancel(@PathVariable Long leaveId) {
        return toAjax(leaveService.cancelLeave(leaveId));
    }

    /**
     * 删除请假申请
     */
    @PreAuthorize("@ss.hasPermi('hr:leave:remove')")
    @DeleteMapping("/{leaveIds}")
    public AjaxResult remove(@PathVariable Long[] leaveIds) {
        return toAjax(leaveService.removeByIds(java.util.Arrays.asList(leaveIds)));
    }
}