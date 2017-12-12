package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails{
	private long expires;
	private String username;
	private String password;
	private Collection<MyGrantedAuthority> authorities;
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
		this.enabled = user.isEnabled();

		Collection<MyGrantedAuthority> c = new HashSet<>();
		for(GrantedAuthority ga: user.getAuthorities()) {
			MyGrantedAuthority myGrantedAuthority = new MyGrantedAuthority();
			myGrantedAuthority.setAuthority(ga.getAuthority());
			c.add(myGrantedAuthority);
		}
		this.setAuthorities(c);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAuthorities(Collection<MyGrantedAuthority> grantedAuthorities) {
		this.authorities = grantedAuthorities;
	}

	@Override
	public Collection<MyGrantedAuthority> getAuthorities() {
		return authorities;
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
