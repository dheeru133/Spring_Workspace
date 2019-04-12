/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web	.ignoring()
			.antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests()
		// .antMatchers("/")
		// .permitAll()
		// .antMatchers("/user/getEmployeesList")
		// .hasAnyRole("ADMIN")
		// .anyRequest()
		// .authenticated()
		// .and()
		// .formLogin()
		// .permitAll()
		// .and()
		// .logout()
		// .permitAll();

		http.authorizeRequests()
			.antMatchers("/")
			.permitAll()
			.antMatchers("/user/getEmployeesList")
			.hasAnyRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.permitAll()
			.and()
			.logout()
			.permitAll();

		http.csrf()
			.disable();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {

		final String password = passwordEncoder().encode("admin");
		authenticationMgr	.inMemoryAuthentication()
							.withUser("admin")
							.password(password)
							.roles("ADMIN");

		// authenticationMgr .inMemoryAuthentication()
		// .withUser("admin")
		// .password("{noop}admin")
		// .authorities("ROLE_ADMIN");

		// authenticationMgr .inMemoryAuthentication()
		// .withUser("admin")
		// .password(encoder.encode("admin"))
		// .roles("ADMIN");
		// User.withDefaultPasswordEncoder()
		// .username("user")
		// .password("password")
		// .roles("user")
		// .build();
	}
}