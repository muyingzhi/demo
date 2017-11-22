package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.auth.User;
import com.example.demo.auth.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	private UserService service;
	@Test
	public void contextLoads() {
	}
	@Test
	public void userSave(){
		User user = new User("muyz","123","穆医生","内科");
		service.save(user);
		System.out.println(user.toString());


	}
	@Test
	public void userFind() {
		UserDetails user = service.loadUserByUsername("muyz");
		assertNotEquals(user, null);
	}

}
