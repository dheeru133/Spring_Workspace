/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.controllers;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SocialFacebookController {

	private final FacebookConnectionFactory factory = new FacebookConnectionFactory("2314904668770938",
			"78f56da7e9a3171b249acb8bb06ac3d3");

	@RequestMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("welcome");
	}

	@GetMapping(value = "/useApplication")
	public String producer() {

		final OAuth2Operations operations = this.factory.getOAuthOperations();
		final OAuth2Parameters params = new OAuth2Parameters();

		params.setRedirectUri("http://localhost:8010/ui/forwardLogin");
		params.setScope("email,public_profile");

		final String url = operations.buildAuthenticateUrl(params);
		System.out.println("The URL is" + url);
		return "redirect:" + url;

	}

	@RequestMapping(value = "/forwardLogin")
	public ModelAndView prodducer(@RequestParam("code") String authorizationCode) {
		final OAuth2Operations operations = this.factory.getOAuthOperations();
		final AccessGrant accessToken = operations.exchangeForAccess(authorizationCode,
				"http://localhost:8010/ui/forwardLogin",
				null);

		final Connection<Facebook> connection = this.factory.createConnection(accessToken);
		final Facebook facebook = connection.getApi();
		final String[] fields = { "id", "email", "first_name", "last_name" };
		final User userProfile = facebook.fetchObject("me", User.class, fields);
		final ModelAndView model = new ModelAndView("details");
		model.addObject("user", userProfile);
		return model;

	}

}