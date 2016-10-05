package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class FoxTest {
	/**
	 * World for test case
	 */
	private World w;
	
	/**
	 * Rabbit element
	 */
	private Fox f;
	
	/**
	 * Setup world 3x3 grid
	 */
	@Before
	public void setup() {
		w = new World(3);
		f = new Fox(w, 0, 0, 0);
		w.grid[0][0] = f;
		w.grid[0][1] = new Badger(w, 0, 1, 0);
		w.grid[1][1] = new Badger(w, 1, 1, 0);
	}
	
	/**
	 * Test .who() method
	 */
	@Test
	public void whoShouldBeState() {
		Fox e = new Fox(w, 0, 0, 0);
		assertEquals("Who should return proper type", State.FOX, e.who());
	}
	
	/**
	 * Test .next() method
	 */
	@Test
	public void nextShouldBeNominal() {
		World w2 = new World(3);
		Living n = f.next(w2);
		//System.out.println(n.toString());
		assertEquals("A fox, when outnumbered, will be replaced with a Badger", State.BADGER, n.who());
	}
}
