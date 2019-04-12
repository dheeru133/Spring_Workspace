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

@Component
public class JwtValidator {

	private final String secret = "youtube";

	public JwtUser validate(String token) {

		JwtUser jwtUser = null;
		try {
			final Claims claims = Jwts	.parser()
										.setSigningKey(this.secret)
										.parseClaimsJws(token)
										.getBody();

			jwtUser = new JwtUser();
			jwtUser.setUserName(claims.getSubject());
			jwtUser.setId(Long.parseLong((String) claims.get("userId")));
			jwtUser.setRole((String) claims.get("role"));
		} catch (final Exception e) {
			System.out.println(e);
		}

		return jwtUser;
	}

}
