package com.example.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by muyz on 2017/11/13.
 */
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public MyAuthenticationFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\": -1 , \"message\": \""+exception.getMessage()+"\"}");
            writer.flush();
            writer.close();
        } else {
            super.onAuthenticationFailure(request, response, exception);
//            request.setAttribute("exception", exception);
//
//            RequestCache requestCache = new HttpSessionRequestCache();
//            SavedRequest savedRequest = requestCache.getRequest(request,response);
//
//            if(savedRequest!=null) {
//                response.sendRedirect(savedRequest.getRedirectUrl());
////                request.getRequestDispatcher(savedRequest.getRedirectUrl()).forward(request,response);
//            }
//            else
//                response.sendRedirect(defaultFailureUrl);
////                request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
        }
    }
}
