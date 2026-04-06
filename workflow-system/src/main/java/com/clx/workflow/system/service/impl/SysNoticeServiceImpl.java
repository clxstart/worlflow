package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.system.domain.SysNotice;
import com.clx.workflow.system.mapper.SysNoticeMapper;
import com.clx.workflow.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知公告 Service 实现类
 *
 * @author clx
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {

    @Autowired
    private SysNoticeMapper noticeMapper;

    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();
        if (notice.getNoticeTitle() != null && !notice.getNoticeTitle().isEmpty()) {
            wrapper.like(SysNotice::getNoticeTitle, notice.getNoticeTitle());
        }
        if (notice.getNoticeType() != null && !notice.getNoticeType().isEmpty()) {
            wrapper.eq(SysNotice::getNoticeType, notice.getNoticeType());
        }
        if (notice.getStatus() != null && !notice.getStatus().isEmpty()) {
            wrapper.eq(SysNotice::getStatus, notice.getStatus());
        }
        wrapper.orderByDesc(SysNotice::getCreateTime);
        return noticeMapper.selectList(wrapper);
    }
}