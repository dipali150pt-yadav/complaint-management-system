package com.cms.complaintsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/home", "/track", "/submit", "/uploads/**", "/css/**", "/js/**", "/error").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable());

        return http.build();
    }
}