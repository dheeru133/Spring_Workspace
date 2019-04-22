/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.models;

public class Rating {

	private String	rating;
	private int		movieId;

	/**
	 * @return the rating
	 */
	public String getRating() {
		return this.rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the movieId
	 */
	public int getMovieId() {
		return this.movieId;
	}

	/**
	 * @param movieId
	 *            the movieId to set
	 */
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public Rating(String rating, int movieId) {
		super();
		this.rating = rating;
		this.movieId = movieId;
	}

}
