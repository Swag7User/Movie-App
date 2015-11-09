package com.UZH.MovieApp.server.generating;

import static org.junit.Assert.*;

import org.junit.Test;

public class MovieTest {

	@Test
	public void test() {
		String testString="975900	/m/03vyhn	Ghosts of Mars	2001-08-24	14010832	98.0	{\"/m/02h40lc\": \"English Language\"}	{\"/m/09c7w0\": \"United States of America\"}	{\"/m/01jfsb\": \"Thriller\", \"/m/06n90\": \"Science Fiction\", \"/m/03npn\": \"Horror\", \"/m/03k9fj\": \"Adventure\", \"/m/0fdjb\": \"Supernatural\", \"/m/02kdv5l\": \"Action\", \"/m/09zvmj\": \"Space western\"}";
		Movie toTest=new Movie(testString);
		
		assertEquals(toTest.getFreebaseID(),"/m/03vyhn" );
		assertEquals(toTest.getWikiID(),"/m/03vyhn" );
		assertEquals(toTest.getName(),"/m/03vyhn" );
		assertEquals(toTest.get,"/m/03vyhn" );
		assertEquals(toTest.getFreebaseID(),"/m/03vyhn" );
		assertEquals(toTest.getFreebaseID(),"/m/03vyhn" );
	}

}
