package com.clx.workflow.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.system.domain.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询部门列表
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询部门树信息
     */
    List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询子部门数量
     */
    int selectChildrenCountByParentId(@Param("parentId") Long parentId);

    /**
     * 查询部门是否存在用户
     */
    int checkDeptExistUser(@Param("deptId") Long deptId);
}