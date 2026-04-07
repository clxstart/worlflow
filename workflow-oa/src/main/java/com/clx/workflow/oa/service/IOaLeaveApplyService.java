package com.clx.workflow.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.oa.domain.OaLeaveApply;

import java.util.List;

/**
 * 请假申请 Service 接口
 *
 * @author clx
 */
public interface IOaLeaveApplyService extends IService<OaLeaveApply> {

    /**
     * 查询请假申请列表
     */
    List<OaLeaveApply> selectLeaveApplyList(OaLeaveApply apply);

    /**
     * 查询我的请假申请
     */
    List<OaLeaveApply> selectMyLeaveApply(Long userId);

    /**
     * 提交审批
     */
    boolean submitApply(Long id);

    /**
     * 撤销申请
     */
    boolean cancelApply(Long id);
}