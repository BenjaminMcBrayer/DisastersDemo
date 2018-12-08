package com.DisastersDemo.entity.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribution {
	private String rank;

	public Attribution() {
	}

	public Attribution(String rank) {
		this.rank = rank;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "Attribution [rank=" + rank + "]";
	}

}
