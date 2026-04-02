package com.clx.workflow.system.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.core.redis.RedisCache;
import com.clx.workflow.system.domain.SysUser;
import com.clx.workflow.system.domain.SysRole;
import com.clx.workflow.system.domain.SysUserRole;
import com.clx.workflow.system.service.ISysUserService;
import com.clx.workflow.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试接口（仅用于开发测试，生产环境请删除）
 *
 * @author clx
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisCache redisCache;

    /**
     * 创建缺失的表
     */
    @PostMapping("/createTables")
    public AjaxResult createTables() {
        try {
            // 创建 sys_role 表
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS sys_role (" +
                    "role_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID'," +
                    "role_name VARCHAR(30) NOT NULL COMMENT '角色名称'," +
                    "role_key VARCHAR(100) NOT NULL COMMENT '角色权限字符串'," +
                    "role_sort INT NOT NULL COMMENT '显示顺序'," +
                    "status CHAR(1) NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）'," +
                    "deleted INT DEFAULT 0 COMMENT '删除标志'," +
                    "create_time DATETIME COMMENT '创建时间'," +
                    "update_time DATETIME COMMENT '更新时间'," +
                    "remark VARCHAR(500) COMMENT '备注'," +
                    "PRIMARY KEY (role_id)" +
                    ") ENGINE=InnoDB COMMENT='角色信息表'");

            // 创建 sys_user_role 表
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS sys_user_role (" +
                    "user_id BIGINT NOT NULL COMMENT '用户ID'," +
                    "role_id BIGINT NOT NULL COMMENT '角色ID'," +
                    "PRIMARY KEY (user_id, role_id)" +
                    ") ENGINE=InnoDB COMMENT='用户和角色关联表'");

            return success("表创建成功！");
        } catch (Exception e) {
            return error("创建表失败: " + e.getMessage());
        }
    }

    /**
     * 检查数据库连接和数据
     */
    @GetMapping("/db")
    public AjaxResult checkDatabase() {
        Map<String, Object> result = new HashMap<>();

        // 查询用户数量
        long userCount = userService.count();
        result.put("userCount", userCount);

        // 查询角色数量
        long roleCount = roleService.count();
        result.put("roleCount", roleCount);

        // 查询用户列表
        List<SysUser> users = userService.lambdaQuery().list();
        result.put("users", users);

        // 查询角色列表
        List<SysRole> roles = roleService.lambdaQuery().list();
        result.put("roles", roles);

        return success(result);
    }

    /**
     * 初始化测试数据
     */
    @PostMapping("/init")
    public AjaxResult initTestData() {
        // 创建表
        createTables();

        // 检查是否已有角色
        long roleCount = roleService.count();
        if (roleCount == 0) {
            // 创建管理员角色
            SysRole adminRole = new SysRole();
            adminRole.setRoleName("超级管理员");
            adminRole.setRoleKey("admin");
            adminRole.setRoleSort(1);
            adminRole.setStatus("0");
            roleService.save(adminRole);

            // 创建普通用户角色
            SysRole userRole = new SysRole();
            userRole.setRoleName("普通用户");
            userRole.setRoleKey("common");
            userRole.setRoleSort(2);
            userRole.setStatus("0");
            roleService.save(userRole);

            // 给admin用户分配管理员角色
            jdbcTemplate.update("INSERT INTO sys_user_role(user_id, role_id) VALUES(1, 1)");
        }

        // 检查是否已有用户
        long userCount = userService.count();
        if (userCount == 0) {
            // 创建管理员用户
            SysUser admin = new SysUser();
            admin.setUserName("admin");
            admin.setNickName("管理员");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@clx.com");
            admin.setPhonenumber("15888888888");
            admin.setStatus("0");
            userService.save(admin);

            // 创建测试用户
            SysUser test = new SysUser();
            test.setUserName("test");
            test.setNickName("测试用户");
            test.setPassword(passwordEncoder.encode("test123"));
            test.setEmail("test@clx.com");
            test.setPhonenumber("15666666666");
            test.setStatus("0");
            userService.save(test);
        }

        return success("初始化成功！用户数: " + userCount + ", 角色数: " + roleCount);
    }

    /**
     * 重置用户密码
     */
    @PostMapping("/resetPwd")
    public AjaxResult resetPassword(@RequestParam String username, @RequestParam String password) {
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            return error("用户不存在: " + username);
        }
        user.setPassword(passwordEncoder.encode(password));
        userService.updateById(user);
        return success("密码已重置为: " + password);
    }

    /**
     * 测试密码验证
     */
    @GetMapping("/verifyPwd")
    public AjaxResult verifyPassword(@RequestParam String username, @RequestParam String password) {
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            return error("用户不存在: " + username);
        }
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        return success("密码验证结果: " + matches + ", 数据库密码hash: " + user.getPassword());
    }

    /**
     * 完整登录测试（绕过Spring Security）
     */
    @PostMapping("/fullLogin")
    public AjaxResult fullLoginTest(@RequestParam String username, @RequestParam String password) {
        // 1. 查询用户
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            return error("用户不存在: " + username);
        }

        // 2. 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return error("密码错误");
        }

        // 3. 检查状态
        if ("1".equals(user.getStatus())) {
            return error("账号已被停用");
        }

        // 4. 获取权限
        Set<String> permissions = roleService.selectRolePermissionByUserId(user.getUserId());

        return success("登录成功！用户: " + user.getUserName() + ", 权限: " + permissions);
    }

    /**
     * 检查Redis中的Token数据
     */
    @GetMapping("/checkRedis")
    public AjaxResult checkRedis() {
        java.util.Collection<String> keys = jdbcTemplate.queryForList(
            "SELECT CONCAT('login_tokens:', token) FROM (SELECT 1 as token) t",
            String.class
        );

        // 使用 RedisCache 检查
        StringBuilder result = new StringBuilder();
        try {
            java.util.Collection<String> redisKeys = redisCache.keys("login_tokens:*");
            result.append("Redis中的登录Token数量: ").append(redisKeys == null ? 0 : redisKeys.size());
            if (redisKeys != null) {
                for (String key : redisKeys) {
                    Object value = redisCache.getCacheObject(key);
                    result.append("\n").append(key).append(": ").append(value != null ? "存在" : "不存在");
                }
            }
        } catch (Exception e) {
            result.append("Redis检查失败: ").append(e.getMessage());
        }

        return success(result.toString());
    }
}