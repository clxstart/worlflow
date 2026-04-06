package com.clx.workflow.hr.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.hr.domain.HrSalary;
import com.clx.workflow.hr.service.IHrSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/hr/salary")
public class HrSalaryController extends BaseController {

    @Autowired
    private IHrSalaryService salaryService;

    /**
     * 查询薪资列表
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:list')")
    @GetMapping("/list")
    public AjaxResult list(HrSalary salary) {
        List<HrSalary> list = salaryService.selectSalaryList(salary);
        return success(list);
    }

    /**
     * 查询薪资详情
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:query')")
    @GetMapping("/{salaryId}")
    public AjaxResult getInfo(@PathVariable Long salaryId) {
        return success(salaryService.getById(salaryId));
    }

    /**
     * 根据用户和月份查询薪资
     */
    @GetMapping("/user/{userId}/month/{salaryMonth}")
    public AjaxResult getByUserAndMonth(@PathVariable Long userId, @PathVariable String salaryMonth) {
        return success(salaryService.selectSalaryByUserIdAndMonth(userId, salaryMonth));
    }

    /**
     * 新增薪资
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HrSalary salary) {
        salary.setStatus("0"); // 待发放
        return toAjax(salaryService.save(salary));
    }

    /**
     * 修改薪资
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HrSalary salary) {
        return toAjax(salaryService.updateById(salary));
    }

    /**
     * 发放薪资
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:pay')")
    @PutMapping("/pay/{salaryId}")
    public AjaxResult pay(@PathVariable Long salaryId) {
        HrSalary salary = salaryService.getById(salaryId);
        if (salary == null) {
            return error("薪资记录不存在");
        }
        salary.setStatus("1"); // 已发放
        salary.setPayTime(java.time.LocalDateTime.now());
        return toAjax(salaryService.updateById(salary));
    }

    /**
     * 删除薪资
     */
    @PreAuthorize("@ss.hasPermi('hr:salary:remove')")
    @DeleteMapping("/{salaryIds}")
    public AjaxResult remove(@PathVariable Long[] salaryIds) {
        return toAjax(salaryService.removeByIds(java.util.Arrays.asList(salaryIds)));
    }
}