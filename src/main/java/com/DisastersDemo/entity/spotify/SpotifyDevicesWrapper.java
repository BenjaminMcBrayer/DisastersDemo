package com.DisastersDemo.entity.spotify;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyDevicesWrapper {
	private Device[] devices;

	public SpotifyDevicesWrapper() {
	}

	public SpotifyDevicesWrapper(Device[] devices) {
		this.devices = devices;
	}

	public Device[] getDevices() {
		return devices;
	}

	public void setDevices(Device[] devices) {
		this.devices = devices;
	}

	@Override
	public String toString() {
		return "SpotifyDevicesWrapper [devices=" + Arrays.toString(devices) + "]";
	}

}
