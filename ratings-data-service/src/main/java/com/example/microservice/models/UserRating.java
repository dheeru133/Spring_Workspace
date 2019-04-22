/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.models;

import java.util.List;

public class UserRating {

	private List<Rating> userRating;

	/**
	 * @return the userRating
	 */
	public List<Rating> getUserRating() {
		return this.userRating;
	}

	/**
	 * @param userRating
	 *            the userRating to set
	 */
	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}

	public UserRating(List<Rating> userRating) {
		super();
		this.userRating = userRating;
	}

	public UserRating() {
		super();
		// TODO Auto-generated constructor stub
	}

}
