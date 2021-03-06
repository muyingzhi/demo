package com.example.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by muyz on 2017/11/10.
 */

public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public AjaxAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
            throws IOException, ServletException {
        System.out.println("--------处理了异常"+arg2.getMessage());

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized.");


    }

}