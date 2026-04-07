package com.clx.workflow.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.common.utils.SecurityUtils;
// import com.clx.workflow.flowable.service.IWfProcessInstanceService;
import com.clx.workflow.hr.domain.HrLeaveType;
import com.clx.workflow.hr.service.IHrLeaveTypeService;
import com.clx.workflow.oa.domain.OaLeaveApply;
import com.clx.workflow.oa.mapper.OaLeaveApplyMapper;
import com.clx.workflow.oa.service.IOaLeaveApplyService;
import com.clx.workflow.system.domain.SysDept;
import com.clx.workflow.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请假申请 Service 实现
 *
 * @author clx
 */
@Service
public class OaLeaveApplyServiceImpl extends ServiceImpl<OaLeaveApplyMapper, OaLeaveApply>
        implements IOaLeaveApplyService {

    // @Autowired
    // private IWfProcessInstanceService processInstanceService;

    @Autowired
    private IHrLeaveTypeService leaveTypeService;

    @Autowired
    private ISysDeptService deptService;

    @Override
    public List<OaLeaveApply> selectLeaveApplyList(OaLeaveApply apply) {
        LambdaQueryWrapper<OaLeaveApply> wrapper = new LambdaQueryWrapper<>();
        if (apply.getUserName() != null && !apply.getUserName().isEmpty()) {
            wrapper.like(OaLeaveApply::getUserName, apply.getUserName());
        }
        if (apply.getDeptId() != null) {
            wrapper.eq(OaLeaveApply::getDeptId, apply.getDeptId());
        }
        if (apply.getStatus() != null && !apply.getStatus().isEmpty()) {
            wrapper.eq(OaLeaveApply::getStatus, apply.getStatus());
        }
        wrapper.orderByDesc(OaLeaveApply::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<OaLeaveApply> selectMyLeaveApply(Long userId) {
        LambdaQueryWrapper<OaLeaveApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OaLeaveApply::getUserId, userId)
                .orderByDesc(OaLeaveApply::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitApply(Long id) {
        OaLeaveApply apply = this.getById(id);
        if (apply == null) {
            throw new ServiceException("请假申请不存在");
        }
        if (!"draft".equals(apply.getStatus())) {
            throw new ServiceException("只有草稿状态的申请才能提交");
        }

        // 补充申请人信息
        if (apply.getUserName() == null) {
            apply.setUserName(SecurityUtils.getUsername());
        }
        if (apply.getDeptId() == null) {
            apply.setDeptId(SecurityUtils.getDeptId());
        }
        if (apply.getDeptName() == null && apply.getDeptId() != null) {
            SysDept dept = deptService.getById(apply.getDeptId());
            if (dept != null) {
                apply.setDeptName(dept.getDeptName());
            }
        }
        if (apply.getLeaveTypeName() == null && apply.getLeaveTypeId() != null) {
            HrLeaveType leaveType = leaveTypeService.getById(apply.getLeaveTypeId());
            if (leaveType != null) {
                apply.setLeaveTypeName(leaveType.getTypeName());
            }
        }

        // 启动审批流程 - 暂时简化
        String businessKey = "leave_" + id;
        String title = apply.getUserName() + "的请假申请";
        // Map<String, Object> variables = new HashMap<>();
        // variables.put("userId", apply.getUserId());
        // variables.put("leaveDays", apply.getLeaveDays());
        // variables.put("leaveType", apply.getLeaveTypeName());

        // String processInstanceId = processInstanceService.startProcess(
        //         "leave_approval", businessKey, "oa_leave_apply", id, title, variables);

        // 更新申请状态
        // apply.setProcessInstanceId(processInstanceId);
        apply.setStatus("pending");
        return this.updateById(apply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelApply(Long id) {
        OaLeaveApply apply = this.getById(id);
        if (apply == null) {
            throw new ServiceException("请假申请不存在");
        }
        if (!"pending".equals(apply.getStatus())) {
            throw new ServiceException("只有审批中的申请才能撤销");
        }

        // 终止流程 - 暂时简化
        // if (apply.getProcessInstanceId() != null) {
        //     processInstanceService.terminateProcess(apply.getProcessInstanceId(), "用户撤销申请");
        // }

        // 更新状态
        apply.setStatus("canceled");
        return this.updateById(apply);
    }
}