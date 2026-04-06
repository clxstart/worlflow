package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrLeave;

import java.util.List;

/**
 * 请假申请 Service 接口
 *
 * @author clx
 */
public interface IHrLeaveService extends IService<HrLeave> {

    /**
     * 查询请假申请列表
     */
    List<HrLeave> selectLeaveList(HrLeave leave);

    /**
     * 审批请假申请
     */
    boolean approveLeave(HrLeave leave);

    /**
     * 撤销请假申请
     */
    boolean cancelLeave(Long leaveId);
}