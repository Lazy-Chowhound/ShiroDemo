package com.example.shirodemo.Service;

import com.example.shirodemo.Entity.Permission;
import com.example.shirodemo.Entity.Role;
import com.example.shirodemo.Entity.User;

import java.util.Set;

/**
 * @author Nakano Miku
 */
public interface userService {

    User getUserInfoByName(String userName);

    Set<Permission> getUserPermissions(String userName);

    Set<Role> getUserRoles(String userName);
}
