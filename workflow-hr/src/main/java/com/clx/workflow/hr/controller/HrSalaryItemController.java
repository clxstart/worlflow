package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrSalaryItem;
import com.clx.workflow.hr.service.IHrSalaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资项 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/salaryItem")
public class HrSalaryItemController extends BaseController {

    @Autowired
    private IHrSalaryItemService salaryItemService;

    /**
     * 根据薪资ID查询薪资项列表
     */
    @GetMapping("/list/{salaryId}")
    public AjaxResult list(@PathVariable Long salaryId) {
        List<HrSalaryItem> list = salaryItemService.selectSalaryItemsBySalaryId(salaryId);
        return success(list);
    }

    /**
     * 查询薪资项详情
     */
    @GetMapping("/{itemId}")
    public AjaxResult getInfo(@PathVariable Long itemId) {
        return success(salaryItemService.getById(itemId));
    }

    /**
     * 新增薪资项
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrSalaryItem salaryItem) {
        return toAjax(salaryItemService.save(salaryItem));
    }

    /**
     * 修改薪资项
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrSalaryItem salaryItem) {
        return toAjax(salaryItemService.updateById(salaryItem));
    }

    /**
     * 删除薪资项
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:remove')")
    @DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds) {
        return toAjax(salaryItemService.removeByIds(java.util.Arrays.asList(itemIds)));
    }
}