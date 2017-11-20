package com.example.demo.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.example.demo.auth.User;
import com.example.demo.security.MyGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails{
	private long expires;
	private String username;
	private String password;
	private boolean enabled;
	private boolean accountNonLocked;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	public MyUserDetails(){}
	public MyUserDetails(String username){
		this.username = username;
	}
	public MyUserDetails(String username,String password){
		this.username = username;
		this.password = password;
	}
	public MyUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		MyGrantedAuthority ua = new MyGrantedAuthority();
		ua.setAuthority("ROLE_ADMIN");
		Collection<GrantedAuthority> c = new ArrayList<GrantedAuthority>();
		c.add(ua);
		return c;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
