package com.clx.workflow.crm.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.crm.domain.CrmContact;
import com.clx.workflow.crm.service.ICrmContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 联系人 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/crm/contact")
public class CrmContactController extends BaseController {

    @Autowired
    private ICrmContactService contactService;

    /**
     * 查询联系人列表
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:list')")
    @GetMapping("/list")
    public AjaxResult list(CrmContact contact) {
        List<CrmContact> list = contactService.selectContactList(contact);
        return success(list);
    }

    /**
     * 查询客户的联系人
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:list')")
    @GetMapping("/customer/{customerId}")
    public AjaxResult getByCustomer(@PathVariable Long customerId) {
        List<CrmContact> list = contactService.selectContactsByCustomerId(customerId);
        return success(list);
    }

    /**
     * 查询联系人详情
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:query')")
    @GetMapping("/{contactId}")
    public AjaxResult getInfo(@PathVariable Long contactId) {
        return success(contactService.getById(contactId));
    }

    /**
     * 新增联系人
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CrmContact contact) {
        return toAjax(contactService.save(contact));
    }

    /**
     * 修改联系人
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody CrmContact contact) {
        return toAjax(contactService.updateById(contact));
    }

    /**
     * 设置主要联系人
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:edit')")
    @PutMapping("/primary/{contactId}")
    public AjaxResult setPrimary(@PathVariable Long contactId) {
        return toAjax(contactService.setPrimaryContact(contactId));
    }

    /**
     * 删除联系人
     */
    @PreAuthorize("@ss.hasPermi('crm:contact:remove')")
    @DeleteMapping("/{contactIds}")
    public AjaxResult remove(@PathVariable Long[] contactIds) {
        return toAjax(contactService.removeByIds(java.util.Arrays.asList(contactIds)));
    }
}