package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by muyz on 2017/11/16.
 */
@Configuration
@EnableRedisHttpSession

public class RedisConfig {
    @Bean
    @Primary
    public JedisConnectionFactory connectionFactory(){
        return new JedisConnectionFactory();
    }
    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory factory){
        final StringRedisTemplate template = new StringRedisTemplate(factory);

        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
