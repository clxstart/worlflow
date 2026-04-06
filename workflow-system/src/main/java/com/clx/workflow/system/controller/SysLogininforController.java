package com.clx.workflow.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.system.domain.SysLogininfor;
import com.clx.workflow.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统访问记录 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/system/logininfor")
public class SysLogininforController extends BaseController {

    @Autowired
    private ISysLogininforService logininforService;

    /**
     * 获取登录日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:logininfor:list')")
    @GetMapping("/list")
    public AjaxResult list(SysLogininfor logininfor,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysLogininfor> page = logininforService.lambdaQuery()
                .like(logininfor.getUserName() != null && !logininfor.getUserName().isEmpty(), SysLogininfor::getUserName, logininfor.getUserName())
                .like(logininfor.getIpaddr() != null && !logininfor.getIpaddr().isEmpty(), SysLogininfor::getIpaddr, logininfor.getIpaddr())
                .eq(logininfor.getStatus() != null && !logininfor.getStatus().isEmpty(), SysLogininfor::getStatus, logininfor.getStatus())
                .orderByDesc(SysLogininfor::getLoginTime)
                .page(new Page<>(pageNum, pageSize));
        return success(page);
    }

    /**
     * 删除登录日志
     */
    @PreAuthorize("@ss.hasPermi('system:logininfor:remove')")
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(logininforService.removeBatchByIds(Arrays.asList(infoIds)));
    }

    /**
     * 清空登录日志
     */
    @PreAuthorize("@ss.hasPermi('system:logininfor:remove')")
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return success();
    }
}