package edu.iastate.cs228.hw2;

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


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 **/
	public static void main(String[] args) throws InputMismatchException, FileNotFoundException 
	{		
		// 
		// Conducts multiple sorting rounds. In each round, performs the following: 
		// 
		//    a) If asked to sort random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		//    b) Reassigns to elements in the array sorters[] (declared below) the references to the 
		//       four newly created objects of SelectionSort, InsertionSort, MergeSort and QuickSort. 
		//    c) Based on the input point order, carries out the four sorting algorithms in one for 
		//       loop that iterates over the array sorters[], to sort the randomly generated points
		//       or points from an input file.  
		//    d) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 2 of the project description. 
		// 	
		
		Scanner s = new Scanner(System.in);
		AbstractSorter[] sorters = new AbstractSorter[4]; 
		System.out.println("Comparison of Four Sorting Algorithms");
		System.out.println("keys: 1 (random integers)  2 (file input)  3 (exit)");
		System.out.println("order: 1 (by x-coordinate)  2 (by polar angle)" );

		int t = 1;
		while(true) {
			System.out.printf("Trial %d: ", t);
			int r = s.nextInt();
			if(r == 1) {
				//random ints
				System.out.printf("Enter number of random points: ");
				int length = s.nextInt();
				
				Point[] pts = CompareSorters.generateRandomPoints(length, new Random());
				sorters[0] = new SelectionSorter(pts);
				sorters[1] = new InsertionSorter(pts);
				sorters[2] = new MergeSorter(pts);
				sorters[3] = new QuickSorter(pts);
			} else if(r == 2) {
				//file input
				System.out.printf("File name: ");
				String name = s.next();
				
				sorters[0] = new SelectionSorter(name);
				sorters[1] = new InsertionSorter(name);
				sorters[2] = new MergeSorter(name);
				sorters[3] = new QuickSorter(name);
			} else {
				break;
			}
			
			int order = -1;
			while(order != 1 && order != 2) {
				System.out.printf("Order used in sorting: ");
				order = s.nextInt();
				if(order != 1 && order != 2)
					System.out.println("Please enter 1 or 2.");
			}
			
			System.out.printf("%12s\t%9s\t%9s\n", "algorithm", "size", "time (ns)");
			System.out.println("------------------------------------");
			
			int i;
			for(i = 0; i < sorters.length; i++) {
				sorters[i].sort(order);
				System.out.println(sorters[i].stats());
				sorters[i].writePointsToFile();

			}
			
			System.out.println("------------------------------------");
					
			t++;
		}
		s.close();
		
		
		// Within a sorting round, have each sorter object call the sort and draw() methods
		// in the AbstractSorter class.  You can visualize the result of each sort. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (cf. Section 2). 
		
	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
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
