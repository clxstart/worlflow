package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.constant.UserConstants;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.common.utils.SecurityUtils;
import com.clx.workflow.system.domain.SysMenu;
import com.clx.workflow.system.mapper.SysMenuMapper;
import com.clx.workflow.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限 Service 实现类
 *
 * @author clx
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList;
        // 管理员显示所有菜单信息
        if (SecurityUtils.isAdmin(userId)) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            if (menu.getMenuName() != null && !menu.getMenuName().isEmpty()) {
                wrapper.like(SysMenu::getMenuName, menu.getMenuName());
            }
            if (menu.getVisible() != null && !menu.getVisible().isEmpty()) {
                wrapper.eq(SysMenu::getVisible, menu.getVisible());
            }
            if (menu.getStatus() != null && !menu.getStatus().isEmpty()) {
                wrapper.eq(SysMenu::getStatus, menu.getStatus());
            }
            wrapper.orderByAsc(SysMenu::getParentId, SysMenu::getOrderNum);
            menuList = menuMapper.selectList(wrapper);
        } else {
            menuList = menuMapper.selectMenuListByUserId(userId);
        }
        return menuList;
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (perm != null && !perm.isEmpty()) {
                permsSet.addAll(Set.of(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysMenu> selectMenusByUserId(Long userId) {
        List<SysMenu> menus;
        if (SecurityUtils.isAdmin(userId)) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getMenuType, UserConstants.TYPE_MENU)
                    .or().eq(SysMenu::getMenuType, UserConstants.TYPE_DIR);
            wrapper.eq(SysMenu::getStatus, UserConstants.NORMAL)
                    .orderByAsc(SysMenu::getParentId, SysMenu::getOrderNum);
            menus = menuMapper.selectList(wrapper);
        } else {
            menus = menuMapper.selectMenusByUserId(userId);
        }
        return getChildPerms(menus, 0L);
    }

    @Override
    public List<SysMenu> buildMenus(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId() == 0) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    @Override
    public List<SysMenu> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees;
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return menuMapper.selectMenuListByRoleId(roleId, true);
    }

    @Override
    public boolean hasChildByParentId(Long parentId) {
        long result = this.lambdaQuery()
                .eq(SysMenu::getParentId, parentId)
                .count();
        return result > 0;
    }

    @Override
    public boolean checkMenuNameUnique(SysMenu menu) {
        Long menuId = menu.getMenuId() == null ? -1L : menu.getMenuId();
        SysMenu info = this.lambdaQuery()
                .eq(SysMenu::getMenuName, menu.getMenuName())
                .eq(SysMenu::getParentId, menu.getParentId())
                .one();
        if (info != null && !info.getMenuId().equals(menuId)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(SysMenu menu) {
        if (!checkMenuNameUnique(menu)) {
            throw new ServiceException("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        return super.save(menu);
    }

    @Override
    public boolean updateById(SysMenu menu) {
        if (!checkMenuNameUnique(menu)) {
            throw new ServiceException("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        return super.updateById(menu);
    }

    /**
     * 删除菜单
     */
    public void deleteMenuById(Long menuId) {
        if (hasChildByParentId(menuId)) {
            throw new ServiceException("存在子菜单,不允许删除");
        }
        menuMapper.deleteById(menuId);
    }

    /**
     * 构建菜单树
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        List<Long> tempList = menus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        for (SysMenu menu : menus) {
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<>();
        for (SysMenu n : list) {
            if (n.getParentId() != null && n.getParentId().equals(t.getMenuId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 根据父节点的ID获取所有子节点
     */
    private List<SysMenu> getChildPerms(List<SysMenu> menus, Long parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId().equals(parentId)) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        return returnList;
    }
}