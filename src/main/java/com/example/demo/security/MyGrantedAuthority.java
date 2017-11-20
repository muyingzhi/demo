package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by muyz on 2017/11/20.
 */
public class MyGrantedAuthority implements GrantedAuthority {
    private String authority;
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
