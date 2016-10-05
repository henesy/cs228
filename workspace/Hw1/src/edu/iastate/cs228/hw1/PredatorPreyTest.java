package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class PredatorPreyTest {
	/**
	 * World for test case
	 */
	private World wInitial;
	private World wFinal;
	
	/**
	 * Setup world 3x3 grid
	 * @throws FileNotFoundException 
	 */
	@Before
	public void setup() throws FileNotFoundException {
		wInitial = new World("public1.txt");
		wFinal = new World("public1-5cycles.txt");
	}
	
	/**
	 * Test for proper updating of a world
	 * @throws FileNotFoundException
	 */
	@Test
	public void shouldUpdateNominally() {
		String wFStr = wFinal.toString();
		World w2 = new World(wInitial.getWidth());
		
		int i;
		for(i = 0; i < 5; i++) {
			if(i % 2 == 0) {
				PredatorPrey.updateWorld(wInitial, w2);
			} else {
				PredatorPrey.updateWorld(w2, wInitial);
			}
		}
		
		assertEquals(w2.toString(), wFStr);
	}
}
