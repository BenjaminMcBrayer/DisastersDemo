package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

	@JsonProperty
	private External_urls external_urls;

	/**
	 * 
	 */
	public Album() {
	}

	/**
	 * @param external_urls
	 */
	public Album(External_urls external_urls) {
		this.external_urls = external_urls;
	}

	/**
	 * @return the external_urls
	 */
	public External_urls getExternal_urls() {
		return external_urls;
	}

	/**
	 * @param external_urls the external_urls to set
	 */
	public void setExternal_urls(External_urls external_urls) {
		this.external_urls = external_urls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Album [external_urls=" + external_urls + "]";
	}
}
