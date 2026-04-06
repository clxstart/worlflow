package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrSalaryItem;

import java.util.List;

/**
 * 薪资项 Service 接口
 *
 * @author clx
 */
public interface IHrSalaryItemService extends IService<HrSalaryItem> {

    /**
     * 根据薪资ID查询薪资项列表
     */
    List<HrSalaryItem> selectSalaryItemsBySalaryId(Long salaryId);
}