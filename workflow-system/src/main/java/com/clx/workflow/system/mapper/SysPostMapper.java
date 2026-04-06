package com.clx.workflow.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.system.domain.SysPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 岗位 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 查询岗位列表
     */
    List<SysPost> selectPostList(SysPost post);

    /**
     * 根据用户ID查询岗位
     */
    List<SysPost> selectPostsByUserId(@Param("userId") Long userId);

    /**
     * 查询所有岗位
     */
    List<SysPost> selectPostAll();

    /**
     * 校验岗位编码是否唯一
     */
    SysPost checkPostCodeUnique(@Param("postCode") String postCode);
}