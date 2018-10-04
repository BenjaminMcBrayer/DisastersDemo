package com.DisastersDemo.entity.nasa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
	@JsonProperty
	private String id;
	@JsonProperty
	private String title;

	public Category() {
	}

	/**
	 * @param id
	 * @param title
	 */
	public Category(String id, String title) {
		this.id = id;
		this.title = title;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + ", title=" + title + "]";
	}
}
