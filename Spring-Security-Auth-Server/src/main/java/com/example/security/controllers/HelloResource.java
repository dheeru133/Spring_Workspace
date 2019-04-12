/** * Copyright (c) * @author TCS * */
package com.example.security.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

	@GetMapping("/principal")
	public Principal user(Principal principal) {
		System.out.println("user info                   " + principal.getName());
		return principal;
	}

	/**
	 * Hello. In order to access this rest endpoint the user should have tod gos
	 * from Authentication and Authorization
	 *
	 * @return the string
	 */
	@GetMapping("/hello")
	public String hello() {
		return "Hello Spring security from Server  - after authenticate and authorize";
	}
}
