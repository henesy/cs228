package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sean Hinchee
 *
 */

/**
 * A fox eats rabbits and competes against a badger. 
 */
public class Fox extends Animal 
{
	/**
	 * Constructor 
	 * @param w: world
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (World w, int r, int c, int a) 
	{
		// TODO 
		world 	= w;
		row 	= r;
		column	= c;
		age		= a;
	}
		
	/**
	 * A fox occupies the square. 	 
	 */
	public State who()
	{
		// TODO 
		return State.FOX; 
	}
	
	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param wNew     world of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(World wNew)
	{
		// TODO 
		// 
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a fox. 
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		this.census(pop);
		
		if(this.age >= Living.FOX_MAX_AGE) {
			return new Empty(wNew, this.row, this.column);
		} else if(pop[Living.BADGER] > pop[Living.FOX]) {
			return new Badger(wNew, this.row, this.column, 0);
		} else if(pop[Living.BADGER] + pop[Living.FOX] > pop[Living.RABBIT]) {
			return new Empty(wNew, this.row, this.column);
		} else {
			this.age++;
			this.world = wNew;
			return this;
		}
	}
}
