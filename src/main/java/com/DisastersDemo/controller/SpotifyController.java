package com.DisastersDemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DisastersDemo.entity.lastfm.JsonTopTagsWrapper;
import com.DisastersDemo.entity.lastfm.JsonTracksWrapper;
import com.DisastersDemo.entity.lastfm.Track;
import com.DisastersDemo.entity.spotify.Device;
import com.DisastersDemo.entity.spotify.SpotifyCurrentlyPlayingContextWrapper;
import com.DisastersDemo.entity.spotify.SpotifyDevicesWrapper;
import com.DisastersDemo.entity.spotify.SpotifySearchWrapper;
import com.DisastersDemo.entity.spotify.SpotifyTokenWrapper;
import com.wrapper.spotify.Base64;

@Controller
@SessionAttributes({ "user", "validEvents", "coordinatesList", "gMarkers", "accessToken", "deviceId", "topTag" })
public class SpotifyController {

	@Value("${spotify.client_id}")
	private String clientId;

	@Value("${spotify.client_secret}")
	private String clientSecret;

	@Value("${lastfm.api_key}")
	private String lastFMKey;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// Testing Spotify
	@RequestMapping("spotifytest")
	public String spotifyTest() {
		return "spotifytest";
	}

	@RequestMapping("/spotifycallback")
	public ModelAndView spotifyCallback(@RequestParam("code") String code, HttpSession session)
			throws URISyntaxException {
		setCode(code);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + Base64.encode((clientId + ":" + clientSecret).getBytes()));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("redirect_uri", "http://localhost:8080/spotifycallback");
		URI uri = new URI("https://accounts.spotify.com/api/token");
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(body,
				headers, HttpMethod.POST, uri);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifyTokenWrapper> response = rT.exchange(request, SpotifyTokenWrapper.class);
		String accessToken = response.getBody().getAccess_token();
		System.out.println(accessToken);
		session.setAttribute("accessToken", accessToken);
		ModelAndView mv = new ModelAndView("redirect:/sketcher");
		return mv;
	}

	public String getTrackURI(HttpSession session) throws URISyntaxException {
		RestTemplate rT = new RestTemplate();
		JsonTopTagsWrapper jTTW = rT.getForObject(
				"http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=" + lastFMKey + "&format=json",
				JsonTopTagsWrapper.class);
		// TODO: Get random top tag from the array
		String topTag = jTTW.getTopTags().getTags()[0].getName();
		JsonTracksWrapper jTW = rT.getForObject("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag="
				+ topTag + "&limit=5&api_key=" + lastFMKey + "&format=json", JsonTracksWrapper.class);
		List<Track> lastFMTracks = jTW.getTracklist().getTracks();
		int numTracks = 5;
		Random trackRoller = new Random();
		int trackNum = trackRoller.nextInt(numTracks);
		String randomTrackName = lastFMTracks.get(trackNum).getName();
		URI uri = new URI("https://api.spotify.com/v1/search?query=" + randomTrackName
				+ "&type=track&market=US&offset=0&limit=20");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(null,
				headers, HttpMethod.GET, uri);
		ResponseEntity<SpotifySearchWrapper> response = rT.exchange(request, SpotifySearchWrapper.class);
		String trackURI = response.getBody().getTracks().getItems().get(0).getUri();
		return trackURI;
	}

	@RequestMapping("playspotifytrack")
	public ModelAndView playSpotifyTrack(HttpSession session) throws URISyntaxException {
		RestTemplate rT = new RestTemplate();
		JsonTopTagsWrapper jTTW = rT.getForObject(
				"http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=" + lastFMKey + "&format=json",
				JsonTopTagsWrapper.class);
		// TODO: Get random top tag from the array
		String topTag = jTTW.getTopTags().getTags()[0].getName();
		JsonTracksWrapper jTW = rT.getForObject("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag="
				+ topTag + "&limit=5&api_key=" + lastFMKey + "&format=json", JsonTracksWrapper.class);
		List<Track> lastFMTracks = jTW.getTracklist().getTracks();
		int numTracks = 5;
		Random trackRoller = new Random();
		int trackNum = trackRoller.nextInt(numTracks);
		String randomTrackName = lastFMTracks.get(trackNum).getName();
		randomTrackName = randomTrackName.replaceAll("\\s", "+");
		URI randomTrackURI = new URI("https://api.spotify.com/v1/search?query=" + randomTrackName
				+ "&type=track&market=US&offset=0&limit=20");
		HttpHeaders trackHeaders = new HttpHeaders();
		trackHeaders.setContentType(MediaType.APPLICATION_JSON);
		trackHeaders.add("Accept", "application/json");
		trackHeaders.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		RequestEntity<MultiValueMap<String, String>> trackRequest = new RequestEntity<MultiValueMap<String, String>>(
				null, trackHeaders, HttpMethod.GET, randomTrackURI);
		ResponseEntity<SpotifySearchWrapper> trackResponse = rT.exchange(trackRequest, SpotifySearchWrapper.class);
		String trackURI = trackResponse.getBody().getTracks().getItems().get(0).getUri();
		URI playerUri = new URI("https://api.spotify.com/v1/me/player/play");
		HttpHeaders playerHeaders = new HttpHeaders();
		playerHeaders.setContentType(MediaType.APPLICATION_JSON);
		playerHeaders.add("Accept", "application/json");
		playerHeaders.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("context_uri", trackURI);
		RequestEntity<MultiValueMap<String, String>> playerRequest = new RequestEntity<MultiValueMap<String, String>>(
				body, playerHeaders, HttpMethod.PUT, playerUri);
		ResponseEntity<SpotifyCurrentlyPlayingContextWrapper> playerResponse = rT.exchange(playerRequest,
				SpotifyCurrentlyPlayingContextWrapper.class);
		return new ModelAndView("spotifyplayer", "playertest", playerResponse.getBody().getIs_playing());
	}

	// Convert to test case.
	@RequestMapping("/spotifysearchtest")
	public ModelAndView spotifySearchTest(HttpSession session) throws URISyntaxException {
		URI uri = new URI(
				"https://api.spotify.com/v1/search?query=dancing+queen&type=track&market=US&offset=0&limit=20");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(null,
				headers, HttpMethod.GET, uri);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifySearchWrapper> response = rT.exchange(request, SpotifySearchWrapper.class);
		return new ModelAndView("spotifytestresults", "trackObject", response.getBody().getTracks().getItems());
	}

	@RequestMapping("getdeviceid")
	public ModelAndView getDeviceId(HttpSession session) throws URISyntaxException {
		URI uRI = new URI("https://api.spotify.com/v1/me/player/devices");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(null,
				headers, HttpMethod.GET, uRI);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifyDevicesWrapper> response = rT.exchange(request, SpotifyDevicesWrapper.class);
		Device[] devices = response.getBody().getDevices();
		String deviceId = null;
		for (int i = 0; i < devices.length; ++i) {
			if (devices[i].getName().equals("Web Player (Chrome)")) {
				deviceId = devices[i].getId();
			}
		}
		session.setAttribute("deviceId", deviceId);
		return new ModelAndView("deviceid", "deviceid", deviceId);
	}

	@RequestMapping("spotifyplayertest")
	public ModelAndView spotifyPlayerTest(HttpSession session) throws URISyntaxException {
		URI uRI = new URI("https://api.spotify.com/v1/me/player/play?device_id=" + session.getAttribute("deviceId"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("context_uri", "spotify:album:1Je1IMUlBXcx1Fz0WE7oPT");
		RequestEntity<MultiValueMap<String, String>> playerRequest = new RequestEntity<MultiValueMap<String, String>>(
				null, headers, HttpMethod.PUT, uRI);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifyCurrentlyPlayingContextWrapper> response = rT.exchange(playerRequest,
				SpotifyCurrentlyPlayingContextWrapper.class);
		return new ModelAndView("spotifyplayertest", "playertest", response.getBody().getIs_playing());
	}

}
