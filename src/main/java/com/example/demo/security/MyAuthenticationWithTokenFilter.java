package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

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

public class MyAuthenticationWithTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final  Logger log = LoggerFactory.getLogger(MyAuthenticationWithTokenFilter.class);

    private TokenAuthenticationService tokenService;

    public MyAuthenticationWithTokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public void setTokenService(TokenAuthenticationService tokenAuthenticationService){
        this.tokenService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!requiresAuthentication(req, res)) {
            chain.doFilter(request, response);

            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession(true);
        SecurityContext sc = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");

        if (sc!=null && sc.getAuthentication()!=null) {
            //----如果从session取登录的用户信息成功，就以此用户信息写入验证，
            Authentication authentication = sc.getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } else {
            //-----没有session的，从头"x-auth-token"获得token，通过算法从token获取用户信息，达到验证的目的
            SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(httpRequest));
            chain.doFilter(request, response);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
