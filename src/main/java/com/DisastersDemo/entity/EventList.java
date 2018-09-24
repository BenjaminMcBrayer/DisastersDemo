package com.DisastersDemo.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventList {
	private String title;
	private String description;
	private String link;
	private ArrayList<Event> events;

	public EventList() {
	}

	/**
	 * @param title
	 * @param description
	 * @param link
	 * @param events
	 */
	public EventList(String title, String description, String link, ArrayList<Event> events) {
		this.title = title;
		this.description = description;
		this.link = link;
		this.events = events;
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
	public ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * @param events
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
		return "EventList [title=" + title + ", description=" + description + ", link=" + link + ", events=" + events
				+ "]";
	}
}
