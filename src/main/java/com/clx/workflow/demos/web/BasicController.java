package com.clx.workflow.demos.web;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础控制器示例
 *
 * @author clx
 */
@RestController
public class BasicController extends BaseController {

    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/hello")
    public AjaxResult hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return success("Hello " + name);
    }

    // http://127.0.0.1:8080/user
    @RequestMapping("/user")
    public AjaxResult user() {
        User user = new User();
        user.setName("theonefx");
        user.setAge(666);
        return success(user);
    }

    // http://127.0.0.1:8080/save_user?name=newName&age=11
    @RequestMapping("/save_user")
    public AjaxResult saveUser(User u) {
        return success("user will save: name=" + u.getName() + ", age=" + u.getAge());
    }
}
