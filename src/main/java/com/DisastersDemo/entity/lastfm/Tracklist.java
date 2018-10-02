package com.DisastersDemo.entity.lastfm;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracklist {
	@JsonProperty("track")
	private ArrayList<Track> tracks;
	@JsonProperty("@attr")
	private Attribution attribution;

	/**
	 * 
	 */
	public Tracklist() {
	}

	/**
	 * @param tracks
	 * @param attribution
	 */
	public Tracklist(ArrayList<Track> tracks, Attribution attribution) {
		this.tracks = tracks;
		this.attribution = attribution;
	}

	/**
	 * @return the tracks
	 */
	public ArrayList<Track> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
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
		return "Tracks [tracks=" + tracks + ", attribution=" + attribution + "]";
	}
}
