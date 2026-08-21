package com.deadlinenote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;
import java.util.*;

@Configuration
public class SecurityConfig {
    @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf->csrf.disable()).cors(cors->{}).authorizeHttpRequests(auth->auth.anyRequest().permitAll()).build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173}") String origins){
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowedOrigins(Arrays.stream(origins.split(",")).map(String::trim).toList());
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type","X-User-Email","Authorization"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();source.registerCorsConfiguration("/**",config);return source;
    }
}
