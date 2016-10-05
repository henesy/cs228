package edu.iastate.cs228.hw2;

/**
 *  
 * @author Sean Hinchee
 *
 */

import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence and records the employed sorting algorithm, 
 * the comparison method, and the time spent on sorting. 
 *
 */


public abstract class AbstractSorter
{
	
	protected Point[] points;    // Array of points operated on by a sorting algorithm. 
	                             // The number of points is given by points.length.
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass. 
									   // constructor.
	protected boolean sortByAngle;     // true if the last sorting was done by polar angle and  
									   // false if by x-coordinate 
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long sortingTime; 	   // execution time in nanoseconds. 
	 
	protected Comparator<Point> pointComparator;  // comparator which compares polar angle if 
												  // sortByAngle == true and x-coordinate if 
												  // sortByAngle == false 
	
	private Point lowestPoint; 	    // lowest point in the array, or in case of a tie, the
									// leftmost of the lowest points. This point is used 
									// as the reference point for polar angle based comparison.

	
	// Add other protected or private instance variables you may need. 
	
	protected AbstractSorter()
	{
		// No implementation needed. Provides a default super constructor to subclasses. 
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter, and QuickSorter.
	}
	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * Sets the instance variable lowestPoint.
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		// TODO 
		if(pts == null || pts.length == 0)
				throw new IllegalArgumentException();
		points = new Point[pts.length];
		Point lp = new Point();
		int i;
		for(i = 0; i < pts.length; i++) {
			if(i == 0) {
				lp = pts[i];
			} else if(lp.compareTo(pts[i]) > 0) {
				lp = pts[i];
			}
			points[i] = pts[i];
		}
		lowestPoint = lp;
	}

	
	/**
	 * This constructor reads points from a file. Sets the instance variables lowestPoint and 
	 * outputFileName.
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		Scanner s;
		Point lp;
		ArrayList<Integer> al = new ArrayList<Integer>();
		ArrayList<String> lines = new ArrayList<String>();
		
		File f = new File(inputFileName);
		s = new Scanner(f);
		lp = new Point();
		
		while(s.hasNextLine()) {
			lines.add(s.nextLine());
		}
		
		int i;
		for(i = 0; i < lines.size(); i++) {
			s = new Scanner(lines.get(i));
			while(s.hasNextInt()) {
				al.add(s.nextInt());
			}
		}

		
		//System.out.println(al.toString());
		
		if(al.size() % 2 != 0) {
			s.close();
			throw new InputMismatchException();
		}
		
		points = new Point[al.size() / 2];
		int j;
		for(i = 0, j = 0; i < al.size(); i+=2, j++) {
			Point p = new Point(al.get(i), al.get(i+1));
			if(i == 0) {
				lp = p;
			} else if(lp.compareTo(p) > 0) {
				lp = p;
			}
			points[j] = p;
			//System.out.println(p.toString());
		}
		lowestPoint = lp;
		//outputFileName = ???; //set by child sorter constructor method ;; this should be called as super();
		s.close();
	}
	

	/**
	 * Sorts the elements in points[]. 
	 * 
	 *     a) in the non-decreasing order of x-coordinate if order == 1
	 *     b) in the non-decreasing order of polar angle w.r.t. lowestPoint if order == 2 
	 *        (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls the method 
	 * setComparator() to set the variable pointComparator and use it in sorting.    
	 * Records the sorting time (in nanoseconds) using the System.nanoTime() method. 
	 * (Assign the time spent to the variable sortingTime.)  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle w.r.t lowestPoint 
	 *
	 * @throws IllegalArgumentException if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException; 
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String s = "";
		s += String.format("%17s", algorithm);
		s += "\t" + points.length + "\t";
		s += sortingTime + "\n";
		return s;
	}
	
	
	/**
	 * Write points[] to a string.  When printed, the points will appear in order of increasing
	 * index with every point occupying a separate line.  The x and y coordinates of the point are 
	 * displayed on the same line with exactly one blank space in between. 
	 */
	@Override
	public String toString()
	{
		String s = "";
		
		int i;
		for(i = 0; i < points.length; i++) {
			s += String.format("%d %d\n", points[i].getX(), points[i].getY());
		}
		
		return s;
	}
	
	
	 /**
	 * This method, called after sorting, writes point data into a file by outputFileName.<br>
	 * The format of data in the file is the same as printed out from toString().<br>
	 * The file can help you verify the full correctness of a sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */

	 public void writePointsToFile() throws FileNotFoundException {
		 try {
			 File file = new File(outputFileName);
			 PrintWriter pw = new PrintWriter(file);
			 pw.write(toString());
			 pw.close();
		 } catch (FileNotFoundException e) {
			 System.out.println("Failed to write to file!");
			 return;
		 }
	 }
	 

	/**
	 * This method is called after sorting for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw 

		// Based on Section 4.1, generate the line segments to draw for display of the sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs]; 
		
		if(sortByAngle) {
			ArrayList<Segment> al = new ArrayList<Segment>();
			int i;
			for(i = 0; i < points.length; i++) {
				if(!points[i].equals(lowestPoint))
					al.add(new Segment(lowestPoint, points[i]));
				
				if(i+1 < points.length)
					if(!points[i].equals(points[i+1])) {
						al.add(new Segment(points[i], points[i+1]));
					}
			}
			
			segments = new Segment[al.size()];
			for(i = 0; i < al.size(); i++) {
				segments[i] = al.get(i);
			}
			
		} else {
			//not sorting by angle;
			segments = new Segment[points.length-1];
			int i, j;
			for(i = 0, j = 0; i < points.length && j < segments.length; i++, j++) {
				segments[j] = new Segment(points[i], points[i+1]);
			}
		}

		
		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, getClass().getName());
		
	}
		
	/**
	 * Generates a comparator on the fly that compares by polar angle if sortByAngle == true
	 * and by x-coordinate if sortByAngle == false. Set the protected variable pointComparator
	 * to it. Need to create an object of the PolarAngleComparator class and call the compareTo() 
	 * method in the Point class, respectively for the two possible values of sortByAngle.  
	 * 
	 * @param order
	 */
	protected void setComparator(int order) 
	{
		if(order == 2){
			//clarification on if this is the correct approach
			pointComparator = new PolarAngleComparator(lowestPoint);
			sortByAngle = true;
		} else {
			//as per a BB discussion ;; anonymous class
			Comparator<Point> comp = new Comparator<Point>() {
				@Override
				public int compare(Point p, Point q) {
					return p.compareTo(q);
				}
			};
			pointComparator = comp;
			sortByAngle = false;
		}
	}

	
	/**
	 * Swap the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		Point t = points[i];
		points[i] = points[j];
		points[j] = t;
	}	
}
