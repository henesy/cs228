package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sean Hinchee
 *
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal 
{	
	/**
	 * Creates a Rabbit object.
	 * @param w: world  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (World w, int r, int c, int a) 
	{
		// TODO 
		world 	= w;
		row 	= r;
		column	= c;
		age		= a;
	}
		
	// Rabbit occupies the square.
	public State who()
	{
		// TODO  
		return State.RABBIT; 
	}
	
	/**
	 * A rabbit dies of old age or hunger, or it is eaten if there are as many 
	 * foxes and badgers in the neighborhood.  
	 * @param wNew     world of the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(World wNew)
	{
		// TODO 
		// 
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a rabbit. 
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		this.census(pop);
		
		if(this.age >= Living.RABBIT_MAX_AGE) {
			return new Empty(wNew, this.row, this.column);
		} else if(pop[Living.GRASS] < 1) {
			return new Empty(wNew, this.row, this.column);
		} else if(pop[Living.FOX] + pop[Living.BADGER] >= pop[Living.RABBIT] && pop[Living.BADGER] < pop[Living.FOX]) {
			return new Fox(wNew, this.row, this.column, 0);
		} else if(pop[Living.BADGER] > pop[Living.RABBIT]) {
			return new Badger(wNew, this.row, this.column, 0);
		} else {
			this.age++;
			this.world = wNew;
			return this;
		}
	}
}
