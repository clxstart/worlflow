package com.clx.workflow.crm.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.crm.domain.CrmCustomer;
import com.clx.workflow.crm.service.ICrmCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客户信息 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/crm/customer")
public class CrmCustomerController extends BaseController {

    @Autowired
    private ICrmCustomerService customerService;

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:list')")
    @GetMapping("/list")
    public AjaxResult list(CrmCustomer customer) {
        List<CrmCustomer> list = customerService.selectCustomerList(customer);
        return success(list);
    }

    /**
     * 查询我的客户
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:list')")
    @GetMapping("/my")
    public AjaxResult myCustomers() {
        List<CrmCustomer> list = customerService.selectMyCustomers(getUserId());
        return success(list);
    }

    /**
     * 查询客户详情
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:query')")
    @GetMapping("/{customerId}")
    public AjaxResult getInfo(@PathVariable Long customerId) {
        return success(customerService.getById(customerId));
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CrmCustomer customer) {
        customer.setOwnerId(getUserId());
        return toAjax(customerService.save(customer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody CrmCustomer customer) {
        return toAjax(customerService.updateById(customer));
    }

    /**
     * 转移客户
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:transfer')")
    @PutMapping("/transfer/{customerId}")
    public AjaxResult transfer(@PathVariable Long customerId,
                               @RequestParam Long newOwnerId,
                               @RequestParam String newOwnerName) {
        return toAjax(customerService.transferCustomer(customerId, newOwnerId, newOwnerName));
    }

    /**
     * 更新客户状态
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:edit')")
    @PutMapping("/status/{customerId}")
    public AjaxResult updateStatus(@PathVariable Long customerId, @RequestParam String status) {
        CrmCustomer customer = customerService.getById(customerId);
        customer.setStatus(status);
        return toAjax(customerService.updateById(customer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:remove')")
    @DeleteMapping("/{customerIds}")
    public AjaxResult remove(@PathVariable Long[] customerIds) {
        return toAjax(customerService.removeByIds(java.util.Arrays.asList(customerIds)));
    }

    /**
     * 客户统计
     */
    @PreAuthorize("@ss.hasPermi('crm:customer:query')")
    @GetMapping("/statistics")
    public AjaxResult statistics() {
        Map<String, Object> stats = customerService.customerStatistics(getUserId());
        return success(stats);
    }
}