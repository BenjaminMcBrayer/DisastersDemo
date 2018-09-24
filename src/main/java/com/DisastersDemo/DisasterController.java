package com.DisastersDemo;

import java.util.ArrayList;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DisastersDemo.entity.Event;
import com.DisastersDemo.entity.EventList;

/**
 * @author
 *
 */
@Controller
public class DisasterController {

	@Value("${apikey}")
	private String key;

	@RequestMapping("/")
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

		return new ModelAndView("index", "events", events);
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
