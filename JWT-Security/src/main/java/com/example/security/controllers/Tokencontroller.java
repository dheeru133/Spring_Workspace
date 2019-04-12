/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.model.JwtUser;
import com.example.security.securityconfig.JwtGenerator;

@RestController
@RequestMapping("/token")
public class Tokencontroller {

	@PostMapping
	public String generate(@RequestBody final JwtUser user) {

		final JwtGenerator jwtGenerator = new JwtGenerator();
		return jwtGenerator.generate(user);

	}

}
