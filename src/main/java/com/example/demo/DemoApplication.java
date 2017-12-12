package com.example.demo;

import com.example.demo.auth.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@SpringBootApplication
public class DemoApplication {


	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

//		RedisTemplate template = ctx.getBean(RedisTemplate.class);
//		template.opsForValue().set("roles","001,002");
	}
//	@Autowired
//	private RedisTemplate redisTemplate;
	@Autowired
	private JedisConnectionFactory connectionFactory;
	@Bean
	public CommandLineRunner t1(){
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				RedisTemplate template = new StringRedisTemplate();
				template.setConnectionFactory(connectionFactory);
				template.setValueSerializer(new Jackson2JsonRedisSerializer<SysUser>(SysUser.class));
				template.afterPropertiesSet();

				SysUser user = new SysUser();
				user.setUsername("xxxxx");
				user.setPassword("123");
				template.opsForValue().set("user",user);
//				template.opsForValue().set("roles","{\"code\",\"003\"}");
//				System.out.println(template.opsForValue().get("roles"));
			}
		};
	}
}
