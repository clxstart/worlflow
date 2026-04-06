package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.system.domain.SysLogininfor;
import com.clx.workflow.system.mapper.SysLogininforMapper;
import com.clx.workflow.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问记录 Service 实现类
 *
 * @author clx
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService {

    @Autowired
    private SysLogininforMapper logininforMapper;

    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        LambdaQueryWrapper<SysLogininfor> wrapper = new LambdaQueryWrapper<>();
        if (logininfor.getUserName() != null && !logininfor.getUserName().isEmpty()) {
            wrapper.like(SysLogininfor::getUserName, logininfor.getUserName());
        }
        if (logininfor.getIpaddr() != null && !logininfor.getIpaddr().isEmpty()) {
            wrapper.like(SysLogininfor::getIpaddr, logininfor.getIpaddr());
        }
        if (logininfor.getStatus() != null && !logininfor.getStatus().isEmpty()) {
            wrapper.eq(SysLogininfor::getStatus, logininfor.getStatus());
        }
        wrapper.orderByDesc(SysLogininfor::getLoginTime);
        return logininforMapper.selectList(wrapper);
    }

    @Override
    public void cleanLogininfor() {
        logininforMapper.delete(new LambdaQueryWrapper<>());
    }
}