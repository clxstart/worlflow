package com.clx.workflow.finance.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.finance.domain.FinanceReimbursement;
import com.clx.workflow.finance.domain.FinanceReimbursementDetail;
import com.clx.workflow.finance.service.IFinanceReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报销申请 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/finance/reimbursement")
public class FinanceReimbursementController extends BaseController {

    @Autowired
    private IFinanceReimbursementService reimbursementService;

    /**
     * 查询报销申请列表
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:list')")
    @GetMapping("/list")
    public AjaxResult list(FinanceReimbursement reimbursement) {
        List<FinanceReimbursement> list = reimbursementService.selectReimbursementList(reimbursement);
        return success(list);
    }

    /**
     * 查询报销申请详情（包含明细）
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:query')")
    @GetMapping("/{reimbursementId}")
    public AjaxResult getInfo(@PathVariable Long reimbursementId) {
        FinanceReimbursement reimbursement = reimbursementService.getById(reimbursementId);
        List<FinanceReimbursementDetail> details = reimbursementService.selectDetailList(reimbursementId);
        return success().put("reimbursement", reimbursement).put("details", details);
    }

    /**
     * 查询报销明细
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:query')")
    @GetMapping("/detail/{reimbursementId}")
    public AjaxResult getDetails(@PathVariable Long reimbursementId) {
        return success(reimbursementService.selectDetailList(reimbursementId));
    }

    /**
     * 提交报销申请
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FinanceReimbursement reimbursement) {
        reimbursement.setUserId(getUserId());
        return toAjax(reimbursementService.save(reimbursement));
    }

    /**
     * 提交报销申请（包含明细）
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:add')")
    @PostMapping("/submit")
    public AjaxResult submit(@RequestBody ReimbursementVO vo) {
        vo.getReimbursement().setUserId(getUserId());
        return toAjax(reimbursementService.submitReimbursement(vo.getReimbursement(), vo.getDetails()));
    }

    /**
     * 修改报销申请
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FinanceReimbursement reimbursement) {
        return toAjax(reimbursementService.updateById(reimbursement));
    }

    /**
     * 审批报销
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:approve')")
    @PutMapping("/approve/{reimbursementId}")
    public AjaxResult approve(@PathVariable Long reimbursementId, @RequestParam String approveStatus) {
        return toAjax(reimbursementService.approveReimbursement(reimbursementId, approveStatus));
    }

    /**
     * 支付报销
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:pay')")
    @PutMapping("/pay/{reimbursementId}")
    public AjaxResult pay(@PathVariable Long reimbursementId) {
        return toAjax(reimbursementService.payReimbursement(reimbursementId));
    }

    /**
     * 删除报销申请
     */
    @PreAuthorize("@ss.hasPermi('finance:reimbursement:remove')")
    @DeleteMapping("/{reimbursementIds}")
    public AjaxResult remove(@PathVariable Long[] reimbursementIds) {
        return toAjax(reimbursementService.removeByIds(java.util.Arrays.asList(reimbursementIds)));
    }

    /**
     * 报销申请VO（用于接收前端提交的数据）
     */
    public static class ReimbursementVO {
        private FinanceReimbursement reimbursement;
        private List<FinanceReimbursementDetail> details;

        public FinanceReimbursement getReimbursement() {
            return reimbursement;
        }

        public void setReimbursement(FinanceReimbursement reimbursement) {
            this.reimbursement = reimbursement;
        }

        public List<FinanceReimbursementDetail> getDetails() {
            return details;
        }

        public void setDetails(List<FinanceReimbursementDetail> details) {
            this.details = details;
        }
    }
}