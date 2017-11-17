package com.example.demo.user.mapper;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by muyz on 2017/11/15.
 */
public class UserAuthority implements GrantedAuthority {
    private String grant;

    public UserAuthority(String grant) {
        this.grant = grant;
    }

    @Override
    public String getAuthority() {
        return grant;
    }
}
