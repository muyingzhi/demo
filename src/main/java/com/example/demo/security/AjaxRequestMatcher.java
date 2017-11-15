package com.example.demo.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by muyz on 2017/11/15.
 */
public class AjaxRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Request-With")) ||
                request.getHeader("Accept")!=null &&
                request.getHeader("Accept").contains("application/json");
    }
}
