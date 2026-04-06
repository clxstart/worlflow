package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysOperLog;

import java.util.List;

/**
 * 操作日志 Service 接口
 *
 * @author clx
 */
public interface ISysOperLogService extends IService<SysOperLog> {

    /**
     * 查询操作日志列表
     */
    List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}