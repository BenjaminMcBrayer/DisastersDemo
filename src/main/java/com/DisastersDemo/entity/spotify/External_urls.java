package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class External_urls {

	@JsonProperty
	private String spotify;

	/**
	 * 
	 */
	public External_urls() {
	}

	/**
	 * @param spotify
	 */
	public External_urls(String spotify) {
		this.spotify = spotify;
	}

	/**
	 * @return the spotify
	 */
	public String getSpotify() {
		return spotify;
	}

	/**
	 * @param spotify the spotify to set
	 */
	public void setSpotify(String spotify) {
		this.spotify = spotify;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "External_urls [spotify=" + spotify + "]";
	}
}
