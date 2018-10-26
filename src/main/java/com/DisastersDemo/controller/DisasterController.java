package com.DisastersDemo.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DisastersDemo.entity.User;
import com.DisastersDemo.entity.google.GMarker;
import com.DisastersDemo.entity.lastfm.JsonTracksWrapper;
import com.DisastersDemo.entity.lastfm.Track;
import com.DisastersDemo.entity.nasa.Event;
import com.DisastersDemo.entity.nasa.EventList;
import com.DisastersDemo.entity.spotify.JsonSpotifyTokenWrapper;
import com.DisastersDemo.repo.UsersRepository;
import com.DisastersDemo.service.nasa.NasaService;

/**
 * @author
 *
 */
@SuppressWarnings("deprecation")
@Controller
@SessionAttributes({ "user", "validEvents", "coordinatesList", "gMarkers"})
public class DisasterController {

	@Value("${nasa.api_key}")
	private String nasaKey;

	@Value("${lastfm.api_key}")
	private String lastFMKey;

	@Autowired
	UsersRepository uR;
	
	ArrayList<Event> validEventsField = new ArrayList<>();
	
	// TODO: Create home page
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index", "test", "Hello, World!");
	}

	// Login, logout, sessions
	@RequestMapping("/login")
	public ModelAndView showLoginForm() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView submitLoginForm(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session, RedirectAttributes redir) {
		User user = uR.findByUsername(username);
		if (user == null || !password.equals(user.getPassword())) {
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("message", "Incorrect username or password.");
			return mv;
		}
		session.setAttribute("user", user);
		redir.addFlashAttribute("message", "Logged in.");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session, RedirectAttributes redir) {
		session.invalidate();
		redir.addFlashAttribute("message", "Logged out.");
		return new ModelAndView("redirect:/");
	}

	// Testing NASA API
	// TODO: Convert to JUnit Test Case
	@RequestMapping("/disastertest")
	public ModelAndView showMeTheDisasters() {
		RestTemplate restTemplate = NasaService.getEONetRestTemplate();
		HttpEntity<String> httpEntity = NasaService.getEONetHttpEntity();
		ResponseEntity<EventList> response = restTemplate.exchange(
				"https://eonet.sci.gsfc.nasa.gov/api/v2.1/events?limit=5&days=20&source=InciWeb,EO&status=open",
				HttpMethod.GET, httpEntity, EventList.class);
		EventList eventList = response.getBody();
		ArrayList<Event> events = eventList.getEvents();
		return new ModelAndView("disastertest", "events", events);
	}

	// TODO: Convert to JUnit Test Case
	@RequestMapping("/disastercategorytest")
	public ModelAndView showMeTheDisastersByCategory() {
		RestTemplate restTemplate = NasaService.getEONetRestTemplate();
		HttpEntity<String> httpEntity = NasaService.getEONetHttpEntity();
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

	// Sketching the disasters
	@RequestMapping("/sketcher")
	public String sketcher() {
		return "sketcher";
	}

	@RequestMapping("/returnmydisasterlist")
	public ModelAndView returnMyDisasterList(@RequestParam("usercat") String userCat,
			@RequestParam("userstartdate") String userStartDate, @RequestParam("userenddate") String userEndDate,
			HttpSession session) {
		RestTemplate restTemplate = NasaService.getEONetRestTemplate();
		HttpEntity<String> httpEntity = NasaService.getEONetHttpEntity();
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
//		@SuppressWarnings("unchecked")
//		ArrayList<Event> validEvents = (ArrayList<Event>) session.getAttribute("validEvents");
		ArrayList<Event> validEvents = validEventsField;
		System.out.println("TEST: " + validEventsField);
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

	@RequestMapping("/sketchmydisasters")
	public ModelAndView sketchMyDisasters(HttpSession session) {
		@SuppressWarnings("unchecked")
		ArrayList<Object> coordinatesList = (ArrayList<Object>) session.getAttribute("coordinatesList");
		
		return new ModelAndView("googlemapstest", "gmarkers", coordinatesList);
	}
	
	@RequestMapping("/sketchmygmarkers")
	public ModelAndView sketchMyGMarkers(HttpSession session) {
		@SuppressWarnings("unchecked")
		ArrayList<GMarker> gMarkers = (ArrayList<GMarker>) session.getAttribute("gmarkers");
		System.out.println("WORKS? " + gMarkers);
		return new ModelAndView("googlemapstest", "gmarkers", gMarkers);
	}

	// Testing geotools.
	@RequestMapping("/showTestMap")
	public ModelAndView geotoolsTest() throws Exception {
		File file = JFileDataStoreChooser.showOpenFile("shp", null);
		if (file == null) {
			return null;
		}
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();

		MapContext map = new DefaultMapContext();
		map.setTitle("Quickstart");
		map.addLayer(featureSource, null);

		JMapFrame.showMap(map);

		return new ModelAndView("geotoolstest", "geotoolstest", "This is the geotools test page.");
	}

	// Testing last.fm API
	@RequestMapping("soundscapetest")
	public String showLastFMSearch() {
		return "soundscapetest";
	}

	@RequestMapping("/searchbytag")
	public ModelAndView submitLastFMSearch(@RequestParam("tag") String tag) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonTracksWrapper> response = restTemplate
				.exchange("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=" + tag + "&limit=5&api_key="
						+ lastFMKey + "&format=json", HttpMethod.GET, entity, JsonTracksWrapper.class);
		JsonTracksWrapper tracksWrapper = response.getBody();
		ArrayList<Track> tracks = tracksWrapper.getTracklist().getTracks();
		return new ModelAndView("/soundscapetestresults", "tracks", tracks);
	}

	// Testing Spotify
	@RequestMapping("spotifytest")
	public String spotifyTest() {
		return "spotifytest";
	}

	// FIXME
	@PostMapping(value = "getToken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ModelAndView getSpotifyToken() throws UnsupportedEncodingException {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		//byte[] credentialsAsBytes = client_credentials.getBytes();
		//byte[] base64EncodedCredentialsAsBytes = Base64.encodeBase64(credentialsAsBytes);
		//String base64EncodedCredentials = new String(base64EncodedCredentialsAsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		//headers.add("Authorization", "Basic " + base64EncodedCredentials);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		String grantType = "grant_type";
		String encodedGrantType = URLEncoder.encode(grantType, "UTF-8");
		String clientCredentials = "client_credentials";
		String encodedClientCredentials = URLEncoder.encode(clientCredentials, "UTF-8");

		requestBody.add(encodedGrantType, encodedClientCredentials);

		// String requestBody = "grant_type=client_credentials";
		// String encodedRequestBody = URLEncoder.encode(requestBody, "UTF-8");

		HttpEntity<?> entity = new HttpEntity<Object>(requestBody, headers);
//		ResponseEntity<JsonSpotifyTokenWrapper> responseEntity = restTemplate.exchange(
//				"https://accounts.spotify.com/api/token", HttpMethod.POST, entity, JsonSpotifyTokenWrapper.class);
		JsonSpotifyTokenWrapper response = restTemplate.postForObject("https://accounts.spotify.com/api/token", entity,
				JsonSpotifyTokenWrapper.class);
		System.out.println(response);
		return new ModelAndView("spotifytestresults", "token", response);
	}

	@RequestMapping("/spotifyplaybutton")
	public String spotifyPlayButton() {
		return "spotifyplaybutton";
	}

	// Testing Google Maps
	@RequestMapping("/googlemapstest")
	public String googleMapsTest() {
		return "googlemapstest";
	}

	// Integration Testing
	@RequestMapping("/httprequesttest")
	public ModelAndView httpRequestTest() {
		return new ModelAndView("httprequesttest", "httprequesttest", "Hello World");
	}

	@RequestMapping("/applicationtest")
	public @ResponseBody String applicationTest() {
		return "Hello World";
	}
}
