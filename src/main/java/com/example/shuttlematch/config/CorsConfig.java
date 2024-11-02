package com.example.shuttlematch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer{
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(
                "http://127.0.0.1:8081",
                "http://127.0.0.1:8080",
                "http://localhost:8081",
                "http://localhost:8080",
                "https://shuttle-match-be.onrender.com",
                "https://shuttlematch.io.vn",
                "https://shuttle-match.vercel.app",
                "http://localhost:5173",
                "http://localhost:5174"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;


    }

}
