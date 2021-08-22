package com.example.shirodemo.DaoTest;

import com.example.shirodemo.Dao.userDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userDaoTest {
    @Autowired
    private userDao userDao;

    @Test
    public void testGetUserByName(){
        System.out.println(userDao.getUserInfoByName("中野三玖"));
    }

    @Test
    public void testGetUserPermissions(){
        System.out.println(userDao.getUserPermissions("中野三玖"));
    }

    @Test
    public void testGetUserRoles(){
        System.out.println(userDao.getUserRoles("中野三玖"));
    }
}
