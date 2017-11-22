package com.example.demo.auth.mapper;

import com.example.demo.auth.Role;
import com.example.demo.auth.UserRoles;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * Created by muyz on 2017/11/20.
 */
@Mapper
public interface UserRolesDao {
    int insert(UserRoles ur);
    int delete(UserRoles ur);
    int update(UserRoles ur);
    Collection<Role> getAll();
    int deleteById(Long id);

    Collection<UserRoles> getByUserName(String userName);
}

