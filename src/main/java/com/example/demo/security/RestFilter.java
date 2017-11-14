package com.example.demo.security;

import com.example.demo.user.MyUserDetails;
import com.example.demo.user.User;
import groovy.util.logging.Commons;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by muyz on 2017/11/7.
 */

public class RestFilter extends GenericFilterBean {

    private final  Logger log = LoggerFactory.getLogger(RestFilter.class);

    @Autowired
    private TokenAuthenticationService tokenService;

    public RestFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession(true);
        SecurityContext sc = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");

        //----验证token,实际上还是从session取登录的用户信息，
        if (sc!=null && sc.getAuthentication()!=null) {
            Authentication authentication = sc.getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
            return;
        }
        //-----null或验证的用户信息写入，达到token验证的目的
        SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(httpRequest));
        chain.doFilter(request, response);
    }
}
