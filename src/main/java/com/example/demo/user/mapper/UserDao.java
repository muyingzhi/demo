package com.example.demo.user.mapper;

import com.example.demo.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
	//@Select("Select * From t_users where username = #{username}")
	public User findByUsername(@Param("username")String username);

	public int insert(User user);

	public int update(User user);
}
