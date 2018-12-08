package com.DisastersDemo.entity.lastfm;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopTags {
	@JsonProperty("tag")
	private Tag[] tags;

	public TopTags() {
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
