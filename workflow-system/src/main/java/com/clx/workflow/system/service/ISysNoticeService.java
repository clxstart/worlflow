package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysNotice;

import java.util.List;

/**
 * 通知公告 Service 接口
 *
 * @author clx
 */
public interface ISysNoticeService extends IService<SysNotice> {

    /**
     * 查询公告列表
     */
    List<SysNotice> selectNoticeList(SysNotice notice);
}