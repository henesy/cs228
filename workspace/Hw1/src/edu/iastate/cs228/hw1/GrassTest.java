package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class GrassTest {
	/**
	 * World for test case
	 */
	private World w;
	
	/**
	 * Grass element
	 */
	private Grass g;
	
	/**
	 * Setup world 3x3 grid
	 */
	@Before
	public void setup() {
		w = new World(3);
		g = new Grass(w, 0, 0);
		w.grid[0][0] = g;
		w.grid[1][0] = new Rabbit(w, 1, 0, 0);
		w.grid[0][1] = new Rabbit(w, 0, 1, 0);
		w.grid[1][1] = new Rabbit(w, 1, 1, 0);
	}
	
	/**
	 * Test .who() method
	 */
	@Test
	public void whoShouldBeState() {
		Grass g = new Grass(w, 0, 0);
		assertEquals("Who should return proper type", State.GRASS, g.who());
	}
	
	/**
	 * Test .next() method
	 */
	@Test
	public void nextShouldBeNominal() {
		World w2 = new World(3);
		Living n = g.next(w2);
		//System.out.println(n.toString());
		assertEquals("With 3*1 rabbits, there shold be a new grass", State.EMPTY, n.who());
	}
}
