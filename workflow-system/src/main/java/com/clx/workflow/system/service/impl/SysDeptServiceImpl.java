package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.constant.UserConstants;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.system.domain.SysDept;
import com.clx.workflow.system.mapper.SysDeptMapper;
import com.clx.workflow.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门 Service 实现类
 *
 * @author clx
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        if (dept.getParentId() != null) {
            wrapper.eq(SysDept::getParentId, dept.getParentId());
        }
        if (dept.getDeptName() != null && !dept.getDeptName().isEmpty()) {
            wrapper.like(SysDept::getDeptName, dept.getDeptName());
        }
        if (dept.getStatus() != null && !dept.getStatus().isEmpty()) {
            wrapper.eq(SysDept::getStatus, dept.getStatus());
        }
        wrapper.orderByAsc(SysDept::getOrderNum);
        return deptMapper.selectList(wrapper);
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<>();
        List<Long> tempList = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        for (SysDept dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public List<SysDept> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees;
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        return deptMapper.selectDeptListByRoleId(roleId);
    }

    @Override
    public boolean hasChildByParentId(Long parentId) {
        int result = deptMapper.selectChildrenCountByParentId(parentId);
        return result > 0;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    @Override
    public boolean checkDeptNameUnique(SysDept dept) {
        Long deptId = dept.getDeptId() == null ? -1L : dept.getDeptId();
        SysDept info = this.lambdaQuery()
                .eq(SysDept::getDeptName, dept.getDeptName())
                .eq(SysDept::getParentId, dept.getParentId())
                .one();
        if (info != null && !info.getDeptId().equals(deptId)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(SysDept dept) {
        if (!checkDeptNameUnique(dept)) {
            throw new ServiceException("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        SysDept parentDept = this.getById(dept.getParentId());
        // 设置祖级列表
        if (parentDept != null) {
            dept.setAncestors(parentDept.getAncestors() + "," + dept.getParentId());
        } else {
            dept.setAncestors("0");
        }
        return super.save(dept);
    }

    @Override
    public boolean updateById(SysDept dept) {
        if (!checkDeptNameUnique(dept)) {
            throw new ServiceException("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        SysDept parentDept = this.getById(dept.getParentId());
        SysDept oldDept = this.getById(dept.getDeptId());
        if (parentDept != null && oldDept != null) {
            String newAncestors = parentDept.getAncestors() + "," + dept.getParentId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        return super.updateById(dept);
    }

    /**
     * 删除部门
     */
    @Override
    public void deleteDeptById(Long deptId) {
        if (hasChildByParentId(deptId)) {
            throw new ServiceException("存在子部门,不允许删除");
        }
        if (checkDeptExistUser(deptId)) {
            throw new ServiceException("部门存在用户,不允许删除");
        }
        deptMapper.deleteById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<>();
        for (SysDept n : list) {
            if (n.getParentId() != null && n.getParentId().equals(t.getDeptId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 修改子部门的祖级列表
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = this.lambdaQuery()
                .likeRight(SysDept::getAncestors, oldAncestors + "," + deptId)
                .list();
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        this.updateBatchById(children);
    }
}