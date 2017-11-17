package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	@RequestMapping(path="/get")
	public @ResponseBody Object get(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null){
			MyUserDetails user = (MyUserDetails) authentication.getDetails();
			User dbUser = service.findByUsername(user.getUsername());
			return dbUser;
		}
		return null;
	}
	@RequestMapping(path="/get/{name}")
	public @ResponseBody User get( @PathVariable("name") String userName)
	{
		return service.findByUsername(userName);
	}

	@RequestMapping("/save")
	public @ResponseBody User save(User user){
//		user.setId(1l);
		service.save(user);
		return user;
	}
}
