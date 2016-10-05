package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException; 
import java.util.Scanner; 

/**
 *  
 * @author Sean Hinchee  
 *
 */

/**
 * 
 * The PredatorPrey class performs the predator-prey simulation over a grid world 
 * with squares occupied by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class PredatorPrey 
{
	/**
	 * Update the new world from the old world in one cycle. 
	 * @param wOld  old world
	 * @param wNew  new world 
	 */
	public static void updateWorld(World wOld, World wNew)
	{
		// TODO 
		// 
		// For every life form (i.e., a Living object) in the grid wOld, generate  
		// a Living object in the grid wNew at the corresponding location such that 
		// the former life form changes into the latter life form. 
		// 
		// Employ the method next() of the Living class. 
		int i, j;
		for(i = 0; i < wOld.getWidth(); i++) {
			for(j = 0; j < wOld.getWidth(); j++) {
				//Living l = wOld.grid[i][j].next(wOld);
				//wNew.grid[i][j] = l;
				wNew.grid[i][j] = wOld.grid[i][j].next(wNew);
			}
		}
	}
	
	/**
	 * Repeatedly generates worlds either randomly or from reading files. 
	 * Over each world, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		// TODO 
		// 
		// Generate predator-prey simulations repeatedly like shown in the 
		// sample run in the project description. 
		// 
		// 1. Enter 1 to generate a random world, 2 to read a world from an input
		//    file, and 3 to end the simulation. (An input file always ends with 
		//    the suffix .txt.)
		// 
		// 2. Print out standard messages as given in the project description. 
		// 
		// 3. For convenience, you may define two worlds even and odd as below. 
		//    In an even numbered cycle (starting at zero), generate the world 
		//    odd from the world even; in an odd numbered cycle, generate even 
		//    from odd. 
		//
		// 4. Print out initial and final worlds only.  No intermediate worlds should
		//    appear in the standard output.  (When debugging your program, you can 
		//    print intermediate worlds.)
		// 
		// 5. You may save some randomly generated worlds as your own test cases. 
		// 
		// 6. It is not necessary to handle file input & output exceptions for this 
		//    project. Assume data in an input file to be correctly formated.
		
		World even = null;   // the world after an even number of cycles 
		World odd = null;    // the world after an odd number of cycles
		int trials = 1;
		int k = -1;
		Scanner s = new Scanner(System.in);
		System.out.println("The Predator-Prey Simulator");
		System.out.println("keys; 1 (random world 2 (file input) 3 (exit)");
		while(k != 3) {
			boolean stop = false;
			int w, cycles, c = 0;
			String path;
			System.out.printf("Trial %d: ", trials);
			k = s.nextInt();
			switch(k) {
			case 1:
				//random world
				System.out.println("Random world");
				System.out.printf("Enter the grid width: ");
				w = s.nextInt();
				even = new World(w);
				odd = new World(w);
				even.randomInit();
				break;
			case 2:
				//file input
				System.out.println("World input from a file");
				System.out.printf("file name: ");
				path = s.next();
				try {
					even = new World(path);
					odd = new World(even.getWidth());
				}
				catch (FileNotFoundException ex) {
					System.out.println("File Error!");
					ex.printStackTrace();
				}
				break;
			default:
				stop = true;
				break;
			}
			if(stop)
				break;
			
			//simulate world
			boolean evenEnd = false;;
			String initialWorld = even.toString(), finalWorld = null;
			System.out.printf("Enter the number of cycles: ");
			cycles = s.nextInt();
			for(c = 0; c < cycles; c++) {
				if(c % 2 == 0) {
					//even cycle
					updateWorld(even, odd);
					evenEnd = true;
				} else {
					//odd cycle
					updateWorld(odd, even);
					evenEnd = false;
				}
			}
			if(evenEnd) {
				//if ended on an even cycle
				finalWorld = odd.toString();
			} else {
				//if ended on an odd cycle
				finalWorld = even.toString();
			}
			
			System.out.printf("\nInitial world: \n\n%s\n", initialWorld);
			System.out.printf("Final world: \n\n%s\n", finalWorld);
			
			trials++;
		}
		s.close();
	}
}
