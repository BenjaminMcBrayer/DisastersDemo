package com.DisastersDemo.entity;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	private String id;
	private String title;
	private String description;
	private String link;
	private ArrayList<Category> categories;
	private ArrayList<Geometry> geometries;

	public Event() {
	}

	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param link
	 * @param categories
	 * @param geometries
	 */
	public Event(String id, String title, String description, String link, ArrayList<Category> categories,
			ArrayList<Geometry> geometries) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.categories = categories;
		this.geometries = geometries;
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

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return
	 */
	public ArrayList<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 */
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	/**
	 * @return
	 */
	public ArrayList<Geometry> getGeometries() {
		return geometries;
	}

	/**
	 * @param geometries
	 */
	public void setGeometries(ArrayList<Geometry> geometries) {
		this.geometries = geometries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", description=" + description + ", link=" + link
				+ ", categories=" + categories + ", geometries=" + geometries + "]";
	}
}
