/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.models.Rating;
import com.example.microservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

	// @GetMapping("/{movieId}")
	// public Rating getMovieInfo(@PathVariable("movieId") int movieId) {
	//
	// return new Rating("Five star", movieId);
	//
	// }

	@GetMapping("/{userId}")
	public UserRating getMovieInfoRating(@PathVariable("userId") int userId) {

		final List<Rating> ratings = Arrays.asList(
				new Rating("4 rating", 1),
				new Rating("5 rating", 2),
				new Rating("2 rating", 3));

		return new UserRating(ratings);

	}

}
