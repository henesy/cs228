package edu.iastate.cs228.hw4;

/**
 *  
 * @author Sean Hinchee
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) 
	{		
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 4 of the project description. 
		// 	
		
		//ConvexHull[] algorithms = new ConvexHull[2]; 
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 4). 
		
		Scanner s = new Scanner(System.in);
		ConvexHull[] sorters = new ConvexHull[2]; 
		System.out.println("Comparison of Convex Hull Algorithms");
		System.out.println("keys: 1 (random integers)  2 (file input)  3 (exit)");

		int t = 1;
		while(true) {
			System.out.printf("Trial %d: ", t);
			int r = s.nextInt();
			if(r == 1) {
				//random ints
				System.out.printf("Enter number of random points: ");
				int length = s.nextInt();
				
				Point[] pts = generateRandomPoints(length, new Random());
				sorters[0] = new GrahamScan(pts);
				sorters[1] = new JarvisMarch(pts);
			} else if(r == 2) {
				//file input
				System.out.printf("File name: ");
				String name = s.next();
				
				try {
					sorters[0] = new GrahamScan(name);
				} catch (InputMismatchException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					sorters[1] = new JarvisMarch(name);
				} catch (InputMismatchException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			
			System.out.printf("%12s\t%9s\t%9s\n", "algorithm", "size", "time (ns)");
			System.out.println("------------------------------------");
			
			int i;
			for(i = 0; i < sorters.length; i++) {
				//run algos 
				sorters[i].constructHull();
				sorters[i].draw();
				System.out.println(sorters[i].stats());
				sorters[i].writeHullToFile();

			}
			
			System.out.println("------------------------------------");
					
			t++;
		}
		s.close();
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50] � [-50,50]. 
	 * 
	 * Reuse your implementation of this method in the CompareSorter class from Project 2.
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if(numPts < 1)
			throw new IllegalArgumentException();
		
		Point[] pts = new Point[numPts];
		int i;
		for(i = 0; i < pts.length; i++) {
			pts[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}
		
		return pts;
	}
}
