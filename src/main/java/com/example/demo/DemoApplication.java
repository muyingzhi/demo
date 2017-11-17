package com.example.demo;

import com.example.demo.user.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private RedisTemplate redisTemplate;
	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

//		RedisTemplate template = ctx.getBean(RedisTemplate.class);
//		template.opsForValue().set("roles","001,002");
	}
	@Bean
	public CommandLineRunner t1(){
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				redisTemplate.opsForValue().set("roles","{\"code\",\"003\"}");
				System.out.println(redisTemplate.opsForValue().get("roles"));
			}
		};
	}
}
