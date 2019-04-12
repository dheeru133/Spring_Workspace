/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.securityconfig;

import org.springframework.stereotype.Component;

import com.example.security.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generate(JwtUser user) {

		final Claims claim = Jwts	.claims()
									.setSubject(user.getUserName());
		claim.put("userId", String.valueOf(user.getId()));
		claim.put("role", user.getRole());

		return Jwts	.builder()
					.setClaims(claim)
					.signWith(SignatureAlgorithm.HS512, "youtube")
					.compact();

	}

}
