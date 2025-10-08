package com.dp.vstore_productservice.configs;

import com.dp.vstore_productservice.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/api/products/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/products/add").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/products/update").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/delete").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                );
        return http.build();
    }
}
