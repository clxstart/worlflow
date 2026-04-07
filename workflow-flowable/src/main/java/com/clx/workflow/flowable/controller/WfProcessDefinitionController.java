package com.clx.workflow.flowable.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.flowable.domain.WfProcessDefinition;
import com.clx.workflow.flowable.service.IWfProcessDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/flowable/process")
public class WfProcessDefinitionController extends BaseController {

    @Autowired
    private IWfProcessDefinitionService definitionService;

    /**
     * 查询流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:list')")
    @GetMapping("/list")
    public AjaxResult list(WfProcessDefinition definition) {
        List<WfProcessDefinition> list = definitionService.selectDefinitionList(definition);
        return success(list);
    }

    /**
     * 查询流程定义详情
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(definitionService.getById(id));
    }

    /**
     * 部署流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:deploy')")
    @PostMapping("/deploy")
    public AjaxResult deploy(@Validated @RequestBody WfProcessDefinition definition) {
        return toAjax(definitionService.deployProcess(definition));
    }

    /**
     * 修改流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WfProcessDefinition definition) {
        return toAjax(definitionService.updateById(definition));
    }

    /**
     * 删除流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(definitionService.removeById(id));
    }

    /**
     * 挂起流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:suspend')")
    @PutMapping("/suspend/{processKey}")
    public AjaxResult suspend(@PathVariable String processKey) {
        return toAjax(definitionService.suspendProcess(processKey));
    }

    /**
     * 激活流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowable:process:activate')")
    @PutMapping("/activate/{processKey}")
    public AjaxResult activate(@PathVariable String processKey) {
        return toAjax(definitionService.activateProcess(processKey));
    }
}