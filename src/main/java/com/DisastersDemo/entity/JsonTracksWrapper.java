package com.DisastersDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonTracksWrapper {
	@JsonProperty("tracks")
	private Tracklist tracklist;

	/**
	 * 
	 */
	public JsonTracksWrapper() {
	}

	/**
	 * @param tracklist
	 */
	public JsonTracksWrapper(Tracklist tracklist) {
		this.tracklist = tracklist;
	}

	/**
	 * @return the tracklist
	 */
	public Tracklist getTracklist() {
		return tracklist;
	}

	/**
	 * @param tracklist the tracklist to set
	 */
	public void setTracklist(Tracklist tracklist) {
		this.tracklist = tracklist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonTracksWrapper [tracklist=" + tracklist + "]";
	}
}
