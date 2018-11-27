package com.DisastersDemo.controller;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DisastersDemo.entity.lastfm.JsonTracksWrapper;
import com.DisastersDemo.entity.lastfm.Track;

@Controller
@SessionAttributes("tracks")
public class LastFMController {

	@Value("${lastfm.api_key}")
	private String lastFMKey;

	@RequestMapping("soundscapetest")
	public String showLastFMSearch() {
		return "soundscapetest";
	}

	@RequestMapping("/searchbytag")
	public ModelAndView submitLastFMSearch(@RequestParam("tag") String tag, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonTracksWrapper> response = restTemplate
				.exchange("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=" + tag + "&limit=5&api_key="
						+ lastFMKey + "&format=json", HttpMethod.GET, entity, JsonTracksWrapper.class);
		JsonTracksWrapper tracksWrapper = response.getBody();
		ArrayList<Track> tracks = tracksWrapper.getTracklist().getTracks();
		session.setAttribute("tracks", tracks);
		return new ModelAndView("/soundscapetestresults", "tracks", tracks);
	}
	
	public static String getRandomTrackName(HttpSession session) {
		@SuppressWarnings("unchecked")
		ArrayList<Track> tracks = (ArrayList<Track>) session.getAttribute("tracks");
		int numTracks = 5;
		Random trackRoller = new Random();
		int trackNum = trackRoller.nextInt(numTracks);
		return tracks.get(trackNum).getName();
	}
}
