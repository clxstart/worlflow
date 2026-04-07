package com.clx.workflow.flowable.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.flowable.domain.WfFlowRecord;
import com.clx.workflow.flowable.domain.WfProcessInstance;
import com.clx.workflow.flowable.service.IWfFlowRecordService;
import com.clx.workflow.flowable.service.IWfProcessInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程实例 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/flowable/instance")
public class WfProcessInstanceController extends BaseController {

    @Autowired
    private IWfProcessInstanceService instanceService;

    @Autowired
    private IWfFlowRecordService flowRecordService;

    /**
     * 查询流程实例列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:instance:list')")
    @GetMapping("/list")
    public AjaxResult list(WfProcessInstance instance) {
        List<WfProcessInstance> list = instanceService.selectInstanceList(instance);
        return success(list);
    }

    /**
     * 查询流程实例详情
     */
    @PreAuthorize("@ss.hasPermi('flowable:instance:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(instanceService.getById(id));
    }

    /**
     * 查询审批记录
     */
    @PreAuthorize("@ss.hasPermi('flowable:instance:record')")
    @GetMapping("/record/{processInstanceId}")
    public AjaxResult getRecord(@PathVariable String processInstanceId) {
        List<WfFlowRecord> records = flowRecordService.selectRecordList(processInstanceId);
        return success(records);
    }

    /**
     * 终止流程
     */
    @PreAuthorize("@ss.hasPermi('flowable:instance:terminate')")
    @PutMapping("/terminate/{processInstanceId}")
    public AjaxResult terminate(@PathVariable String processInstanceId,
                                @RequestParam(required = false) String reason) {
        return toAjax(instanceService.terminateProcess(processInstanceId, reason));
    }
}