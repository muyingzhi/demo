package com.example.demo.auth.service;

import com.example.demo.auth.SysUser;
import com.example.demo.auth.UserRoles;
import com.example.demo.auth.mapper.UserDao;
import com.example.demo.auth.mapper.UserRolesDao;
import com.example.demo.security.MyGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserDao dao;
	@Autowired
	private UserRolesDao userRolesDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = this.findByUsername(username);
		return new User(username,sysUser.getPassword(),this.findAuthority(username));
	}
	public SysUser findByUsername(String username) {
		return dao.findByUsername(username);
	}

	public SysUser save(SysUser user){
		SysUser dbUser = dao.findByUsername(user.getUsername());
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

	public int addRoles(String userName,String ... roles){
		Collection<UserRoles> urs = userRolesDao.getByUserName(userName);

		if (urs.size() == 0){
			UserRoles ur = new UserRoles();
			ur.setUserName(userName);
			ur.setRoles(this.toStr(roles));
			return userRolesDao.insert(ur);
		}

		if (!this.hasRoles(urs, roles)){
			UserRoles ur = urs.iterator().next();
			String s = ur.getRoles();
			if (s!=null &&  !"".equals(s)){
				s += "," + this.toStr(roles);
			} else {
				s = this.toStr(roles);
			}
			ur.setRoles(s);
			return userRolesDao.update(ur);
		}
		return 0;
	}

	public int removeRole(String userName, String role){
		Collection<UserRoles> urs = userRolesDao.getByUserName(userName);

		if (urs == null){
			return 0;
		}

		if (this.hasRoles(urs, role)){
			Iterator<UserRoles> itr = urs.iterator();
			int ii=0;
			while (itr.hasNext()){
				UserRoles ur = itr.next();
				String roles = ur.getRoles();
				if (roles!=null && roles.contains(role)) {
					roles = roles.replace( role+",", "");
					roles = roles.replace( ","+role, "");
					roles = roles.replace( role, "");//----确保逗号去掉
					ur.setRoles(roles);
					ii += userRolesDao.update(ur);
				}
			}
			return ii;
		}
		return 0;
	}

	private String toStr(String ... roles){
		StringBuffer sb = new StringBuffer();
		for(String one : roles){
			sb.append(one+",");
		}

		return sb.toString().substring(0,sb.length() - 1);
	}
	private boolean hasRoles(Collection<UserRoles> urs, String ... roles){
		//----检查是否已经存在此角色
		boolean flag = false;
		for (UserRoles ur : urs){
			for (String one: roles){
				if (ur.getRoles()!=null && ur.getRoles().contains(one)){
					flag = true;
				}
			}
		}
		return flag;
	}
	private Collection<MyGrantedAuthority> findAuthority(String username){
		Collection<MyGrantedAuthority> c = new ArrayList<MyGrantedAuthority>();

		Collection<UserRoles> urs = userRolesDao.getByUserName(username);
		for(UserRoles ur : urs){
			String[] roles = ur.getRoles().split(",");
			for( String roleName : roles){
				MyGrantedAuthority ua = new MyGrantedAuthority();
				ua.setAuthority(roleName);

				c.add(ua);
			}
		}
		return c;
	}
}
