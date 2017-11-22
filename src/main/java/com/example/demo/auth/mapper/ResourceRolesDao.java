package com.example.demo.auth.mapper;

import com.example.demo.auth.ResourceRoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;

/**
 * Created by muyz on 2017/11/20.
 */
@Mapper
public interface ResourceRolesDao {

    int insert(ResourceRoles obj);

    int update(ResourceRoles obj);

    Collection<ResourceRoles> getAll();

    int deleteById(Long id);
}
