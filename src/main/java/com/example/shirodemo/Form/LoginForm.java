package com.example.shirodemo.Form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Nakano Miku
 */
@Data
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String password;
}
