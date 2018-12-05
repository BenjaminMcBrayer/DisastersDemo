package com.DisastersDemo.entity.spotify;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackObject {

	@JsonProperty
	private Album album;
	@JsonProperty
	private ArrayList<Artist> artists;
	@JsonProperty
	private String href;
	@JsonProperty
	private String id;
	@JsonProperty
	private String name;
	@JsonProperty
	private boolean is_local;
	@JsonProperty
	private boolean is_playable;
	@JsonProperty
	private Long popularity;
	@JsonProperty
	private String uri;

	public TrackObject() {
	}

	public TrackObject(Album album, ArrayList<Artist> artists, String href, String id, String name, boolean is_local,
			boolean is_playable, Long popularity, String uri) {
		this.album = album;
		this.artists = artists;
		this.href = href;
		this.id = id;
		this.name = name;
		this.is_local = is_local;
		this.is_playable = is_playable;
		this.popularity = popularity;
		this.uri = uri;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public ArrayList<Artist> getArtists() {
		return artists;
	}

	public void setArtists(ArrayList<Artist> artists) {
		this.artists = artists;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIs_local() {
		return is_local;
	}

	public void setIs_local(boolean is_local) {
		this.is_local = is_local;
	}

	public boolean isIs_playable() {
		return is_playable;
	}

	public void setIs_playable(boolean is_playable) {
		this.is_playable = is_playable;
	}

	public Long getPopularity() {
		return popularity;
	}

	public void setPopularity(Long popularity) {
		this.popularity = popularity;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "TrackObject [album=" + album + ", artists=" + artists + ", href=" + href + ", id=" + id + ", name="
				+ name + ", is_local=" + is_local + ", is_playable=" + is_playable + ", popularity=" + popularity
				+ ", uri=" + uri + "]";
	}
	
	
}
