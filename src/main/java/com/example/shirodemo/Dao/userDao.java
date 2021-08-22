package com.example.shirodemo.Dao;

import com.example.shirodemo.Entity.Permission;
import com.example.shirodemo.Entity.Role;
import com.example.shirodemo.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author Nakano Miku
 */
@Mapper
public interface userDao {

    User getUserInfoByName(String userName);

    Set<Permission> getUserPermissions(String userName);

    Set<Role> getUserRoles(String userName);
}
