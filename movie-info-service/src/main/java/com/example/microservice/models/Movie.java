/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.models;

public class Movie {

	private String	name;
	private int		movieId;

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.movieId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.movieId = id;
	}

	public Movie(String name, int id) {
		super();
		this.name = name;
		this.movieId = id;
	}

}
