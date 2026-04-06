package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.system.domain.SysOperLog;
import com.clx.workflow.system.mapper.SysOperLogMapper;
import com.clx.workflow.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 Service 实现类
 *
 * @author clx
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();
        if (operLog.getTitle() != null && !operLog.getTitle().isEmpty()) {
            wrapper.like(SysOperLog::getTitle, operLog.getTitle());
        }
        if (operLog.getBusinessType() != null) {
            wrapper.eq(SysOperLog::getBusinessType, operLog.getBusinessType());
        }
        if (operLog.getStatus() != null) {
            wrapper.eq(SysOperLog::getStatus, operLog.getStatus());
        }
        if (operLog.getOperName() != null && !operLog.getOperName().isEmpty()) {
            wrapper.like(SysOperLog::getOperName, operLog.getOperName());
        }
        wrapper.orderByDesc(SysOperLog::getOperTime);
        return operLogMapper.selectList(wrapper);
    }

    @Override
    public void cleanOperLog() {
        operLogMapper.delete(new LambdaQueryWrapper<>());
    }
}