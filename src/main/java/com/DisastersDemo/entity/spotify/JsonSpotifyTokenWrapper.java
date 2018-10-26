package com.DisastersDemo.entity.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSpotifyTokenWrapper {

	@JsonProperty
	private String access_token;
	@JsonProperty
	private String token_type;
	@JsonProperty
	private Number expires_in;

	public JsonSpotifyTokenWrapper() {
	}

	/**
	 * @param access_token
	 * @param token_type
	 * @param expires_in
	 */
	public JsonSpotifyTokenWrapper(String access_token, String token_type, Number expires_in) {
		this.access_token = access_token;
		this.token_type = token_type;
		this.expires_in = expires_in;
	}

	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * @return the token_type
	 */

	public String getToken_type() {
		return token_type;
	}

	/**
	 * @param token_type the token_type to set
	 */

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	/**
	 * @return the expires_in
	 */

	public Number getExpires_in() {
		return expires_in;
	}

	/**
	 * @param expires_in the expires_in to set
	 */
	public void setExpires_in(Number expires_in) {
		this.expires_in = expires_in;
	}

	@Override
	public String toString() {
		return "JsonSpotifyTokenWrapper [access_token=" + access_token + ", token_type=" + token_type + ", expires_in="
				+ expires_in + "]";
	}
}
