package com.DisastersDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribution {
	private String rank;

	/**
	 * 
	 */
	public Attribution() {
	}

	/**
	 * @param rank
	 */
	public Attribution(String rank) {
		this.rank = rank;
	}

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Attribution [rank=" + rank + "]";
	}
	
	
}
