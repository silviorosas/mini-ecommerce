package com.mini_ecommerce.ordenes_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Bean
    @LoadBalanced // <--- Clave para que entienda que "pagos-service" es un microservicio
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
