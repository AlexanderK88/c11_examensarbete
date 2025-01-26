package com.example.c11_examensarbete;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;

@Configuration
@EnableRetry
@EnableCaching
public class ApplicationConfig {

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}
