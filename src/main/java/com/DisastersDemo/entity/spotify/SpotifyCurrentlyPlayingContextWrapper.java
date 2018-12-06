package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyCurrentlyPlayingContextWrapper {

	private Boolean is_playing;
	private String currently_playing_type;

	public SpotifyCurrentlyPlayingContextWrapper() {

	}

	public SpotifyCurrentlyPlayingContextWrapper(Boolean is_playing, String currently_playing_type) {
		this.is_playing = is_playing;
		this.currently_playing_type = currently_playing_type;
	}

	public Boolean getIs_playing() {
		return is_playing;
	}

	public void setIs_playing(Boolean is_playing) {
		this.is_playing = is_playing;
	}

	public String getCurrently_playing_type() {
		return currently_playing_type;
	}

	public void setCurrently_playing_type(String currently_playing_type) {
		this.currently_playing_type = currently_playing_type;
	}

	@Override
	public String toString() {
		return "SpotifyCurrentlyPlayingContextWrapper [is_playing=" + is_playing + ", currently_playing_type="
				+ currently_playing_type + "]";
	}

}
