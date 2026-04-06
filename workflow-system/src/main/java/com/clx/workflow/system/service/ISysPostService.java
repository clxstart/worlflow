package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysPost;

import java.util.List;

/**
 * 岗位 Service 接口
 *
 * @author clx
 */
public interface ISysPostService extends IService<SysPost> {

    /**
     * 查询岗位列表
     */
    List<SysPost> selectPostList(SysPost post);

    /**
     * 根据用户ID查询岗位
     */
    List<SysPost> selectPostsByUserId(Long userId);

    /**
     * 查询所有岗位
     */
    List<SysPost> selectPostAll();

    /**
     * 校验岗位编码是否唯一
     */
    boolean checkPostCodeUnique(SysPost post);
}