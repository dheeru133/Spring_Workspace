/**
 * Copyright (c)
 * @author TCS
 *
 */
/***Copyright(c)*@author TCS**/
package com.example.security.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView getEmployeeInfo() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/secure", method = RequestMethod.GET)
	public ModelAndView showEmployees(@RequestParam("code") String code) throws JsonProcessingException, IOException {
		ResponseEntity<String> response = null;
		System.out.println("Authorization Code------" + code);

		final RestTemplate restTemplate = new RestTemplate();

		// According OAuth documentation we need to send the client id and secret key in
		// the header for authentication
		final String credentials = "ClientId:secret";
		final String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		final HttpEntity<String> request = new HttpEntity<>(headers);

		String access_token_url = "http://localhost:8083/auth/oauth/token";
		access_token_url += "?code=" + code;
		access_token_url += "&grant_type=authorization_code";
		access_token_url += "&redirect_uri=http://localhost:8084/ui/secure";

		response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

		// Get the Access Token From the recieved JSON response
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode node = mapper.readTree(response.getBody());
		final String token = node	.path("access_token")
									.asText();

		System.out.println("Access Token  ---------" + token);

		final String url = "http://localhost:8083/auth/user/getEmployeesList";

		// Use the access token for authentication
		final HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Authorization", "Bearer " + token);
		final HttpEntity<String> entity = new HttpEntity<>(headers1);

		final ResponseEntity<Employee[]> employees = restTemplate.exchange(url, HttpMethod.GET, entity,
				Employee[].class);
		System.out.println(employees);
		final Employee[] employeeArray = employees.getBody();

		final ModelAndView model = new ModelAndView("secure");
		model.addObject("employees", Arrays.asList(employeeArray));
		System.out.println(
				"Employees ----------------" + employeeArray[0].getEmpId() + "-----" + employeeArray[0].getEmpName());
		return model;

	}
}