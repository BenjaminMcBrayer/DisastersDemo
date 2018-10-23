package com.DisastersDemo.service.nasa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class NasaServiceTest {

	// getNumDays()
	@Test
	void test1() {
		String expected = "2191";
		String actual = NasaService.getNumDays("2012-04-12", "2018-04-12");
		assertEquals(expected, actual);
	}

	@Test
	void test2() {
		String expected = "3536";
		String actual = NasaService.getNumDays("2006-07-04", "2016-03-09");
		assertEquals(expected, actual);
	}

	@Test
	void test3() {
		String expected = "18510";
		String actual = NasaService.getNumDays("1967-07-05", "2018-03-09");
		assertEquals(expected, actual);
	}

	// getCoordinatesList()
	@Test
	void test4() {
		ArrayList<Double[]> expected = new ArrayList<>();
		Double[] a = { 1.0, 2.0 };
		Double[] b = { 3.0, 4.0 };
		Double[] c = { 5.0, 6.0 };
		expected.add(a);
		expected.add(b);
		expected.add(c);
		//ArrayList<Event> validEvents = new ArrayList<>();
		//TODO: Get one Double[] and one Double[][].
		//ArrayList<Double[]> actual = NasaService.getCoordinatesList(validEvents);
		//assertEquals(expected, actual);
	}

}
