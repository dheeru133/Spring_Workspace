/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.securityconfig.JwtAuthenticationEntryPoint;
import com.example.security.securityconfig.JwtAuthenticationProvider;
import com.example.security.securityconfig.JwtAuthenticationTokenFilter;
import com.example.security.securityconfig.JwtSucessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationProvider	authenticationProvider;
	@Autowired
	private JwtAuthenticationEntryPoint	entryPoint;

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFiler() {

		final JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtSucessHandler());
		return filter;

	}

	@Override
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(this.authenticationProvider));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("**/rest/**")
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(this.entryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authenticationTokenFiler(), UsernamePasswordAuthenticationFilter.class);

		http.headers()
			.cacheControl();
	}

}
