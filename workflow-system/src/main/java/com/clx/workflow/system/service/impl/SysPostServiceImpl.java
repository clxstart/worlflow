package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.system.domain.SysPost;
import com.clx.workflow.system.mapper.SysPostMapper;
import com.clx.workflow.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位 Service 实现类
 *
 * @author clx
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Autowired
    private SysPostMapper postMapper;

    @Override
    public List<SysPost> selectPostList(SysPost post) {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        if (post.getPostCode() != null && !post.getPostCode().isEmpty()) {
            wrapper.like(SysPost::getPostCode, post.getPostCode());
        }
        if (post.getPostName() != null && !post.getPostName().isEmpty()) {
            wrapper.like(SysPost::getPostName, post.getPostName());
        }
        if (post.getStatus() != null && !post.getStatus().isEmpty()) {
            wrapper.eq(SysPost::getStatus, post.getStatus());
        }
        wrapper.orderByAsc(SysPost::getPostSort);
        return postMapper.selectList(wrapper);
    }

    @Override
    public List<SysPost> selectPostsByUserId(Long userId) {
        return postMapper.selectPostsByUserId(userId);
    }

    @Override
    public List<SysPost> selectPostAll() {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPost::getStatus, "0")
                .orderByAsc(SysPost::getPostSort);
        return postMapper.selectList(wrapper);
    }

    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        Long postId = post.getPostId() == null ? -1L : post.getPostId();
        SysPost info = this.lambdaQuery()
                .eq(SysPost::getPostCode, post.getPostCode())
                .one();
        if (info != null && !info.getPostId().equals(postId)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(SysPost post) {
        if (!checkPostCodeUnique(post)) {
            throw new ServiceException("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return super.save(post);
    }

    @Override
    public boolean updateById(SysPost post) {
        if (!checkPostCodeUnique(post)) {
            throw new ServiceException("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return super.updateById(post);
    }
}