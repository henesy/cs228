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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
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
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		super.algorithm = "selection sort";
		super.outputFileName = "select.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public SelectionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.algorithm = "selection sort";
		super.outputFileName = "select.txt";
	}
	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 *
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		setComparator(order);
		
		sortingTime = System.nanoTime();
		
		int i;
		int lowest;
		for(i = 0; i < points.length-1; i++) {
			lowest = i;
			int j;
			for(j = i+1; j < points.length; j++) {
				if(pointComparator.compare(points[j], points[lowest]) < 0)
					lowest = j;
			}
			if(lowest != i) {
				swap(i, lowest);
				/*
				Point tmp = points[i];
				points[i] = points[lowest];
				points[lowest] = tmp;
				*/
			}
		}
		
		sortingTime = System.nanoTime() - sortingTime;
		
		draw();
	}
}
