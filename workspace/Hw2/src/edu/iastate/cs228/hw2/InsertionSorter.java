package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.InputMismatchException;
import java.lang.IllegalArgumentException; 


/**
 *  
 * @author Sean Hinchee
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points. 
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		super.algorithm = "insertion sort";
		super.outputFileName = "insert.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public InsertionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.algorithm = "insertion sort";
		super.outputFileName = "insert.txt";
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 */
	@Override 
	public void sort(int order)
	{
		setComparator(order);
		
		sortingTime = System.nanoTime();
		
		int i;
		for(i = 1; i < points.length; i++) {
			Point tmp = points[i];
			int h = i;
			
			while(h > 0 && pointComparator.compare(points[h - 1], tmp) > 0) {
				points[h] = points[h-1];
				h--;
			}
			if(h != i) {
				points[h] = tmp;
			}
		}
		
		sortingTime = System.nanoTime() - sortingTime;
		
		draw();
	}
}
