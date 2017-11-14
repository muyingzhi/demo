package com.example.demo.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Created by muyz on 2017/11/13.
 */
public class MyLogoutFilter extends LogoutFilter {
    public MyLogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
        super(logoutSuccessUrl, handlers);
    }
}
