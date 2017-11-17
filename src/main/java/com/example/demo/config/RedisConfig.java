package com.example.demo.config;

import com.example.demo.user.User;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by muyz on 2017/11/16.
 */
@Configuration
@EnableRedisHttpSession

public class RedisConfig {
    @Bean
    public JedisConnectionFactory connectionFactory(){
        return new JedisConnectionFactory();
    }
    @Bean
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory factory){
        final StringRedisTemplate template = new StringRedisTemplate(factory);

//        template.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return template;
    }
}
