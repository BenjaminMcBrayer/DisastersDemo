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
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.DisastersDemo.entity.nasa.Event;
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

	// Convert String to LocalDate
	public static LocalDate getMyLocalDateFromString(String myDate) {
		String formattedDate = myDate.replaceAll("-", "");
		LocalDate myLocalDate = LocalDate.parse(formattedDate, DateTimeFormatter.BASIC_ISO_DATE);
		return myLocalDate;
	}

	public static ArrayList<String> getDates(ArrayList<Event> events) {
		ArrayList<ArrayList<Geometry>> geometriesList = new ArrayList<>();
		ArrayList<String> dates = new ArrayList<>();
		for (Event e : events) {
			geometriesList.add(e.getGeometries());
		}
		for (ArrayList<Geometry> g : geometriesList) {
			for (int i = 0; i < g.size(); ++i) {
				dates.add(g.get(i).getDate());
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

	public static ArrayList<Event> getValidEvents(String[] validDatesArray, ArrayList<Event> events) {
		ArrayList<Event> validEvents = new ArrayList<>();
		String d = null;
		for (String s : validDatesArray) {
			System.out.println("Valid Date: " + s);
			for (Event e : events) {
				d = e.getGeometries().get(0).getDate();
				if (d.substring(0, 10).equals(s)) {
					validEvents.add(e);
				}
			}
		}
		return validEvents;
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
