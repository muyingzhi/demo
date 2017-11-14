package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by muyz on 2017/11/7.
 */
public class RestExceptionTranslationFilter extends ExceptionTranslationFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionTranslationFilter.class);

    public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException{
        super.doFilter(request, response, chain);
    }

    @Override
    protected void sendStartAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, AuthenticationException reason)
            throws ServletException, IOException {

        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (isAjax) {

            String jsonObject = "{\"message\":\"Please login first.\"," + "\"access-denied\":true,\"cause\":\"AUTHENTICATION_FAILURE\"}";
            String contentType = "application/json";
            resp.setContentType(contentType);
            PrintWriter out = resp.getWriter();
            out.print(jsonObject);
            out.flush();
            out.close();
            return;
        }

        super.sendStartAuthentication(req, resp, chain, reason);
    }

}
