package com.DisastersDemo.entity.nasa;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
	@JsonProperty
	private String date;
	@JsonProperty
	private String type;
	@JsonProperty
	private Double[] coordinates;

	public Geometry() {
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the coordinates
	 */
	public Double[] getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(Double[] coordinates) {
		this.coordinates = coordinates;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Geometry [date=" + date + ", type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	}

}
