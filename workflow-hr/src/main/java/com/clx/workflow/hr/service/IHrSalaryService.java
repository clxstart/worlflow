package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrSalary;

import java.util.List;

/**
 * 薪资 Service 接口
 *
 * @author clx
 */
public interface IHrSalaryService extends IService<HrSalary> {

    /**
     * 查询薪资列表
     */
    List<HrSalary> selectSalaryList(HrSalary salary);

    /**
     * 根据用户ID和月份查询薪资
     */
    HrSalary selectSalaryByUserIdAndMonth(Long userId, String salaryMonth);
}