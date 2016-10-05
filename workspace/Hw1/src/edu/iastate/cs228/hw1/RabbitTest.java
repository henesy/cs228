package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class RabbitTest {
	/**
	 * World for test case
	 */
	private World w;
	
	/**
	 * Rabbit element
	 */
	private Rabbit r;
	
	/**
	 * Setup world 3x3 grid
	 */
	@Before
	public void setup() {
		w = new World(3);
		r = new Rabbit(w, 0, 0, 0);
		w.grid[0][0] = r;
	}
	
	/**
	 * Test .who() method
	 */
	@Test
	public void whoShouldBeState() {
		assertEquals("Who should return proper type", State.RABBIT, r.who());
	}
	
	/**
	 * Test .next() method
	 */
	@Test
	public void nextShouldBeNominal() {
		World w2 = new World(3);
		Living n = r.next(w2);
		//System.out.println(n.toString());
		assertEquals("A rabbit without grass is a dead rabbit", State.EMPTY, n.who());
	}
}
