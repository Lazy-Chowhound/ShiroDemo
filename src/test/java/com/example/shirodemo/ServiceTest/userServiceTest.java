package com.example.shirodemo.ServiceTest;

import com.example.shirodemo.Service.userService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userServiceTest {
    @Autowired
    private userService userService;

    @Test
    public void testGetUserInfoByName() {
        System.out.println(userService.getUserInfoByName("中野三玖"));
    }

    @Test
    public void testGetUserPermissions() {
        System.out.println(userService.getUserPermissions("中野三玖"));
    }

    @Test
    public void testGetUserRoles(){
        System.out.println(userService.getUserRoles("中野三玖"));
    }
}
