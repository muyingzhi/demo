package com.example.demo.auth.service;

import com.example.demo.auth.Role;
import com.example.demo.auth.mapper.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by muyz on 2017/11/20.
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public int insert(Role role){
        return roleDao.insert(role);
    }

    public int delete(Role role){
        return roleDao.delete(role);
    }

    public Collection<Role> getAll(){
        return  roleDao.getAll();
    }

    public int deleteById(Long id) {
        return roleDao.deleteById(id);
    }

}
