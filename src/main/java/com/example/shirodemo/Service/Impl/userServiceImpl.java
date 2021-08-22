package com.example.shirodemo.Service.Impl;

import com.example.shirodemo.Dao.userDao;
import com.example.shirodemo.Entity.Permission;
import com.example.shirodemo.Entity.Role;
import com.example.shirodemo.Entity.User;
import com.example.shirodemo.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Nakano Miku
 */
@Service
public class userServiceImpl implements userService {

    @Autowired
    private userDao userDao;


    @Override
    public User getUserInfoByName(String userName) {
        return userDao.getUserInfoByName(userName);
    }

    @Override
    public Set<Permission> getUserPermissions(String userName) {
        return userDao.getUserPermissions(userName);
    }

    @Override
    public Set<Role> getUserRoles(String userName) {
        return userDao.getUserRoles(userName);
    }
}
