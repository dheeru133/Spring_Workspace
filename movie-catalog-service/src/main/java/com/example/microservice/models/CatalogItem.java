/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.microservice.models;

public class CatalogItem {

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
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

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

	private String	name;
	private String	description;
	private String	rating;

	public CatalogItem(String name, String description, String rating) {
		super();
		this.name = name;
		this.description = description;
		this.rating = rating;
	}

}
