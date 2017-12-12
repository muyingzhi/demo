package com.example.demo.auth.web;

import com.example.demo.auth.SysUser;
import com.example.demo.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	@RequestMapping(path="/")
	public @ResponseBody Object get(Principal user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null){
//			MyUserDetails details = (MyUserDetails)user;// authentication.getPrincipal();

			SysUser dbUser = service.findByUsername(user.getName());
			return dbUser;
		}
		return null;
	}
	@RequestMapping(path="/get/{name}")
	public @ResponseBody
	SysUser get(@PathVariable("name") String userName)
	{
		return service.findByUsername(userName);
	}

	@RequestMapping("/save")
	public @ResponseBody
	SysUser save(SysUser user){
		service.save(user);
		return user;
	}
}
