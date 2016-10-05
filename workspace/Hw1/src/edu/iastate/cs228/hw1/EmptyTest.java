package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import org.junit.*;

/**
 *  
 * @author Sean Hinchee  
 *
 */

public class EmptyTest {
	/**
	 * World for test case
	 */
	private World w;
	
	/**
	 * Living type to keep
	 */
	private Empty e;
	
	/**
	 * Setup world 3x3 grid
	 */
	@Before
	public void setup() {
		w = new World(3);
		e = new Empty(w, 0, 0);
		//make a neighborhood
		w.grid[0][0] = e;
		w.grid[0][1] = new Grass(w, 0, 1);
		w.grid[1][0] = new Grass(w, 1, 0);
	}
	
	/**
	 * Test .who() method
	 */
	@Test
	public void whoShouldBeState() {
		assertEquals("Who should return proper type", State.EMPTY, e.who());
	}
	
	/**
	 * Test .next() method
	 */
	@Test
	public void nextShouldBeNominal() {
		World w2 = new World(3);
		Living n = e.next(w2);
		assertEquals("With 2 grassen there should be a new grassen", State.GRASS, n.who());
	}
}
