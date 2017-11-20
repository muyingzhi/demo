package com.example.demo.security;

import com.example.demo.auth.service.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by muyz on 2017/11/7.
 */
public class MyAuthentication implements Authentication {
    private boolean authenticated = false;
    private MyUserDetails userDetails;
    private Collection<MyGrantedAuthority> authorities = null;
    public MyAuthentication(boolean authenticated, MyUserDetails userDetails) {
        this.authenticated = authenticated;
        this.userDetails = userDetails;
        authorities = new ArrayList<MyGrantedAuthority>();
        MyGrantedAuthority ga = new MyGrantedAuthority();
        ga.setAuthority("ROLE_ADMIN");
        authorities.add(ga);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public MyUserDetails getDetails() {
        return this.userDetails;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }
}
