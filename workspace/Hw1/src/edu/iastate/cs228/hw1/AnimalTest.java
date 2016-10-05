package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class AnimalTest {
	
	/**
	 * Internal world
	 */
	private World w;
	
	/**
	 * Setup method
	 * @throws FileNotFoundException 
	 */
	@Before
	public void setup() throws FileNotFoundException {
		w = new World("public1.txt");
	}
	
	/**
	 * Test if ages are set correctly/returned correctly
	 */
	@Test
	public void shouldReturnCorrectAge() {
		assertEquals(((Animal)w.grid[1][0]).myAge(), 0);
	}
}
