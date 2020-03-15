package com.client.feign.feignClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.client.feign.feignClient")
public class FeignClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}

}
