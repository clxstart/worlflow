package com.clx.workflow.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.system.domain.SysOperLog;
import com.clx.workflow.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 操作日志 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/system/operlog")
public class SysOperLogController extends BaseController {

    @Autowired
    private ISysOperLogService operLogService;

    /**
     * 获取操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:list')")
    @GetMapping("/list")
    public AjaxResult list(SysOperLog operLog,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysOperLog> page = operLogService.lambdaQuery()
                .like(operLog.getTitle() != null && !operLog.getTitle().isEmpty(), SysOperLog::getTitle, operLog.getTitle())
                .eq(operLog.getBusinessType() != null, SysOperLog::getBusinessType, operLog.getBusinessType())
                .eq(operLog.getStatus() != null, SysOperLog::getStatus, operLog.getStatus())
                .like(operLog.getOperName() != null && !operLog.getOperName().isEmpty(), SysOperLog::getOperName, operLog.getOperName())
                .orderByDesc(SysOperLog::getOperTime)
                .page(new Page<>(pageNum, pageSize));
        return success(page);
    }

    /**
     * 根据操作日志ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:query')")
    @GetMapping("/{operId}")
    public AjaxResult getInfo(@PathVariable Long operId) {
        return success(operLogService.getById(operId));
    }

    /**
     * 删除操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(operLogService.removeBatchByIds(Arrays.asList(operIds)));
    }

    /**
     * 清空操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:remove')")
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return success();
    }
}