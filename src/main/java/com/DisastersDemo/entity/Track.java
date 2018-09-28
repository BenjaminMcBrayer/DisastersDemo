package com.DisastersDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
	private String name;
	private String duration;
	private String url;
	@JsonProperty("@attr")
	private Attribution attribution;

	/**
	 * 
	 */
	public Track() {
	}

	/**
	 * @param name
	 * @param duration
	 * @param url
	 * @param attribution
	 */
	public Track(String name, String duration, String url, Attribution attribution) {
		this.name = name;
		this.duration = duration;
		this.url = url;
		this.attribution = attribution;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the attribution
	 */
	public Attribution getAttribution() {
		return attribution;
	}

	/**
	 * @param attribution the attribution to set
	 */
	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Track [name=" + name + ", duration=" + duration + ", url=" + url + ", attribution=" + attribution + "]";
	}
}
