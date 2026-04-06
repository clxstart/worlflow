package com.clx.workflow.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.system.domain.SysNotice;
import com.clx.workflow.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 通知公告 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {

    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public AjaxResult list(SysNotice notice,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysNotice> page = noticeService.lambdaQuery()
                .like(notice.getNoticeTitle() != null && !notice.getNoticeTitle().isEmpty(), SysNotice::getNoticeTitle, notice.getNoticeTitle())
                .eq(notice.getNoticeType() != null && !notice.getNoticeType().isEmpty(), SysNotice::getNoticeType, notice.getNoticeType())
                .eq(notice.getStatus() != null && !notice.getStatus().isEmpty(), SysNotice::getStatus, notice.getStatus())
                .orderByDesc(SysNotice::getCreateTime)
                .page(new Page<>(pageNum, pageSize));
        return success(page);
    }

    /**
     * 根据通知公告ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping("/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return success(noticeService.getById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        return toAjax(noticeService.save(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        return toAjax(noticeService.updateById(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.removeBatchByIds(Arrays.asList(noticeIds)));
    }
}