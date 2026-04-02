package com.clx.workflow.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.system.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和角色关联表 数据层
 *
 * @author clx
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 通过角色ID查询用户数量
     *
     * @param roleId 角色ID
     * @return 用户数量
     */
    int countUserRoleByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量新增用户角色关联
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(@Param("list") List<SysUserRole> userRoleList);

    /**
     * 删除用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 批量删除用户角色关联
     *
     * @param roleId 角色ID
     * @param userIds 用户ID数组
     * @return 结果
     */
    int deleteUserRoles(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}