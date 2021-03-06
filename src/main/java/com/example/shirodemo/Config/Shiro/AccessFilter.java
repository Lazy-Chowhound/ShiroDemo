package com.example.shirodemo.Config.Shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.shirodemo.Common.Util.HttpServletWrapper;
import com.example.shirodemo.Service.userService;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


/**
 * @author Nakano Miku
 */
@Component
public class AccessFilter extends AuthenticatingFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private userService userService;

    @Value("${jwt.token.refreshTime}")
    private int refreshTime;

    /**
     * executeLogin会调用此方法生成login时所需的token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String token = getUserToken(servletRequest);
        System.out.println(token);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        return new AccessToken(token);
    }

    /**
     * 是否允许访问，返回true继续往下执行，返回false则执行onAccessDenied方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // options请求不拦截
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            return false;
        } else {
            String[] roles = (String[]) mappedValue;
            if (roles == null || roles.length == 0) {
                return true;
            }
            for (String role : roles) {
                if (subject.hasRole(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * isAccessAllowed返回false会进入该方法
     * 返回false则不会往下执行，返回true则继续执行
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        Subject subject = getSubject(servletRequest, servletResponse);
        // 没登录则尝试登录
        if (!subject.isAuthenticated()) {
            String token = getUserToken(servletRequest);
            String userName = jwtUtil.getUserName(token);
            String password = userService.getUserInfoByName(userName).getPassword();
            //查看token有无过期
            try {
                jwtUtil.verifyToken(token);
            } catch (TokenExpiredException expiredException) {
                // 如果用户token过期，redis里refreshToken也过期，则重新登录
                if (!redisTemplate.hasKey(userName)) {
                    httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                    httpServletResponse.getWriter().print("token已过期,请重新登录");
                    return false;
                }
            } catch (Exception exception) {
                httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().print("无效的token");
                return false;
            }
            System.out.println("oldtoken:" + token);
            // redis里token没过期，则生成新的用户token和refreshToken，再用新token登录
            String newUserToken = jwtUtil.createUserToken(userName, password);
            // 新生成的用户token返回给用户
            httpServletResponse.setHeader("token", newUserToken);
            // 将新生成的token写入request
            HttpServletWrapper httpServletWrapper = new HttpServletWrapper((HttpServletRequest) servletRequest, newUserToken);

            //生成新的refreshToken，刷新过期时间，存入redis
            String newRefreshToken = jwtUtil.createRefreshToken(userName, password);
            redisTemplate.delete(userName);
            redisTemplate.opsForValue().set(userName, newRefreshToken, refreshTime, TimeUnit.DAYS);

            return executeLogin(httpServletWrapper, servletResponse);
        }
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().print("没有相关权限");
        return false;
    }


    /**
     * executeLogin登录失败时调用此方法，调用了subject.login，失败情况相同
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try {
            httpServletResponse.getWriter().print(e.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return false;
    }

    /**
     * 获取token
     */
    private String getUserToken(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = httpServletRequest.getParameter("token");
        }
        return token;
    }
}
