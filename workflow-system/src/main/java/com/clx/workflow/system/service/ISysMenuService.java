package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限 Service 接口
 *
 * @author clx
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户查询系统菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * 构建前端路由所需要的菜单
     */
    List<SysMenu> buildMenus(List<SysMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     */
    List<SysMenu> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 根据角色ID查询菜单树信息
     */
    List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 根据用户ID查询权限标识
     */
    Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 是否存在菜单子节点
     */
    boolean hasChildByParentId(Long parentId);

    /**
     * 校验菜单名称是否唯一
     */
    boolean checkMenuNameUnique(SysMenu menu);
}