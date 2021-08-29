package com.example.shirodemo.Controller;

import com.example.shirodemo.Common.Result;
import com.example.shirodemo.Config.Shiro.AccessToken;
import com.example.shirodemo.Config.Shiro.JwtUtil;
import com.example.shirodemo.Form.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Nakano Miku
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginForm loginForm) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return Result.success("已经登录，请先退出当前帐号再登录", null);
        }
        String userName = loginForm.getName();
        String password = loginForm.getPassword();
        String token = jwtUtil.createToken(userName, password);
        System.out.println(token);
        AccessToken accessToken = new AccessToken(token);
        subject.login(accessToken);

        return Result.success("login success!", token);
    }

    /**
     * 配置了authc拦截器，需要登录才能访问
     */
    @GetMapping("/display")
    public Result display() {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return Result.success("以下是您的用户名", userName);
    }

    /**
     * RequiresAuthentication 需要登录，否则抛出 UnauthenticatedException 异常
     * RequiresPermissions 需要登录和相关权限，否则抛出 UnauthenticatedException 或 UnauthorizedException 异常
     * RequiresRoles 需要登录和相关角色，否则抛出 UnauthenticatedException 或 UnauthorizedException 异常
     * UnauthenticatedException 和 UnauthorizedException均为 AuthorizationException 的子类
     */
    @GetMapping("/get")
    @RequiresAuthentication
    @RequiresPermissions("C:INSERT")
    @RequiresRoles("R1")
    public Result get() {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return Result.success("以下是您的用户名", userName);
    }

    @GetMapping("/area")
    public Result area() {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return Result.success("欢迎来到我的领域," + userName, null);
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "logout success";
    }

    @GetMapping("/notAllowed")
    public Result ban() {
        return Result.fail("not allowed,尚未登录", null);
    }

    @GetMapping("/homepage")
    public Result hello() {
        return Result.success("小哥，买苹果吗", null);
    }

}
