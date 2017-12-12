package com.example.demo.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by muyz on 2017/11/15.
 */
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {
    private RequestMatcher requestMatcher;

    public MyFilterSecurityInterceptor(RequestMatcher requestMatcher,
                                       FilterInvocationSecurityMetadataSource metadataSource,
                                       AccessDecisionManager accessDecisionManager) {

        this.requestMatcher = requestMatcher;
        super.setSecurityMetadataSource(metadataSource);
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilter(request, response, chain);
        System.out.println("interceptor do filter ....");


        if (requestMatcher.matches((HttpServletRequest)request)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object obj = authentication.getPrincipal();
            if (!(obj instanceof UserDetails)) {
                return;
            }
            UserDetails user = (UserDetails)obj;
            //----用户拥有的角色
            Collection userGranded = user.getAuthorities();
            // TODO: 2017/12/12  有待完善此鉴权过程
            //----当前request需要的角色，
            //-------在cache里保存所有有权限要求的url
            //-------request的url是否在cache里有对应
            //-------找出需要的角色

            //----判断是否用户角色包含所需角色
            //-------不完全包含，则禁止访问
//            throw new AccessDeniedException("禁止访问");
        }
        chain.doFilter(request, response);

    }
}
