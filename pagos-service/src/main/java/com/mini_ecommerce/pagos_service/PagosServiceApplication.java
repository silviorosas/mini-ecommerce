package com.mini_ecommerce.pagos_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PagosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagosServiceApplication.class, args);
	}

}
