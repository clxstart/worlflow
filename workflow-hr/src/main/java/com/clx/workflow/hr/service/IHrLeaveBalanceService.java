package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrLeaveBalance;

import java.util.List;

/**
 * 假期余额 Service 接口
 *
 * @author clx
 */
public interface IHrLeaveBalanceService extends IService<HrLeaveBalance> {

    /**
     * 查询假期余额列表
     */
    List<HrLeaveBalance> selectLeaveBalanceList(HrLeaveBalance leaveBalance);

    /**
     * 根据用户ID和类型ID查询假期余额
     */
    HrLeaveBalance selectLeaveBalanceByUserIdAndTypeId(Long userId, Long typeId, Integer year);
}