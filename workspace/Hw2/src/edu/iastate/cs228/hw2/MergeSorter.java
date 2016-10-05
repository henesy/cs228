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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 * in the array. 
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		super.outputFileName = "merge.txt";
		super.algorithm = "mergesort";
		
	}
	
	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public MergeSorter(String inputFileName) throws InputMismatchException, FileNotFoundException 
	{
		super(inputFileName);
		super.outputFileName = "merge.txt";
		super.algorithm = "mergesort";
		
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
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

		mergeSortRec(points);
		
		sortingTime = System.nanoTime() - sortingTime;
		
		draw();
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		//recurse into mergesort from this point ;; carries out mergesort on array pts[] of points
		points = mergeSort(pts);
	}

	
	// Other private methods in case you need ...
	
	/**
	 * Performs merge sort recursively 
	 * @param pts
	 * @return sorted and merged version of array Point[] pts
	 */
	private Point[] mergeSort(Point[] pts) {
		if(pts.length <= 1)
			return pts;
				
		Point[] a = new Point[pts.length/2];
		Point[] b = new Point[pts.length - pts.length/2];
		
		int i;
		for(i = 0; i < a.length; i++) {
			a[i] = pts[i];
		}
		int j = pts.length/2;
		for(i = 0; i < b.length && j < pts.length; i++, j++) {
			b[i] = pts[j];
		}
		
		/* deal with our split arrays */
		
		Point[] c = mergeSort(a);
		Point[] d = mergeSort(b);
		int p = c.length;
		int q = d.length;
		
		Point[] e = new Point[p+q];
		
		//System.out.println("E length: " + e.length);
		
		int k = 0;
		i = 0;
		j = 0;
		while(i < p && j < q) {
			if(pointComparator.compare(c[i], d[j]) <= 0) {
				e[k] = c[i];
				i++;
				k++;
			} else {
				e[k] = d[j];
				j++;
				k++;
			}
		}
		if(i >= p) {
			for(; j < q; j++) {
				e[k] = d[j];
				k++;
			}
		} else {
			for(; i < p; i++) {
				e[k] = c[i];
				k++;
			}
		}
		
		
		return e;
	}

}
