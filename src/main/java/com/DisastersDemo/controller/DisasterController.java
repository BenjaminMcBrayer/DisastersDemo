package com.DisastersDemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DisastersDemo.entity.google.GMarker;
import com.DisastersDemo.entity.nasa.Event;
import com.DisastersDemo.entity.nasa.EventList;
import com.DisastersDemo.entity.spotify.SpotifyCurrentlyPlayingContextWrapper;
import com.DisastersDemo.repo.UsersRepository;
import com.DisastersDemo.service.nasa.NasaService;
import com.DisastersDemo.utility.Utility;

/**
 * @author
 *
 */
@Controller
@SessionAttributes({ "user", "validEvents", "coordinatesList", "gMarkers", "accessToken", "deviceId", "topTag" })
public class DisasterController {

	@Value("${nasa.api_key}")
	private String nasaKey;

	@Autowired
	UsersRepository uR;

	ArrayList<Event> validEventsField = new ArrayList<>();

	@RequestMapping("/sketcher")
	public String sketcher() {
		return "sketcher";
	}

	@RequestMapping("/returnmydisasterlist")
	public ModelAndView returnMyDisasterList(@RequestParam("usercat") String userCat,
			@RequestParam("userstartdate") String userStartDate, @RequestParam("userenddate") String userEndDate,
			HttpSession session) {
		RestTemplate restTemplate = Utility.getRequestFactoryRestTemplate();
		HttpEntity<String> httpEntity = Utility.getHttpEntity();
		ArrayList<Event> disasters = NasaService.getDisasters(userCat, restTemplate, httpEntity);
		ArrayList<String> dates = NasaService.getDates(disasters);
		ArrayList<LocalDate> localDates = NasaService.getLocalDates(dates, userStartDate, userEndDate);
		String[] validDatesArray = NasaService.getValidDatesArray(localDates);
		ArrayList<Event> validEvents = NasaService.getValidEvents(validDatesArray, disasters);
		session.setAttribute("validEvents", validEvents);
		validEventsField = validEvents;
		return new ModelAndView("mydisasterlist", "disasters", validEvents);
	}

	@RequestMapping("/mycoordinates")
	public ModelAndView returnMyCoordinates(HttpSession session) {
		@SuppressWarnings("unchecked")
		ArrayList<Event> validEvents = (ArrayList<Event>) session.getAttribute("validEvents");
		ArrayList<Object> coordinatesList = NasaService.getCoordinatesList(validEvents);
		session.setAttribute("coordinatesList", coordinatesList);
		return new ModelAndView("mycoordinates", "coordinateslist", coordinatesList);
	}

	@RequestMapping("/mygmarkers")
	public ModelAndView returnMyGMarkers(HttpSession session) {
		ArrayList<Event> validEvents = validEventsField;
		ArrayList<GMarker> gMarkers = new ArrayList<>();
		GMarker gMarker;
		for (Event validEvent : validEvents) {
			if (validEvent.getGeometries().get(0).getGeotype().equals("Point")) {
				gMarker = NasaService.getPointGMarker(validEvent);
				gMarkers.add(gMarker);
			} else if (validEvent.getGeometries().get(0).getGeotype().equals("Polygon")) {
				String s = validEvent.getGeometries().get(0).getGeocoordinates().get(0).toString();
				String[] polyCoordinates = NasaService.getPolyCoordinates(s);
				ArrayList<GMarker> temp = NasaService.getPolygonGMarkers(polyCoordinates, validEvent);
				for (GMarker gM : temp) {
					gMarkers.add(gM);
				}
			}
		}
		session.setAttribute("gmarkers", gMarkers);
		System.out.println(gMarkers);
		return new ModelAndView("mygmarkers", "gmarkers", gMarkers);
	}

	@RequestMapping("/sketchmygmarkers")
	public ModelAndView sketchMyGMarkers(HttpSession session) throws URISyntaxException {
		@SuppressWarnings("unchecked")
		ArrayList<GMarker> gMarkers = (ArrayList<GMarker>) session.getAttribute("gmarkers");
		SpotifyController sC = new SpotifyController();
		String trackURI = sC.getTrackURI(session);
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
		System.out.println("Is_Playing = " + response.getBody().getIs_playing());
		return new ModelAndView("googlemapstest", "gMarkers", gMarkers);
	}

	@RequestMapping("/sketchmygmarkers2")
	public ModelAndView sketchMyGMarkers2(HttpSession session) {
		@SuppressWarnings("unchecked")
		ArrayList<GMarker> gMarkers = (ArrayList<GMarker>) session.getAttribute("gmarkers");
		return new ModelAndView("googlemapstest2", "gMarkers", gMarkers);
	}

	// Testing Google Maps
	@RequestMapping("/googlemapstest")
	public String googleMapsTest() {
		return "googlemapstest";
	}

	@RequestMapping("/spotifyplaybutton")
	public String spotifyPlayButton() {
		return "spotifyplaybutton";
	}

	// Integration Testing
	// TODO: Create home page
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index", "test", "Hello, World!");
	}

	// Testing NASA API
	// TODO: Convert to test case
	@RequestMapping("/disastertest")
	public ModelAndView showMeTheDisasters() {
		RestTemplate restTemplate = Utility.getRequestFactoryRestTemplate();
		HttpEntity<String> httpEntity = Utility.getHttpEntity();
		ResponseEntity<EventList> response = restTemplate.exchange(
				"https://eonet.sci.gsfc.nasa.gov/api/v2.1/events?limit=5&source=InciWeb,EO&status=open", HttpMethod.GET,
				httpEntity, EventList.class);
		EventList eventList = response.getBody();
		ArrayList<Event> events = eventList.getEvents();
		return new ModelAndView("disastertest", "events", events);
	}

	// TODO: Convert to test case
	@RequestMapping("/disastercategorytest")
	public ModelAndView showMeTheDisastersByCategory() {
		RestTemplate restTemplate = Utility.getRequestFactoryRestTemplate();
		HttpEntity<String> httpEntity = Utility.getHttpEntity();
		String userCat = "12";
		String userStartDate = "2016-04-12";
		String userEndDate = "2018-07-15";
		ArrayList<Event> disasters = NasaService.getDisasters(userCat, restTemplate, httpEntity);
		ArrayList<String> dates = NasaService.getDates(disasters);
		ArrayList<LocalDate> localDates = NasaService.getLocalDates(dates, userStartDate, userEndDate);
		String[] validDatesArray = NasaService.getValidDatesArray(localDates);
		ArrayList<Event> validEvents = NasaService.getValidEvents(validDatesArray, disasters);
		return new ModelAndView("disastercategorytest", "events", validEvents);
	}

	@RequestMapping("/httprequesttest")
	public ModelAndView httpRequestTest() {
		return new ModelAndView("httprequesttest", "httprequesttest", "Hello World");
	}

	@RequestMapping("/applicationtest")
	public @ResponseBody String applicationTest() {
		return "Hello World";
	}
}
