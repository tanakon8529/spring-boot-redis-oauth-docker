package com.example.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    private final RedisTemplate<String, String> redisTemplate;

    public ProtectedController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/protected")
    public String checkToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            String value = ops.get(token);
            if (value != null) {
                return "200 OK - accessToken is available";
            }
        }
        return "401 Unauthorized - accessToken is expired or invalid";
    }
}
