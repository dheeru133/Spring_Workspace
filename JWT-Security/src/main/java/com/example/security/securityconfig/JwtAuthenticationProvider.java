/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.securityconfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.security.model.JwtAuthenticationToken;
import com.example.security.model.JwtUser;
import com.example.security.model.JwtUserDetails;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtValidator validator;

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken userNamePasswordAuthToken)
			throws AuthenticationException {
		// TODO Auto-generated method stub

		final JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) userNamePasswordAuthToken;
		final String token = jwtAuthenticationToken.getToken();
		final JwtUser jwtUser = this.validator.validate(token);

		// Check user exist or not
		if (jwtUser == null) {
			throw new RuntimeException("JWT token is incorect");
		}

		final List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
				jwtUser.getRole());

		return new JwtUserDetails(jwtUser.getUserName(), jwtUser.getId(), token, grantedAuthorities);

	}

}
