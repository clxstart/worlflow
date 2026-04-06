package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysLogininfor;

import java.util.List;

/**
 * 系统访问记录 Service 接口
 *
 * @author clx
 */
public interface ISysLogininforService extends IService<SysLogininfor> {

    /**
     * 查询登录日志列表
     */
    List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * 清空登录日志
     */
    void cleanLogininfor();
}