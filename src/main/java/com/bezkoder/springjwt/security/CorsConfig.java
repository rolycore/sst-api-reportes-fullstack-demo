package com.bezkoder.springjwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permitir todas las solicitudes desde cualquier origen
                registry.addMapping("/**"
                        )
                        .allowedOrigins("http://localhost:4200")//.allowedOrigins("https://appicmlab.icmetrologia.com/")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
