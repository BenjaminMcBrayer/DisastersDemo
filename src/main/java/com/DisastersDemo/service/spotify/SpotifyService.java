package com.DisastersDemo.service.spotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.DisastersDemo.entity.lastfm.Track;

@Component
public class SpotifyService {

	@Value("${spotify.client_id}")
	private String clientId;

	@Value("${spotify.client_secret}")
	private String clientSecret;

	/**
	 * Make HTTP request to Spotify's server. Get access token using provided code.
	 */
	public String getSpotifyAccessToken(String code) {
		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		RestTemplate rT = new RestTemplate();
		@SuppressWarnings("unchecked")
		Map<String, String> response = rT.postForObject("https://accounts.spotify.com/api/token", params, Map.class);
		return response.get("access_token");
	}

	public String getRandomTrackName(ArrayList<Track> tracks) {
		int numTracks = 5;
		Random trackRoller = new Random();
		int trackNum = trackRoller.nextInt(numTracks);
		return tracks.get(trackNum).getName();
	}
}