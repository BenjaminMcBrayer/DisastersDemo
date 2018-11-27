package com.DisastersDemo.entity.spotify;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks {
	
	@JsonProperty
	private ArrayList<TrackObject> items;

	public Tracks() {
		
	}

	public Tracks(ArrayList<TrackObject> items) {
		this.items = items;
	}

	public ArrayList<TrackObject> getItems() {
		return items;
	}

	public void setItems(ArrayList<TrackObject> items) {
		this.items = items;
	}
}
