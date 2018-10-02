package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSpotifyWrapper {

	@JsonProperty
	private Album album;

	/**
	 * 
	 */
	public JsonSpotifyWrapper() {
	}

	/**
	 * @param album
	 */
	public JsonSpotifyWrapper(Album album) {
		this.album = album;
	}

	/**
	 * @return the album
	 */
	public Album getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JsonSpotifyWrapper [album=" + album + "]";
	}
}
