package com.clx.workflow.oa.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import com.clx.workflow.oa.domain.OaLeaveApply;
import com.clx.workflow.oa.service.IOaLeaveApplyService;
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
@RequestMapping("/oa/leave")
public class OaLeaveApplyController extends BaseController {

    @Autowired
    private IOaLeaveApplyService leaveApplyService;

    /**
     * 查询请假申请列表
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:list')")
    @GetMapping("/list")
    public AjaxResult list(OaLeaveApply apply) {
        List<OaLeaveApply> list = leaveApplyService.selectLeaveApplyList(apply);
        return success(list);
    }

    /**
     * 查询我的请假申请
     */
    @GetMapping("/my")
    public AjaxResult myList() {
        Long userId = SecurityUtils.getUserId();
        List<OaLeaveApply> list = leaveApplyService.selectMyLeaveApply(userId);
        return success(list);
    }

    /**
     * 查询请假申请详情
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(leaveApplyService.getById(id));
    }

    /**
     * 新增请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody OaLeaveApply apply) {
        apply.setUserId(SecurityUtils.getUserId());
        apply.setUserName(SecurityUtils.getUsername());
        apply.setDeptId(SecurityUtils.getDeptId());
        apply.setStatus("draft"); // 草稿状态
        return toAjax(leaveApplyService.save(apply));
    }

    /**
     * 修改请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody OaLeaveApply apply) {
        // 只有草稿状态才能修改
        OaLeaveApply existing = leaveApplyService.getById(apply.getId());
        if (existing != null && !"draft".equals(existing.getStatus())) {
            return error("只有草稿状态的申请才能修改");
        }
        return toAjax(leaveApplyService.updateById(apply));
    }

    /**
     * 删除请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        // 只有草稿状态才能删除
        OaLeaveApply existing = leaveApplyService.getById(id);
        if (existing != null && !"draft".equals(existing.getStatus())) {
            return error("只有草稿状态的申请才能删除");
        }
        return toAjax(leaveApplyService.removeById(id));
    }

    /**
     * 提交审批
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:submit')")
    @PostMapping("/submit/{id}")
    public AjaxResult submit(@PathVariable Long id) {
        return toAjax(leaveApplyService.submitApply(id));
    }

    /**
     * 撤销申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:cancel')")
    @PutMapping("/cancel/{id}")
    public AjaxResult cancel(@PathVariable Long id) {
        return toAjax(leaveApplyService.cancelApply(id));
    }
}