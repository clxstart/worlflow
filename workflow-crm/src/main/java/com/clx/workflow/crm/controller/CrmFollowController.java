package com.clx.workflow.crm.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.crm.domain.CrmFollow;
import com.clx.workflow.crm.service.ICrmFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 跟进记录 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/crm/follow")
public class CrmFollowController extends BaseController {

    @Autowired
    private ICrmFollowService followService;

    /**
     * 查询跟进记录列表
     */
    @PreAuthorize("@ss.hasPermi('crm:follow:list')")
    @GetMapping("/list")
    public AjaxResult list(CrmFollow follow) {
        List<CrmFollow> list = followService.selectFollowList(follow);
        return success(list);
    }

    /**
     * 查询客户的跟进记录
     */
    @PreAuthorize("@ss.hasPermi('crm:follow:list')")
    @GetMapping("/customer/{customerId}")
    public AjaxResult getByCustomer(@PathVariable Long customerId) {
        List<CrmFollow> list = followService.selectFollowsByCustomerId(customerId);
        return success(list);
    }

    /**
     * 查询跟进记录详情
     */
    @PreAuthorize("@ss.hasPermi('crm:follow:query')")
    @GetMapping("/{followId}")
    public AjaxResult getInfo(@PathVariable Long followId) {
        return success(followService.getById(followId));
    }

    /**
     * 新增跟进记录
     */
    @PreAuthorize("@ss.hasPermi('crm:follow:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CrmFollow follow) {
        follow.setUserId(getUserId());
        return toAjax(followService.save(follow));
    }

    /**
     * 修改跟进记录
     */
    @PreAuthorize("@ss.hasPermi('crm:follow:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody CrmFollow follow) {
        return toAjax(followService.updateById(follow));
    }

    /**
     * 删除跟进记录
     */
    @PreAuthorize("@ss.hasPermi('crm:follow:remove')")
    @DeleteMapping("/{followIds}")
    public AjaxResult remove(@PathVariable Long[] followIds) {
        return toAjax(followService.removeByIds(java.util.Arrays.asList(followIds)));
    }
}