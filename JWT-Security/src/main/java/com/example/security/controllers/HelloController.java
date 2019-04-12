/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class HelloController {

	@GetMapping("/hello")
	public String getHello() {
		return "Hello World";
	}

}
