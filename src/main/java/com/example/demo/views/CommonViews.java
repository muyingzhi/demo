package com.example.demo.views;

import com.example.demo.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Map;

/**
 * Created by muyz on 2017/11/16.
 */
@Controller
@RequestMapping("/")
public class CommonViews {
//    @RequestMapping("/")
//    public String index( Map<String, Object> map){
//        return "index";
//    }
    @RequestMapping("/main")
    public String main(ServletRequest request, ServletResponse response, Map<String, Object> map){
        map.put("user",SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return "main";
    }
//    @RequestMapping("/login")
//    public String login(ServletRequest request, ServletResponse response, Map<String, Object> map){
//
//        return "login";
//    }
}
