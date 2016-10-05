package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class BadgerTest {
	/**
	 * World for test case
	 */
	private World w;
	
	/**
	 * Badger element
	 */
	private Badger b;
	
	/**
	 * Setup world 3x3 grid
	 */
	@Before
	public void setup() {
		w = new World(3);
		b = new Badger(w, 0, 0, 4);
		w.grid[0][0] = b;
		w.grid[0][1] = new Fox(w, 0, 1, 0);
		w.grid[0][1] = new Rabbit(w, 1, 1, 0);
	}
	
	/**
	 * Test .who() method
	 */
	@Test
	public void whoShouldBeState() {
		assertEquals("Who should return proper type", State.BADGER, b.who());
	}
	
	/**
	 * Test .next() method
	 */
	@Test
	public void nextShouldBeNominal() {
		World w2 = new World(3);
		Living n = b.next(w2);
		//System.out.println(n.toString());
		assertEquals("An old badger should be a dead badger", State.EMPTY, n.who());
	}
}
