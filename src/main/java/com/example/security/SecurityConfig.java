package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UUIDAuthenticationFilter uuidAuthenticationFilter;

    public SecurityConfig(UUIDAuthenticationFilter uuidAuthenticationFilter) {
        this.uuidAuthenticationFilter = uuidAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/auth", "/health").permitAll()
            .antMatchers("/protected").authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(uuidAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
