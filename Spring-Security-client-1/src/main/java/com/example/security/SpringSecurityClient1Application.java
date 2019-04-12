/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.example.security.controllers" })
public class SpringSecurityClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityClient1Application.class, args);
	}

}
