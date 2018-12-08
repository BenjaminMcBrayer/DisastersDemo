package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
	private String id;
	private Boolean is_active;
	private String name;

	public Device() {
	}

	public Device(String id, Boolean is_active, String name) {
		this.id = id;
		this.is_active = is_active;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", is_active=" + is_active + ", name=" + name + "]";
	}

}
