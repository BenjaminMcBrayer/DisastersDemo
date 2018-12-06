package com.DisastersDemo.entity.lastfm;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopTags {

	private Tag[] tags;

	public TopTags() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopTags(Tag[] tags) {
		this.tags = tags;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "TopTags [tags=" + Arrays.toString(tags) + "]";
	}
}
