package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class LivingTest {
	
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
	 * Check if census works as intended
	 */
	@Test
	public void censusShouldCountCorrectly() {
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		w.grid[0][0].census(pop);
		assertEquals(pop[Living.BADGER], 1);
		assertEquals(pop[Living.FOX], 2);
		assertEquals(pop[Living.GRASS], 1);
	}
}
