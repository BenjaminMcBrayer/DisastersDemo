package com.DisastersDemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DisastersDemo.entity.User;
import com.DisastersDemo.entity.lastfm.JsonTopTagsWrapper;
import com.DisastersDemo.entity.lastfm.JsonTracksWrapper;
import com.DisastersDemo.entity.lastfm.Track;
import com.DisastersDemo.entity.spotify.Device;
import com.DisastersDemo.entity.spotify.SpotifyDevicesWrapper;
import com.DisastersDemo.entity.spotify.SpotifySearchWrapper;
import com.DisastersDemo.entity.spotify.SpotifyTokenWrapper;
import com.DisastersDemo.entity.spotify.UserDetails;
import com.DisastersDemo.repo.UsersRepository;
import com.wrapper.spotify.Base64;

@Controller
@SessionAttributes({ "user", "validEvents", "coordinatesList", "gMarkers", "accessToken", "deviceId", "topTag",
		"previewURL" })
public class SpotifyController {

	@Value("${spotify.client_id}")
	private String clientId;

	@Value("${spotify.client_secret}")
	private String clientSecret;

	@Value("${lastfm.api_key}")
	private String lastFMKey;

	@Autowired
	UsersRepository uR;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@RequestMapping("/spotifycallback")
	public ModelAndView spotifyCallback(@RequestParam("code") String code, HttpSession session,
			RedirectAttributes redir) throws URISyntaxException {
		// Get access token.
		setCode(code);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + Base64.encode((clientId + ":" + clientSecret).getBytes()));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("redirect_uri", "http://localhost:8080/spotifycallback");
		URI uRI = new URI("https://accounts.spotify.com/api/token");
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(body,
				headers, HttpMethod.POST, uRI);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifyTokenWrapper> response = rT.exchange(request, SpotifyTokenWrapper.class);
		String accessToken = response.getBody().getAccess_token();
		System.out.println(accessToken);
		session.setAttribute("accessToken", accessToken);
		// Get display_name and email for user.
		headers.clear();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		URI userURI = new URI("https://api.spotify.com/v1/me");
		RequestEntity<MultiValueMap<String, String>> userRequest = new RequestEntity<MultiValueMap<String, String>>(
				null, headers, HttpMethod.GET, userURI);
		ResponseEntity<UserDetails> userResponse = rT.exchange(userRequest, UserDetails.class);
		if (uR.findByEmail(userResponse.getBody().getEmail()) == null) {
			// Create a new user in database.
			String displayName = userResponse.getBody().getDisplayName();
			String email = userResponse.getBody().getEmail();
			User user = new User(displayName, email);
			uR.save(user);
		}
		User user = uR.findByEmail(userResponse.getBody().getEmail());
		session.setAttribute("user", user);
		redir.addFlashAttribute("message", "Logged in.");
		return new ModelAndView("sketcher");
	}

	@RequestMapping("playspotifytrack")
	public ModelAndView playSpotifyTrack(HttpSession session) throws URISyntaxException {
		RestTemplate rT = new RestTemplate();
		JsonTopTagsWrapper jTTW = rT.getForObject(
				"http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=" + lastFMKey + "&format=json",
				JsonTopTagsWrapper.class);
		int numTags = 5;
		Random tagRoller = new Random();
		int tagNum = tagRoller.nextInt(numTags);
		String topTag = jTTW.getTopTags().getTags()[tagNum].getName();
		System.out.println("Top Tag: " + topTag);
		JsonTracksWrapper jTW = rT.getForObject("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag="
				+ topTag + "&limit=5&api_key=" + lastFMKey + "&format=json", JsonTracksWrapper.class);
		List<Track> lastFMTracks = jTW.getTracklist().getTracks();
		int numTracks = 5;
		Random trackRoller = new Random();
		int trackNum = trackRoller.nextInt(numTracks);
		String randomTrackName = lastFMTracks.get(trackNum).getName();
		System.out.println(randomTrackName);
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
		String previewURL = trackResponse.getBody().getTracks().getItems().get(0).getPreview_url();
		session.setAttribute("previewURL", previewURL);
		System.out.println(trackResponse.getBody().getTracks().getItems().get(0).getName());
		return new ModelAndView("spotifyplayer", "play", previewURL);
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
		Boolean is_active = null;
		for (int i = 0; i < devices.length; ++i) {
			if (devices[i].getName().equals("Web Player (Chrome)")) {
				deviceId = devices[i].getId();
				is_active = devices[i].getIs_active();
			}
		}
		session.setAttribute("deviceId", deviceId);
		ModelAndView mV = new ModelAndView("deviceid");
		mV.addObject("deviceId", deviceId);
		mV.addObject("is_active", is_active);
		return mV;
	}

	// This will only work if the device is currently active.
	@RequestMapping("testspotifyplayer")
	public ModelAndView spotifyPlayerTest(HttpSession session) throws URISyntaxException {
		String deviceId = (String) session.getAttribute("deviceId");
		URI uRI = new URI("https://api.spotify.com/v1/me/player/play");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("device_id", deviceId);
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(body,
				headers, HttpMethod.PUT, uRI);
		System.out.println(request.getHeaders());
		System.out.println(request.getBody());
		RestTemplate rT = new RestTemplate();
		rT.put("https://api.spotify.com/v1/me/player/play", request);
		return new ModelAndView("spotifyplayertest", "message", "It worked!");
	}

	@RequestMapping("testspotifysdk")
	public ModelAndView spotifySDKTest(HttpSession session) {
		ModelAndView mV = new ModelAndView("spotifysdktest");
		mV.addObject("accessToken", session.getAttribute("accessToken"));
		mV.addObject("deviceId", session.getAttribute("deviceId"));
		return mV;
	}

}
