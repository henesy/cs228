package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sean Hinchee
 *
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living 
{
	public Empty (World w, int r, int c) 
	{
		// TODO  
		world 	= w;
		row 	= r;
		column	= c;
	}
	
	public State who()
	{
		// TODO 
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or 
	 * remain empty. 
	 * @param wNew     world of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(World wNew)
	{
		// TODO 
		// 
		// See Living.java for an outline of the function. 
		// See the project description for corresponding survival rules. 
		int pop[] = new int[5];
		this.census(pop);
		
		if(pop[Living.RABBIT] > 1) {
			return new Rabbit(wNew, this.row, this.column, 0);
		} else if(pop[Living.FOX] > 1) {
			return new Fox(wNew, this.row, this.column, 0);
		} else if(pop[Living.BADGER] > 1) {
			return new Badger(wNew, this.row, this.column, 0);
		} else if(pop[Living.GRASS] >= 1) {
			return new Grass(wNew, this.row, this.column);
		} else {
			world = wNew;
			return this;
		}
	}
}
