package com.example.demo.security;

import com.example.demo.user.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by muyz on 2017/11/13.
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String successfulUrl ;
    public MyAuthenticationSuccessHandler() {

    }
    public MyAuthenticationSuccessHandler(String successfulUrl, TokenAuthenticationService tokenService) {
        this.tokenService = tokenService;
        this.successfulUrl = successfulUrl;
    }

    private TokenAuthenticationService tokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //----这里只要一个用户名即可
        final MyUserDetails authenticatedUser = new MyUserDetails(authentication.getName());
        final MyAuthentication userAuthentication = new MyAuthentication(true,authenticatedUser);

        //-----实际在向response的headers添加一个认证信息token
        String token = tokenService.addAuthentication(response, userAuthentication);

        //-----写入,在授权阶段使用
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);

        if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\": 0 , \"message\": \"ok\" , \"token\":\""+token+"\"}");
            writer.flush();
            writer.close();
        } else {
            request.setAttribute("token", token);

            RequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request,response);

            if(savedRequest!=null) {
                response.sendRedirect(savedRequest.getRedirectUrl());
            }
            else
                response.sendRedirect(successfulUrl);
        }
    }
}
