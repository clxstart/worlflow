package com.clx.workflow.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.hr.domain.HrLeaveType;

import java.util.List;

/**
 * 请假类型 Service 接口
 *
 * @author clx
 */
public interface IHrLeaveTypeService extends IService<HrLeaveType> {

    /**
     * 查询请假类型列表
     */
    List<HrLeaveType> selectLeaveTypeList(HrLeaveType leaveType);

    /**
     * 根据类型编码查询请假类型
     */
    HrLeaveType selectLeaveTypeByCode(String typeCode);
}