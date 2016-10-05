package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class WorldTest {
	/**
	 * World for test case
	 */
	private World wR;
	
	/**
	 * Setup world 3x3 grid
	 */
	@Before
	public void setup() {
		wR = new World(3);
		wR.randomInit();
	}
	
	/**
	 * Test for proper writing and reading of file
	 * @throws FileNotFoundException
	 */
	@Test
	public void shouldReadInNominally() throws FileNotFoundException {
		String wName = "junit-test-world.txt";
		wR.write(wName);
		World w2 = new World(wName);
		assertEquals(w2.toString(), wR.toString());
	}
}
