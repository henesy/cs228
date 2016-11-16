package edu.iastate.cs228.hw4;

/**
 *  
 * @author Sean Hinchee
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
		if(p1.compareTo(p2) == 0)
			return 0;
		
		if(crossProduct(p1, p2) > 0)
			return -1;
		
		if(flag == true && compareDistance(p1, p2) < 0 && crossProduct(p1, p2) == 0)
			return -1;
		
		if(flag == false && compareDistance(p1, p2) > 0 && crossProduct(p1, p2) == 0)
			return -1;
		
		return 1;
	}
	    

    /**
     * 
     * @param p1
     * @param p2
     * @return cross product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int crossProduct(Point p1, Point p2)
    {
    	Point a = new Point(p1.getX() - referencePoint.getX(), p1.getY() - referencePoint.getY());
    	Point b = new Point(p2.getX() - referencePoint.getX(), p2.getY() - referencePoint.getY());
    	return a.getX() * b.getY() - b.getX() * a.getY(); 
    }

    /**
     * 
     * @param p1
     * @param p2
     * @return dot product of two vectors p1 - referencePoint and p2 - referencePoint
     */
    private int dotProduct(Point p1, Point p2)
    {
    	Point a = new Point(p1.getX() - referencePoint.getX(), p1.getY() - referencePoint.getY());
    	Point b = new Point(p2.getX() - referencePoint.getX(), p2.getY() - referencePoint.getY());
    	return a.getX() * b.getX() + a.getY() * b.getY(); 
    }
    
    /**
	 * Compare the polar angles of two points p1 and p2 with respect to referencePoint.  Use 
	 * cross products.  Do not use trigonometric functions. 
	 * 
	 * Ought to be private but made public for testing purpose. 
	 * 
	 * @param p1
	 * @param p2
	 * @return    0  if p1 and p2 have the same polar angle.
	 * 			 -1  if p1 equals referencePoint or its polar angle with respect to referencePoint
	 *               is less than that of p2. 
	 *            1  otherwise. 
	 */
    public int comparePolarAngle(Point p1, Point p2) 
    {
    	int r = crossProduct(p1, p2);
    	
    	r *= -1;
    	
    	if(r < 0)
    		r = -1;
    	else if(r > 0)
    		r = 1;
    	else
    		r = 0;
    	
    	return r;
    }
    
    
    /**
     * Compare the distances of two points p1 and p2 to referencePoint.  Use dot products. 
     * Do not take square roots. 
     * 
     * Ought to be private but made public for testing purpose.
     * 
     * @param p1
     * @param p2
     * @return    0   if p1 and p2 are equidistant to referencePoint
     * 			 -1   if p1 is closer to referencePoint 
     *            1   otherwise (i.e., if p2 is closer to referencePoint)
     */
    public int compareDistance(Point p1, Point p2)
    {
    	int p1d = dotProduct(p1, p1);
    	int p2d = dotProduct(p2, p2);
    	int r = p1d - p2d;
    	
    	if(r < 0)
    		r = -1;
    	else if(r > 0)
    		r = 1;
    	else
    		r = 0;
    	
    	return r; 
    }
}
