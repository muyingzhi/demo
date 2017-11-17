package com.example.demo.user;

import com.example.demo.user.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserService implements UserDetailsService{
	@Autowired
	private UserDao dao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dao.findByUsername(username);
		if (user!= null){
			return new MyUserDetails(user);
		}
		return null;
	}
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}
	public User save(User user){
		User dbUser = dao.findByUsername(user.getUsername());
		if (dbUser!=null){
			user.setId(dbUser.getId());
		}
		System.out.println(user.getUsername());
		int rtn = dao.update(user);
		if (rtn ==0){
			rtn = dao.insert(user);
		}
		return user;
	}
}
