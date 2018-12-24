package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {
	
	@JsonProperty("display_name")
	private String displayName;
	private String email;
	
	public UserDetails() {
	}

	public UserDetails(String displayName, String email) {
		this.displayName = displayName;
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDetails [displayName=" + displayName + ", email=" + email + "]";
	}
}
