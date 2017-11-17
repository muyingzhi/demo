package com.example.demo.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Created by muyz on 2017/11/17.
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
}
