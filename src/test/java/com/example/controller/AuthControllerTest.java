package com.example.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @MockBean
    private ValueOperations<String, String> valueOperations;

    @Test
    @WithMockUser
    public void testAuth() throws Exception {
        String clientId = "yourClientId";
        String clientSecret = "yourClientSecret";
        String token = UUID.randomUUID().toString();

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.doNothing().when(valueOperations).set(Mockito.eq(token), Mockito.eq("valid"), Mockito.anyLong(), Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .header("clientId", clientId)
                .header("clientSecret", clientSecret)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("Bearer")))
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @WithMockUser
    public void testAuthInvalidCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .header("clientId", "invalidClientId")
                .header("clientSecret", "invalidClientSecret")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid clientId or clientSecret"));
    }
}
