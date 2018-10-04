package com.DisastersDemo.service.nasa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NasaServiceTest {

	// Tests for getNumDays()
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

}
