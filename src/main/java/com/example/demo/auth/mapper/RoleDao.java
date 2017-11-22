package com.example.demo.auth.mapper;

import com.example.demo.auth.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * Created by muyz on 2017/11/20.
 */
@Mapper
public interface RoleDao {
    int insert(Role role);
    int delete(Role role);
    Collection<Role> getAll();
    int deleteById(Long id);
}
