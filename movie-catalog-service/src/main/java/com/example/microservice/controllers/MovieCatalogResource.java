/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.microservice.models.CatalogItem;
import com.example.microservice.models.Movie;
import com.example.microservice.models.Rating;
import com.example.microservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	public List<CatalogItem>	catItem	= new ArrayList<>();
	@Autowired
	public RestTemplate			restTemplate;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		// Get all rated movie id

		final UserRating userRating = this.restTemplate.getForObject(
				"http://RATINGS-DATA-SERVICE/ratingsdata/" + userId, UserRating.class);

		// For each movie id call movie info
		for (final Rating rating : userRating.getUserRating()) {
			final Movie movie = this.restTemplate.getForObject(
					"http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class);
			this.catItem.add(
					new CatalogItem(movie.getName(), "Thats a great movie. Musts watch", rating.getRating()));
		}

		// Put them all together

		return this.catItem;

	}

}
