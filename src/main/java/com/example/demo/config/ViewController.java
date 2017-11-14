package com.example.demo.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by muyz on 2017/11/8.
 */
@Controller
@RequestMapping("/")
public class ViewController {
    @RequestMapping(path = {"/main","/tmain"})
    public ModelAndView tmain(){
        ModelAndView view = new ModelAndView("main");
        return view;
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "index";
    }
}
