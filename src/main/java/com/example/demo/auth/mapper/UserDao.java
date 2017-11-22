package com.example.demo.auth.mapper;

import com.example.demo.auth.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface UserDao {
	//@Select("Select * From t_users where username = #{username}")

	@Cacheable(value = "user" , key = "#a0")
	public User findByUsername(@Param("username")String username);

	public int insert(User user);

	@CacheEvict(value = "user" , key = "#a0.username")
	public int update(User user);
}
