package com.DisastersDemo.service.nasa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.DisastersDemo.entity.google.GMarker;
import com.DisastersDemo.entity.nasa.Event;
import com.DisastersDemo.entity.nasa.EventList;
import com.DisastersDemo.entity.nasa.Geometry;

public class NasaService {

	public static RestTemplate getEONetRestTemplate() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}

	public static HttpEntity<String> getEONetHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);
		return httpEntity;
	}
	
	/**
	 * @param userCat
	 * @param restTemplate
	 * @param httpEntity
	 * @return
	 */
	public static ArrayList<Event> getDisasters(String userCat, RestTemplate restTemplate, HttpEntity<String> httpEntity) {
		ResponseEntity<EventList> response = restTemplate
				.exchange(
						"https://eonet.sci.gsfc.nasa.gov/api/v2.1/categories/" + userCat
								+ "?limit=50&source=InciWeb,EO&status=open",
						HttpMethod.GET, httpEntity, EventList.class);
		EventList eventList = response.getBody();
		ArrayList<Event> disasters = eventList.getEvents();
		return disasters;
	}

	// Convert String to LocalDate
	public static LocalDate getMyLocalDateFromString(String myDate) {
		String formattedDate = myDate.replaceAll("-", "");
		LocalDate myLocalDate = LocalDate.parse(formattedDate, DateTimeFormatter.BASIC_ISO_DATE);
		return myLocalDate;
	}

	public static ArrayList<String> getDates(ArrayList<Event> disasters) {
		ArrayList<ArrayList<Geometry>> geometriesList = new ArrayList<>();
		ArrayList<String> dates = new ArrayList<>();
		for (Event e : disasters) {
			geometriesList.add(e.getGeometries());
		}
		for (ArrayList<Geometry> g : geometriesList) {
			for (int i = 0; i < g.size(); ++i) {
				dates.add(g.get(i).getGeodate());
			}
		}
		return dates;
	}

	public static ArrayList<LocalDate> getLocalDates(ArrayList<String> dates, String userStartDate,
			String userEndDate) {
		ArrayList<LocalDate> localDates = new ArrayList<>();
		LocalDate userLocalDateStart = NasaService.getMyLocalDateFromString(userStartDate);
		LocalDate userLocalDateEnd = NasaService.getMyLocalDateFromString(userEndDate);
		for (String d : dates) {
			d = d.substring(0, 10);
			LocalDate lD = NasaService.getMyLocalDateFromString(d);
			if (!(lD.isBefore(userLocalDateStart) || lD.isAfter(userLocalDateEnd))) {
				localDates.add(lD);
			}
		}
		return localDates;
	}

	public static String[] getValidDatesArray(ArrayList<LocalDate> localDates) {
		HashSet<String> validDatesSet = new HashSet<>();
		for (LocalDate lD : localDates) {
			String s = lD.format(DateTimeFormatter.ISO_DATE);
			validDatesSet.add(s);
		}
		String[] validDatesArray = new String[validDatesSet.size()];
		validDatesArray = validDatesSet.toArray(validDatesArray);
		return validDatesArray;
	}

	public static ArrayList<Event> getValidEvents(String[] validDatesArray, ArrayList<Event> disasters) {
		ArrayList<Event> validEvents = new ArrayList<>();
		String d = null;
		for (String s : validDatesArray) {
			for (Object e : disasters) {
				d = ((Event) e).getGeometries().get(0).getGeodate();
				if (d.substring(0, 10).equals(s)) {
					validEvents.add((Event) e);
				}
			}
		}
		return validEvents;
	}

	public static ArrayList<Object> getCoordinatesList(ArrayList<Event> validEvents) {
		ArrayList<Object> coordinatesList = new ArrayList<>();
		for (int i = 0; i < validEvents.size(); ++i) {
			coordinatesList.add(validEvents.get(i).getGeometries().get(0).getGeocoordinates());	
		}
		return coordinatesList;
	}
	
	public static GMarker getPointGMarker(Event e) {
		GMarker gMarker = new GMarker();
		gMarker.setName(e.getTitle());
		gMarker.setLat((Double) e.getGeometries().get(0).getGeocoordinates().get(0));
		gMarker.setLng((Double) e.getGeometries().get(0).getGeocoordinates().get(1));
		return gMarker;
	}
	
	public static String[] getPolyCoordinates(String s) {
		s = s.replace("[", "");
		s = s.replace("]", "");
		s = s.replace(" ", "");
		String[] polyCoordinates = s.split(",");
		return polyCoordinates;
	}
	
	public static ArrayList<GMarker> getPolygonGMarkers(String[] polyCoordinates, Event e) {
		ArrayList<GMarker> temp = new ArrayList<>();
		setPolygonGMarkerNames(polyCoordinates, e, temp);
		ArrayList<Double> latitudes = getPolygonGMarkerLatitudes(polyCoordinates);				
		ArrayList<Double> longitudes = getPolygonGMarkerLongitudes(polyCoordinates);
		setPolygonGMarkerLatsAndLngs(temp, latitudes, longitudes);
		return temp;
	}

	/**
	 * @param temp
	 * @param latitudes
	 * @param longitudes
	 */
	private static void setPolygonGMarkerLatsAndLngs(ArrayList<GMarker> temp, ArrayList<Double> latitudes,
			ArrayList<Double> longitudes) {
		for (int i = 0; i < temp.size(); ++i) {
			temp.get(i).setLat(latitudes.get(i));
			temp.get(i).setLng(longitudes.get(i));
		}
	}

	/**
	 * @param polyCoordinates
	 * @return
	 */
	private static ArrayList<Double> getPolygonGMarkerLongitudes(String[] polyCoordinates) {
		ArrayList<Double> longitudes = new ArrayList<>();
		for (int i = 1; i < polyCoordinates.length; i += 2) {
			longitudes.add(Double.parseDouble(polyCoordinates[i]));
		}
		return longitudes;
	}

	/**
	 * @param polyCoordinates
	 * @return
	 */
	private static ArrayList<Double> getPolygonGMarkerLatitudes(String[] polyCoordinates) {
		ArrayList<Double> latitudes = new ArrayList<>();
		for (int i = 0; i < polyCoordinates.length; i += 2) {
			latitudes.add(Double.parseDouble(polyCoordinates[i]));
		}
		return latitudes;
	}

	/**
	 * @param polyCoordinates
	 * @param e
	 * @param temp
	 */
	private static void setPolygonGMarkerNames(String[] polyCoordinates, Event e, ArrayList<GMarker> temp) {
		GMarker gMarker;
		for (int i = 0; i < polyCoordinates.length / 2; ++i) {
			gMarker = new GMarker();
			gMarker.setName(e.getTitle());
			temp.add(gMarker);
		}
	}
	
	public static String getNumDays(String userStartDate, String userEndDate) {
		String formattedUserStartDate = userStartDate.replaceAll("-", "");
		String formattedUserEndDate = userEndDate.replaceAll("-", "");
		LocalDate startDate = LocalDate.parse(formattedUserStartDate, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate endDate = LocalDate.parse(formattedUserEndDate, DateTimeFormatter.BASIC_ISO_DATE);
		Long differenceInDays = ChronoUnit.DAYS.between(startDate, endDate);
		String numDays = String.valueOf(differenceInDays);
		return numDays;
	}
}
