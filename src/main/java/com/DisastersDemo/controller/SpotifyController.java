package com.DisastersDemo.controller;

import java.net.URI;
import java.net.URISyntaxException;

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

import com.DisastersDemo.entity.spotify.SpotifyCurrentlyPlayingContextWrapper;
import com.DisastersDemo.entity.spotify.SpotifySearchWrapper;
import com.DisastersDemo.entity.spotify.SpotifyTokenWrapper;
import com.wrapper.spotify.Base64;

@Controller
@SessionAttributes("accessToken")
public class SpotifyController {

	@Value("${spotify.client_id}")
	private String clientId;

	@Value("${spotify.client_secret}")
	private String clientSecret;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String trackURI;

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

	@RequestMapping("")
	public void setTrackURI(HttpSession session) throws URISyntaxException {
		LastFMController lFMC = new LastFMController();
		lFMC.setRandomTrackName(session);
		String trackName = lFMC.getRandomTrackName();
		URI uri = new URI(
				"https://api.spotify.com/v1/search?query=" + trackName + "&type=track&market=US&offset=0&limit=20");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(null,
				headers, HttpMethod.GET, uri);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifySearchWrapper> response = rT.exchange(request, SpotifySearchWrapper.class);
		this.trackURI = response.getBody().getTracks().getItems().get(0).getUri();
	}

	public String getTrackURI() {
		return trackURI;
	}

	@RequestMapping("spotifyplayertest")
	public ModelAndView spotifyPlayerTest(HttpSession session) throws URISyntaxException {
		setTrackURI(session);
		String trackURI = getTrackURI();
		URI uri = new URI("https://api.spotify.com/v1/me/player/play");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Authorization", "Bearer " + session.getAttribute("accessToken"));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("context_uri", trackURI);
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(body,
				headers, HttpMethod.PUT, uri);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<SpotifyCurrentlyPlayingContextWrapper> response = rT.exchange(request,
				SpotifyCurrentlyPlayingContextWrapper.class);
		return new ModelAndView("spotifyplayertest", "playertest", response.getBody().getIs_playing());
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

}
