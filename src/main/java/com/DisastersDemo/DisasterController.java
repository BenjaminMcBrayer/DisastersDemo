package com.DisastersDemo;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DisastersDemo.entity.Event;
import com.DisastersDemo.entity.EventList;
import com.DisastersDemo.entity.JsonTracksWrapper;
import com.DisastersDemo.entity.Track;
import com.DisastersDemo.entity.User;
import com.DisastersDemo.repo.UsersRepository;

/**
 * @author
 *
 */
@Controller
@SessionAttributes("user")
public class DisasterController {

	@Value("${nasa.api_key}")
	private String nasaKey;

	@Value("${lastfm.api_key}")
	private String lastFMKey;

	@Autowired
	UsersRepository uR;

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
	@RequestMapping("/disastertest")
	public ModelAndView showMeTheDisasters() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		ResponseEntity<EventList> response = restTemplate.exchange(
				"https://eonet.sci.gsfc.nasa.gov/api/v2.1/events?limit=5&days=20&source=InciWeb&status=open",
				HttpMethod.GET, entity, EventList.class);

		EventList eventList = response.getBody();

		ArrayList<Event> events = eventList.getEvents();

		return new ModelAndView("disastertest", "events", events);
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
		
		ResponseEntity<JsonTracksWrapper> response = restTemplate.exchange("http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=" + tag + "&limit=5&api_key="
						+ lastFMKey + "&format=json", HttpMethod.GET, entity, JsonTracksWrapper.class);
		JsonTracksWrapper tracksWrapper = response.getBody();
		ArrayList<Track> tracks = tracksWrapper.getTracklist().getTracks();
		return new ModelAndView("/soundscapetestresults", "tracks", tracks);
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
