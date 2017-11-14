package com.example.demo.security;

import com.example.demo.user.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by muyz on 2017/11/13.
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public MyUsernamePasswordAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        super();
        this.tokenService = tokenAuthenticationService;
    }
    @Autowired
    private TokenAuthenticationService tokenService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MyUserDetails user = null;
        if ("application/x-www-form-urlencoded".equals(request.getHeader("content-type"))){
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

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        //----这里只要一个用户名即可
//        final MyUserDetails authenticatedUser = new MyUserDetails(authResult.getName());
//        final MyAuthentication userAuthentication = new MyAuthentication(true,authenticatedUser);
//        //-----实际在向response的headers添加一个认证信息token
//        tokenService.addAuthentication(response, userAuthentication);
//        if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
//            response.getWriter().println("{\"code\":0}");
//        }
//        //-----写入,在授权阶段使用
//        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
//    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
////        super.unsuccessfulAuthentication(request, response, failed);
//        if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
//            PrintWriter writer = response.getWriter();
//            writer.write("{\"code\": -1 , \"message\": \""+failed.getMessage()+"\"}");
//            writer.flush();
//            writer.close();
//        } else {
//            RequestCache requestCache = new HttpSessionRequestCache();
//            SavedRequest savedRequest = requestCache.getRequest(request,response);
//            if(savedRequest!=null) {
//                response.sendRedirect(savedRequest.getRedirectUrl());
//            }else
//                response.sendRedirect("");
//
//        }
//    }
}
