package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.common.utils.StringUtils;
import com.clx.workflow.system.domain.SysRole;
import com.clx.workflow.system.domain.SysUserRole;
import com.clx.workflow.system.mapper.SysRoleMapper;
import com.clx.workflow.system.mapper.SysUserRoleMapper;
import com.clx.workflow.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色服务实现
 *
 * @author clx
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 根据用户ID查询角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询角色权限字符串
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<String> perms = roleMapper.selectRoleKeyByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色ID列表
     */
    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        return roleMapper.selectRoleIdListByUserId(userId);
    }

    /**
     * 查询所有角色
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return this.lambdaQuery()
                .eq(SysRole::getDeleted, 0)
                .orderByAsc(SysRole::getRoleSort)
                .list();
    }

    /**
     * 通过角色ID查询角色
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return this.lambdaQuery()
                .eq(SysRole::getRoleId, roleId)
                .eq(SysRole::getDeleted, 0)
                .one();
    }

    /**
     * 校验角色名称是否唯一
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验角色权限是否唯一
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验角色是否允许操作
     */
    @Override
    public void checkRoleAllowed(SysRole role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增角色
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        // 新增角色信息
        this.save(role);
        return 1;
    }

    /**
     * 修改角色
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        this.updateById(role);
        return 1;
    }

    /**
     * 批量删除角色
     */
    @Override
    @Transactional
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(new SysRole());
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%s已分配,不能删除", role.getRoleName()));
            }
        }
        // 逻辑删除角色
        return this.lambdaUpdate()
                .in(SysRole::getRoleId, roleIds)
                .set(SysRole::getDeleted, 1)
                .update() ? roleIds.length : 0;
    }

    /**
     * 取消授权用户角色
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return userRoleMapper.deleteUserRole(userRole.getUserId(), userRole.getRoleId());
    }

    /**
     * 批量取消授权用户角色
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        return userRoleMapper.deleteUserRoles(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     */
    @Override
    @Transactional
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}