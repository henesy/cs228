package edu.iastate.cs228.hw4;

/**
 *  
 * @author
 *
 */

import java.util.Comparator;

/**
 * 
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.  
 *  
 */
public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint; 
	private boolean flag;  // used for breaking a tie between two points which have 
	                       // the same polar angle with respect to referencePoint
	
	/**
	 * 
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p, boolean flag)
	{
		referencePoint = p; 
		this.flag = flag; 
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. See earlier PowerPoint notes for Project 2 on how to 
	 * carry out cross and dot products. Calls private methods crossProduct() and dotProduct(). 
	 * 
	 * Precondition: both p1 and p2 are different from referencePoint. 
	 * 
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 if one of the following three conditions holds: 
	 *                a) the cross product between p1 - referencePoint and p2 - referencePoint 
	 *                   is greater than zero. 
	 *                b) the above cross product equals zero, flag == true, and p1 is closer to 
	 *                   referencePoint than p2 is. 
	 *                c) the above cross product equals zero, flag == false, and p1 is further 
	 *                   from referencePoint than p2 is.   
	 *          1  otherwise. 
	 *                   
	 */
	public int compare(Point p1, Point p2)
	{
		// TODO
		return 0; 
	}
	    

    /**
     * 
     * @param p1
     * @param p2
     * @return cross product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int crossProduct(Point p1, Point p2)
    {
    	// TODO 
    	return 0; 
    }

    /**
     * 
     * @param p1
     * @param p2
     * @return dot product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int dotProduct(Point p1, Point p2)
    {
    	// TODO 
    	return 0; 
    }
}
