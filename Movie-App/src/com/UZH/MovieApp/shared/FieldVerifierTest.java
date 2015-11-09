/**
 * 
 */
package com.UZH.MovieApp.shared;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Sandro
 *
 */
public class FieldVerifierTest {

	@Test
	public void test() {
		FieldVerifier fieldVerifier=new FieldVerifier();
		
		String testString="2";
		int returnint=fieldVerifier.giveBackInt(testString);
		assertEquals(returnint,2);
	}

}
