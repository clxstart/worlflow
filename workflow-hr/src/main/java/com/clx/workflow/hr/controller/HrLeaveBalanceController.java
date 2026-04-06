package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrLeaveBalance;
import com.clx.workflow.hr.service.IHrLeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 假期余额 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/leaveBalance")
public class HrLeaveBalanceController extends BaseController {

    @Autowired
    private IHrLeaveBalanceService leaveBalanceService;

    /**
     * 查询假期余额列表
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveBalance:list')")
    @GetMapping("/list")
    public AjaxResult list(HrLeaveBalance leaveBalance) {
        List<HrLeaveBalance> list = leaveBalanceService.selectLeaveBalanceList(leaveBalance);
        return success(list);
    }

    /**
     * 查询假期余额详情
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveBalance:query')")
    @GetMapping("/{balanceId}")
    public AjaxResult getInfo(@PathVariable Long balanceId) {
        return success(leaveBalanceService.getById(balanceId));
    }

    /**
     * 根据用户和类型查询假期余额
     */
    @GetMapping("/user/{userId}/type/{typeId}/year/{year}")
    public AjaxResult getByUserAndType(@PathVariable Long userId, @PathVariable Long typeId, @PathVariable Integer year) {
        return success(leaveBalanceService.selectLeaveBalanceByUserIdAndTypeId(userId, typeId, year));
    }

    /**
     * 新增假期余额
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveBalance:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrLeaveBalance leaveBalance) {
        return toAjax(leaveBalanceService.save(leaveBalance));
    }

    /**
     * 修改假期余额
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveBalance:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrLeaveBalance leaveBalance) {
        return toAjax(leaveBalanceService.updateById(leaveBalance));
    }

    /**
     * 删除假期余额
     */
    @PreAuthorize("@ss.hasPermi('hr:leaveBalance:remove')")
    @DeleteMapping("/{balanceIds}")
    public AjaxResult remove(@PathVariable Long[] balanceIds) {
        return toAjax(leaveBalanceService.removeByIds(java.util.Arrays.asList(balanceIds)));
    }
}