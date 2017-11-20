package com.example.demo.security;

import com.example.demo.auth.service.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by muyz on 2017/11/13.
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public MyAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.tokenService = tokenAuthenticationService;
    }
    @Autowired
    private TokenAuthenticationService tokenService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MyUserDetails user = null;
        RequestHeaderRequestMatcher matcher = new RequestHeaderRequestMatcher("content-type","application/x-www-form-urlencoded");
        if (matcher.matches(request)){
            user = new MyUserDetails(request.getParameter("username"),request.getParameter("password"));
        } else {
            try {
                user = new ObjectMapper().readValue(request.getInputStream(), MyUserDetails.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //-----组织验证用的token
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        return getAuthenticationManager().authenticate(loginToken);
    }
}
