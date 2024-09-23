package com.krakenflex.technicaltest.demo.config;

import com.krakenflex.technicaltest.demo.interceptors.RestTemplateLogInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestTemplate;

@Configuration
public class MockOutageConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .interceptors(new RestTemplateLogInterceptor())
                .build();

    }
}
