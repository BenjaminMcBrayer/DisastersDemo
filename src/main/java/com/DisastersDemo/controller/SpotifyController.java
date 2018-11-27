package com.DisastersDemo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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

import com.DisastersDemo.entity.spotify.JsonSpotifyTokenWrapper;
import com.DisastersDemo.entity.spotify.SpotifySearchWrapper;
import com.DisastersDemo.utility.Utility;
import com.wrapper.spotify.Base64;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

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

	private URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotifycallback");

	private SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret)
			.setRedirectUri(redirectUri).build();
	
	// Testing Spotify
	@RequestMapping("spotifytest")
	public String spotifyTest() {
		return "spotifytest";
	}

	@RequestMapping("/spotifycallback")
	public ModelAndView spotifyCallback(@RequestParam("code") String code, HttpSession session) throws URISyntaxException {
		setCode(code);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + Base64.encode((clientId + ":" + clientSecret).getBytes()));
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("redirect_uri", "http://localhost:8080/spotifycallback");
		URI uri = new URI("https://accounts.spotify.com/api/token");
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(body, headers, HttpMethod.POST, uri);
		RestTemplate rT = new RestTemplate();
		ResponseEntity<JsonSpotifyTokenWrapper> response = rT.exchange(request,
				JsonSpotifyTokenWrapper.class);
		String accessToken = response.getBody().getAccess_token();
		System.out.println(accessToken);
		session.setAttribute("accessToken", accessToken);
		ModelAndView mv = new ModelAndView("redirect:/sketcher");
		return mv;
	}
	
	@RequestMapping("/accesstokenrequest")
	public ModelAndView accessTokenRequest() {
		System.out.println(code);
		System.out.println(clientId);
		System.out.println(clientSecret);
		System.out.println(redirectUri);
		AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(getCode()).build();
		try {
			AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println(spotifyApi.getAccessToken());
			System.out.println("Error: " + e.getMessage());
		}
		return new ModelAndView("spotifytoken", "message", spotifyApi.getAccessToken());
	}

	@RequestMapping("/spotifysearchtest")
	public ModelAndView spotifySearchTest(HttpSession session) {
		RestTemplate restTemplate = Utility.getRequestFactoryRestTemplate();
		HttpEntity<String> httpEntity = Utility.getHttpEntity();
		ResponseEntity<SpotifySearchWrapper> response = restTemplate
				.exchange("https://api.spotify.com/v1/search?q=name:" + LastFMController.getRandomTrackName(session)
						+ "&type=track", HttpMethod.GET, httpEntity, SpotifySearchWrapper.class);
		SpotifySearchWrapper pagingObject = response.getBody();
		// Tracks tracks = pagingObject.getTracks();
		return new ModelAndView("spotifytestresults", "pagingobject", pagingObject);
	}

}
