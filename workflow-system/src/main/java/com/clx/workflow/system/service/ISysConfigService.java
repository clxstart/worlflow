package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysConfig;

/**
 * 参数配置 Service 接口
 *
 * @author clx
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 根据参数键名查询参数值
     */
    String selectConfigByKey(String configKey);

    /**
     * 校验参数键名是否唯一
     */
    boolean checkConfigKeyUnique(SysConfig config);
}