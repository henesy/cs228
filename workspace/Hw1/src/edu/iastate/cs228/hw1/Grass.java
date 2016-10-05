package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sean Hinchee
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood;
 * otherwise, it is eaten. 
 *
 */
public class Grass extends Living 
{
	public Grass (World w, int r, int c) 
	{
		// TODO 
		world 	= w;
		row 	= r;
		column	= c;
	}
	
	public State who()
	{
		// TODO  
		return State.GRASS; 
	}
	
	/**
	 * Grass can be eaten out by too many rabbits in the neighborhood. Rabbits may also 
	 * multiply fast enough to take over Grass. 
	 */
	public Living next(World wNew)
	{
		// TODO 
		// 
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for grass. 
		int pop[] = new int[Living.NUM_LIFE_FORMS];
		this.census(pop);
		
		if(pop[Living.RABBIT] >= pop[Living.GRASS] * 3) {
			return new Empty(wNew, this.row, this.column);
		} else if(pop[Living.RABBIT] >= 3) {
			return new Rabbit(wNew, this.row, this.column, 0);
		} else {
			this.world = wNew;
			return this;
		}
	}
}
