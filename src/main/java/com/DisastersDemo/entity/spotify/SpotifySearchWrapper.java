package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchWrapper {

	@JsonProperty
	private Tracks tracks;

	public SpotifySearchWrapper() {
	}

	public SpotifySearchWrapper(Tracks tracks) {
		this.tracks = tracks;
	}

	public Tracks getTracks() {
		return tracks;
	}

	public void setTracks(Tracks tracks) {
		this.tracks = tracks;
	}
}
