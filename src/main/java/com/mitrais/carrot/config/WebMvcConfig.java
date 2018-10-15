package com.mitrais.carrot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc configuration for cors mapping
 * 
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * set max age secs
     */
    public static final long MAX_AGE_SECS = 3600;

    /**
     * cors mapping
     *
     * @param registry CorsRegistry object to set the cors config
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
}
