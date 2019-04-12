/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients	.inMemory()
				.withClient("ClientId")
				.secret("{noop}secret")
				.authorizedGrantTypes("authorization_code")
				.scopes("read")
				.authorities("CLIENT")
				.autoApprove(true)
				.redirectUris("http://localhost:8084/ui/secure");
	}
}