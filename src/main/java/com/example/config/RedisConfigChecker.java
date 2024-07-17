package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfigChecker {

    @Value("${spring.redis.host}")
    private String redisHost;

    @PostConstruct
    public void checkConfig() {
        System.out.println("Configured Redis Host: " + redisHost);
    }
}
