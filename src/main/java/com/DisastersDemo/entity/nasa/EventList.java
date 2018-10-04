package com.DisastersDemo.entity.nasa;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventList {
	@JsonProperty
	private String id;
	@JsonProperty
	private String title;
	@JsonProperty
	private String description;
	@JsonProperty
	private String link;
	@JsonProperty
	private ArrayList<Event> events;

	public EventList() {
	}

	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param link
	 * @param events
	 */
	public EventList(String id, String title, String description, String link, ArrayList<Event> events) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.events = events;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventList [id=" + id + ", title=" + title + ", description=" + description + ", link=" + link
				+ ", events=" + events + "]";
	}
}
