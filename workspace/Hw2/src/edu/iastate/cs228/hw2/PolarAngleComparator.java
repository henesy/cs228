package edu.iastate.cs228.hw2;

/**
 *  
 * @author Sean Hinchee
 *
 */

import java.util.Comparator;

/**
 * 
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.  
 * It is known that the reference point is not above either p1 or p2, and in the case that
 * either or both of p1 and p2 have the same y-coordinate, not to their right. 
 *
 */
public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint; 
	
	/**
	 * 
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p)
	{
		referencePoint = p; 
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. See the PowerPoint notes on how to carry out cross and 
	 * dot products. Calls private methods crossProduct() and dotProduct(). 
	 * 
	 * Call comparePolarAngle() and compareDistance(). 
	 * 
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 otherwise, if one of the following three conditions holds: 
	 *                a) p1 and referencePoint are the same point (hence p2 is a different point); 
	 *                b) neither p1 nor p2 equals referencePoint, and the polar angle of 
	 *                   p1 with respect to referencePoint is less than that of p2; 
	 *                c) neither p1 nor p2 equals referencePoint, p1 and p2 have the same polar 
	 *                   angle w.r.t. referencePoint, and p1 is closer to referencePoint than p2. 
	 *   
	 *          1  otherwise. 
	 *                   
	 */
	public int compare(Point p1, Point p2)
	{
	
		if(comparePolarAngle(p1, p2) == 0)
			return compareDistance(p1, p2);
		else
			return comparePolarAngle(p1, p2);
		
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
    	// TODO 
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
}
