package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class AuthController {

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    private final RedisTemplate<String, String> redisTemplate;

    public AuthController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/auth")
    public String authenticate(@RequestHeader("clientId") String clientIdHeader,
                               @RequestHeader("clientSecret") String clientSecretHeader) {
        if (clientId.equals(clientIdHeader) && clientSecret.equals(clientSecretHeader)) {
            String token = UUID.randomUUID().toString();
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(token, "valid", 1, TimeUnit.HOURS);
            return "{ \"type\": \"Bearer\", \"accessToken\": \"" + token + "\" }";
        } else {
            return "Invalid clientId or clientSecret";
        }
    }
}
