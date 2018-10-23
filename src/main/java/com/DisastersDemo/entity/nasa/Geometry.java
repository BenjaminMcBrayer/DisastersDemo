/**
 * 
 */
package com.DisastersDemo.entity.nasa;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author benjamin.mcbrayer
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
	@JsonProperty("date")
	String geodate;
	@JsonProperty("type")
	String geotype;
	@JsonProperty("coordinates")
	ArrayList<Object> geocoordinates;

	/**
	 * 
	 */
	public Geometry() {
	}

	/**
	 * @param geodate
	 * @param geotype
	 * @param geocoordinates
	 */
	public Geometry(String geodate, String geotype, ArrayList<Object> geocoordinates) {
		this.geodate = geodate;
		this.geotype = geotype;
		this.geocoordinates = geocoordinates;
	}

	/**
	 * @return the geodate
	 */
	public String getGeodate() {
		return geodate;
	}

	/**
	 * @param geodate the geodate to set
	 */
	public void setGeodate(String geodate) {
		this.geodate = geodate;
	}

	/**
	 * @return the geotype
	 */
	public String getGeotype() {
		return geotype;
	}

	/**
	 * @param geotype the geotype to set
	 */
	public void setGeotype(String geotype) {
		this.geotype = geotype;
	}

	/**
	 * @return the geocoordinates
	 */
	public ArrayList<Object> getGeocoordinates() {
		return geocoordinates;
	}

	/**
	 * @param geocoordinates the geocoordinates to set
	 */
	public void setGeocoordinates(ArrayList<Object> geocoordinates) {
		this.geocoordinates = geocoordinates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Geometry [geodate=" + geodate + ", geotype=" + geotype + ", geocoordinates=" + geocoordinates + "]";
	}

}
