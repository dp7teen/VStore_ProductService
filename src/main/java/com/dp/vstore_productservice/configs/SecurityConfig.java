package com.dp.vstore_productservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/api/products/search").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "api/products/rate").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "api/products/rate").permitAll()
                                .requestMatchers(HttpMethod.PUT, "api/products/rate").permitAll()
                                .anyRequest().permitAll()
                );
        return http.build();
    }
}
