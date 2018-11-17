package com.DisastersDemo.entity.google;

/**
 * @author
 *
 */
public class GMarker {
	private String name;
	private double lat;
	private double lng;
	
	public GMarker() {
	}

	/**
	 * @param name
	 * @param lat
	 * @param lng
	 */
	public GMarker(String name, double lat, double lng) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[\"" + name + "\", " + lat + ", " + lng + "]";
	}
}