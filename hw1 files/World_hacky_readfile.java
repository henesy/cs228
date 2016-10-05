package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sean Hinchee
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 
import java.util.ArrayList;

/**
 * 
 * The world is represented as a square grid of size width X width. 
 *
 */
public class World 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public World(String inputFileName) throws FileNotFoundException
	{		
        // TODO 
		// 
		// Assumption: The input file is in correct format. 
		// 
		// You may create the grid world in the following steps: 
		// 
		// 1) Reads the first line to determine the width of the grid.
		// 
		// 2) Creates a grid object. 
		// 
		// 3) Fills in the grid according to the input file. 
		// 
		// Be sure to close the input file when you are done. 
		File file = new File(inputFileName);
		
		//read in the lines
		int i = 0, j, k = 0;
		ArrayList<String> alst = new ArrayList<String>();
		Scanner input = new Scanner(file);
		input.useDelimiter(" ");
		while(input.hasNext()) {
			String str = input.next();
			//System.out.println(str);
			if(str.contains("\n")) {
				i++;
				str = str.trim();
				str.replace(" ", "");
			}
			if(str.isEmpty()) {
				continue;
			}

			alst.add(str);
		}
		System.out.println("Ourlist: " + alst.toString());
		
		width = i+1;
		System.out.println(width);
		grid = new Living[width][width];
		for(i = 0; i < width; i++) {
			for(j = 0; j < width; j++) {
				//decide the type of Living thing and insert it
				System.out.printf("k: %d, i: %d, j: %d\n", k, i, j);
				String str = alst.get(k);
				//System.out.println(str);
				//alst.remove(0);

				k++;
				if(str.length() > 0) {
					char c = str.charAt(0);
					if(str.length() == 1) {
						//split the char out only
						if(c == 'E') {
							//empty
							grid[i][j] = new Empty(this, i, j);
						} else if(c == 'G') {
							//grass
							grid[i][j] = new Grass(this, i, j);
						}
					} else if(str.length() == 2) {
						//split the char and digit out
						int d = Character.getNumericValue(str.charAt(1));
						//System.out.println(d);
						if(c == 'B') {
							//badger
							grid[i][j] = new Badger(this, i, j, d);
						} else if(c == 'F') {
							//fox
							grid[i][j] = new Fox(this, i, j, d);
						} else if(c == 'R') {
							//rabbit
							grid[i][j] = new Rabbit(this, i, j, d);
						}
					}
				}
			}
		}
		
		input.close();
	}
	
	/**
	 * Constructor that builds a w X w grid without initializing it. 
	 * @param width  the grid 
	 */
	public World(int w)
	{
		grid = new Living[w][w];
		width = w;
	}
	
	
	public int getWidth()
	{
		// TODO  
		return width;  // to be modified 
	}
	
	/**
	 * Initialize the world by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random();
		int i, j;
		for(i = 0; i < grid.length; i++) {
			for(j = 0; j < grid[i].length; j++) {
				int n = generator.nextInt(Living.NUM_LIFE_FORMS);
				if(n == Living.BADGER) {
					grid[i][j] = new Badger(this, i, j, 0);
				} else if(n == Living.EMPTY) {
					grid[i][j] = new Empty(this, i, j);
				} else if(n == Living.FOX) {
					grid[i][j] = new Fox(this, i, j, 0);
				} else if(n == Living.GRASS) {
					grid[i][j] = new Grass(this, i, j);
				} else if(n == Living.RABBIT) {
					grid[i][j] = new Rabbit(this, i, j, 0);
				}
			}
		}
		 
		// TODO 
	}
	
	
	/**
	 * Output the world grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		// TODO
		int i, j;
		String str = "";
		for(i = 0; i < grid.length; i++) {
			for(j = 0; j < grid[i].length; j++) {
				boolean isAnimal;
				String toAdd;
				//System.out.println(grid[i][j].who());
				//should work; will be null until all who() fxns are implemented
				State ts = grid[i][j].who();
				if(ts == State.BADGER) {
					isAnimal = true;
					toAdd = "B";
				} else if(ts == State.FOX) {
					isAnimal = true;
					toAdd = "F";
				} else if(ts == State.RABBIT) {
					isAnimal = true;
					toAdd = "R";
				} else if(ts == State.EMPTY) {
					isAnimal = false;
					toAdd = "E";
				} else if(ts == State.GRASS) {
					isAnimal = false;
					toAdd = "G";
				} else {
					isAnimal = false;
					toAdd = "XX";
				}
				
				//System.out.println();
				//syso -> ctrl + space -> enter
				
				if(isAnimal) {
					//System.out.println(((Animal)grid[i][j]).myAge());
					str += toAdd + ((Animal)grid[i][j]).myAge();
					if(j + 1 == grid[i].length)
						str += " ";
				} else {
					str += toAdd + " ";
				}
				if(j + 1 != grid[i].length)
					str += " ";
			}
			str += "\n";
		}
		
		return str;
	}
	

	/**
	 * Write the world grid to an output file.  Also useful for saving a randomly 
	 * generated world for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		// TODO 
		// 
		// 1. Open the file. 
		// 
		// 2. Write to the file. The five life forms are represented by characters 
		//    B, E, F, G, R. Leave one blank space in between. Examples are given in
		//    the project description. 
		// 
		// 3. Close the file. 
		//assuming that we need to format the printing as per the printed screen output ?
		File file = new File(outputFileName);
		PrintWriter pw = new PrintWriter(file);
		String str = this.toString();
		pw.write(str);
		pw.close();
	}			
}
