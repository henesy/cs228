package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sean Hinchee 
 *
 */

/**
 * A badger eats a rabbit and competes against a fox. 
 */
public class Badger extends Animal
{
	/**
	 * Constructor 
	 * @param w: world
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Badger (World w, int r, int c, int a) 
	{
		// TODO 
		world 	= w;
		row 	= r;
		column	= c;
		age		= a;
		//System.out.println("Badger: " + a);
	}
	
	/**
	 * A badger occupies the square. 	 
	 */
	public State who()
	{
		// TODO 
		return State.BADGER; 
	}
	
	/**
	 * A badger dies of old age or hunger, from isolation and attack by a group of foxes. 
	 * @param wNew     world of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(World wNew)
	{
		// TODO 
		// 
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a badger. 
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		this.census(pop);
		
		if(this.age >= Living.BADGER_MAX_AGE) {
			return new Empty(wNew, this.row, this.column);
		} else if(pop[Living.FOX] > 1 && pop[Living.BADGER] == 1) {
			return new Fox(wNew, this.row, this.column, 0);
		} else if(pop[Living.FOX] + pop[Living.BADGER] > pop[Living.RABBIT]) {
			return new Empty(wNew, this.row, this.column);
		} else {
			this.world = wNew;
			this.age++;
			return this;
		}
	}
}
