package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
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
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public MyAuthenticationSuccessHandler() {
    }

    public MyAuthenticationSuccessHandler(String successfulUrl, TokenAuthenticationService tokenService) {
        super(successfulUrl);
        this.tokenService = tokenService;
    }

    private TokenAuthenticationService tokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //-----实际在向response的headers添加一个认证信息token
        String token = tokenService.addAuthentication(response, authentication);

        if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
            // -- ajax请求，放回json
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\": 0 , \"message\": \"ok\" , \"token\":\""+token+"\"}");
            writer.flush();
            writer.close();
        } else {
            //---其它的，按框架方式执行
            super.onAuthenticationSuccess(request, response, authentication);

        }
    }
}
