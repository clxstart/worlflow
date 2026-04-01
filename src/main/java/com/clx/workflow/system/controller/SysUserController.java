package com.clx.workflow.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.system.domain.SysUser;
import com.clx.workflow.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    /**
     * 查询用户列表（分页）
     */
    @GetMapping("/list")
    public AjaxResult list(SysUser user,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysUser> page = userService.lambdaQuery()
                .like(user.getUserName() != null, SysUser::getUserName, user.getUserName())
                .like(user.getNickName() != null, SysUser::getNickName, user.getNickName())
                .eq(user.getStatus() != null, SysUser::getStatus, user.getStatus())
                .page(new Page<>(pageNum, pageSize));
        return success(page);
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{userId}")
    public AjaxResult getInfo(@PathVariable Long userId) {
        return success(userService.getById(userId));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysUser user) {
        return toAjax(userService.save(user));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user) {
        return toAjax(userService.updateById(user));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.removeBatchByIds(java.util.Arrays.asList(userIds)));
    }
}