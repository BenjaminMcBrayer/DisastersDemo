package com.DisastersDemo.entity.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonTopTagsWrapper {

	private TopTags topTags;

	public JsonTopTagsWrapper() {

	}

	public JsonTopTagsWrapper(TopTags topTags) {
		this.topTags = topTags;
	}

	public TopTags getTopTags() {
		return topTags;
	}

	public void setTopTags(TopTags topTags) {
		this.topTags = topTags;
	}

	@Override
	public String toString() {
		return "JsonTopTagsWrapper [topTags=" + topTags + "]";
	}

}
