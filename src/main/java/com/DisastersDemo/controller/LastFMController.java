package com.DisastersDemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DisastersDemo.entity.lastfm.JsonTopTagsWrapper;
import com.DisastersDemo.entity.lastfm.JsonTracksWrapper;
import com.DisastersDemo.entity.lastfm.Track;

@Controller
@SessionAttributes({ "user", "validEvents", "coordinatesList", "gMarkers", "accessToken", "deviceId", "topTag",
		"previewURL" })
public class LastFMController {

	@Value("${lastfm.api_key}")
	private String lastFMKey;

	public String getTopTag(HttpSession session) {
		RestTemplate rT = new RestTemplate();
		JsonTopTagsWrapper jTTW = rT.getForObject(
				"http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=" + lastFMKey + "&format=json",
				JsonTopTagsWrapper.class);
		// TODO: Get random top tag from the array
		String topTag = jTTW.getTopTags().getTags()[0].getName();
		session.setAttribute("topTag", topTag);
		return topTag;
	}

	public List<Track> getLastFMTracks(HttpSession session) {
		String topTag = getTopTag(session);
		RestTemplate rT = new RestTemplate();
		JsonTracksWrapper jTW = rT.getForObject("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag="
				+ topTag + "&limit=5&api_key=" + lastFMKey + "&format=json", JsonTracksWrapper.class);
		List<Track> lastFMTracks = jTW.getTracklist().getTracks();
		return lastFMTracks;
	}

	public String getRandomTrackName(HttpSession session) {
		int numTracks = 5;
		Random trackRoller = new Random();
		int trackNum = trackRoller.nextInt(numTracks);
		String randomTrackName = getLastFMTracks(session).get(trackNum).getName();
		return randomTrackName;
	}

	@RequestMapping("soundscapetest")
	public String showLastFMSearch() {
		return "soundscapetest";
	}

	// Convert to test case
	@RequestMapping("searchbytag")
	public ModelAndView submitLastFMSearch(@RequestParam("tag") String tag) {
		RestTemplate rT = new RestTemplate();
		JsonTracksWrapper jTW = rT.getForObject("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=" + tag
				+ "&limit=5&api_key=" + lastFMKey + "&format=json", JsonTracksWrapper.class);
		ArrayList<Track> tracks = jTW.getTracklist().getTracks();
		return new ModelAndView("soundscapetestresults", "tracks", tracks);
	}

	// Convert to test case
	@RequestMapping("gettoptags")
	public ModelAndView getTopTagsTest(HttpSession session) {
		RestTemplate rT = new RestTemplate();
		JsonTopTagsWrapper jTTW = rT.getForObject(
				"http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=" + lastFMKey + "&format=json",
				JsonTopTagsWrapper.class);
		session.setAttribute("topTag", jTTW.getTopTags().getTags()[0]);
		return new ModelAndView("toptagstest", "toptags", jTTW.getTopTags().getTags()[0]);
	}

	@RequestMapping("toptagsession")
	public ModelAndView topTagSession(HttpSession session) {
		ModelAndView mV = new ModelAndView("toptagsession");
		mV.addObject("topTag", session.getAttribute("topTag"));
		return mV;
	}
}
