/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroserviceEurekaDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEurekaDiscoveryServerApplication.class, args);
	}

}
