package com.example.shirodemo.Common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Nakano Miku
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return Result.fail("参数错误", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationExceptionHandler(AuthenticationException exception) {
        return Result.fail("登录异常", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    public Result unauthenticatedExceptionHandler(UnauthenticatedException exception) {
        return Result.fail("您没有登录，请先登录", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result unauthorizedExceptionHandler(UnauthorizedException exception) {
        return Result.fail("没有相关权限", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Result authorizationExceptionHandler(AuthorizationException exception) {
        return Result.fail("您没有权限", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(UnsupportedTokenException.class)
    public Result unsupportedTokenExceptionHandler(UnsupportedTokenException exception) {
        return Result.fail("token为null", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(UnknownAccountException.class)
    public Result unknownAccountExceptionHandler(UnknownAccountException exception) {
        return Result.fail("账户不存在", exception.getClass());
    }

    @ResponseBody
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result incorrectCredentialsExceptionHandler(IncorrectCredentialsException exception) {
        return Result.fail("密码错误", exception.getClass());
    }
}
