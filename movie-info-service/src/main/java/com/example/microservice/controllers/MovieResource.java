/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@GetMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") int movieId) {

		return new Movie("Harry Potter", movieId);

	}

}
