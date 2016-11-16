package edu.iastate.cs228.hw4;

/**
 * @author Sean Hinchee
 */

import java.io.FileNotFoundException; 
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Comparator; 

public class JarvisMarch extends ConvexHull
{
	// last element in pointsNoDuplicate(), i.e., highest of all points (and the rightmost one in case of a tie)
	private Point highestPoint; 
	
	// left chain of the convex hull counterclockwise from lowestPoint to highestPoint
	private PureStack<Point> leftChain; 
	
	// right chain of the convex hull counterclockwise from highestPoint to lowestPoint
	private PureStack<Point> rightChain; 
		

	/**
	 * Call corresponding constructor of the super class.  Initialize the variable algorithm 
	 * (from the class ConvexHull). Set highestPoint. Initialize the two stacks leftChain 
	 * and rightChain. 
	 * 
	 * @param n  number of points 
	 * @throws IllegalArgumentException  when pts.length == 0
	 */
	public JarvisMarch(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		super.algorithm = "jarvis";
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
				
		//highest point
		Point lp = new Point();
		int i;
		for(i = 0; i < super.pointsNoDuplicate.length; i++) {
			if(i == 0) {
				lp = super.pointsNoDuplicate[i];
			} else if(lp.compareTo(super.pointsNoDuplicate[i]) < 0) {
				lp = super.pointsNoDuplicate[i];
			}
		}
		highestPoint = lp;
	}

	
	/**
	 * Call corresponding constructor of the superclass.  Initialize the variable algorithm.
	 * Set highestPoint.  Initialize leftChain and rightChain.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public JarvisMarch(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		super.algorithm = "jarvis";
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
		
		//highest point
		Point lp = new Point();
		int i;
		for(i = 0; i < super.pointsNoDuplicate.length; i++) {
			if(i == 0) {
				lp = super.pointsNoDuplicate[i];
			} else if(lp.compareTo(super.pointsNoDuplicate[i]) < 0) {
				lp = super.pointsNoDuplicate[i];
			}
		}
		highestPoint = lp;
	}


	// ------------
	// Javis' march
	// ------------

	/**
	 * Calls createRightChain() and createLeftChain().  Merge the two chains stored on the stacks  
	 * rightChain and leftChain into the array hullVertices[].
	 * 
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains only two points, in which case the hull is the line segment 
     *        connecting them.   
	 */
	public void constructHull()
	{
		time = System.nanoTime();
		
		if(pointsNoDuplicate.length == 1) {
			hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
		} else if(pointsNoDuplicate.length == 2) {
			hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
			hullVertices[1] = pointsNoDuplicate[1];
		} else {
			
			//////system.out.println("Before infinite loop here!");
			//infinite loop in here somewhere

			createRightChain();
			//////system.out.println("After right chain!");
			createLeftChain();
						
			hullVertices = new Point[ leftChain.size() + rightChain.size()];
			
			//merge left half then right half
			int i;
			for(i = 0; i < hullVertices.length; i++) {
				Point tmp;
				if(leftChain.size() > 0)
					tmp = leftChain.pop();
				else
					tmp = rightChain.pop();
				hullVertices[i] = tmp;
			}
			
		}
		time = System.nanoTime() - time;
		
		
	}
	
	
	/**
	 * Construct the right chain of the convex hull.  Starts at lowestPoint and wrap around the 
	 * points counterclockwise.  For every new vertex v of the convex hull, call nextVertex()
	 * to determine the next vertex, which has the smallest polar angle with respect to v.  Stop 
	 * when the highest point is reached.  
	 * 
	 * Use the stack rightChain to carry out the operation.  
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createRightChain()
	{
		//////system.out.println(highestPoint.toString() + " " + lowestPoint.toString());
		Point tmp = lowestPoint;
		Point tmpLast = new Point();
		rightChain.push(tmp);
		while(tmp.compareTo(highestPoint) != 0) {
			tmp = nextVertex(tmp);
			if(tmp.compareTo(tmpLast) == 0)
				break;
			//////system.out.println(tmp.toString());
			rightChain.push(tmp);
			tmpLast = tmp;
		}
	}
	
	
	/**
	 * Construct the left chain of the convex hull.  Starts at highestPoint and continues the 
	 * counterclockwise wrapping.  Stop when lowestPoint is reached.  
	 * 
	 * Use the stack leftChain to carry out the operation. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createLeftChain()
	{
		Point tmp = highestPoint;
		Point tmpLast = new Point();
		leftChain.push(tmp);
		while(tmp.compareTo(lowestPoint) != 0) {
			tmp = nextVertex(tmp);
			if(tmp.compareTo(tmpLast) == 0)
				break;
			//////system.out.println(tmp.toString());
			leftChain.push(tmp);
			tmpLast = tmp;
		}
	}
	
	
	/**
	 * Return the next vertex, which is less than all other points by polar angle with respect
	 * to the current vertex v. When there is a tie, pick the point furthest from v. Comparison 
	 * is done using a PolarAngleComparator object created by the constructor call 
	 * PolarAngleCompartor(v, false).
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param v  current vertex 
	 * @return
	 */
	public Point nextVertex(Point v)
	{
		PolarAngleComparator comp = new PolarAngleComparator(v, false);
		
		Point lp = super.pointsNoDuplicate[0];
		
		int i;
		for(i = 1; i < super.pointsNoDuplicate.length; i++) {
			if(comp.compare(super.pointsNoDuplicate[i], lp) < 0 && super.pointsNoDuplicate[i].compareTo(v) != 0) {
				lp = super.pointsNoDuplicate[i];
			} else if(comp.compare(super.pointsNoDuplicate[i], lp) == 0 && super.pointsNoDuplicate[i].compareTo(v) != 0) {
				if(comp.compareDistance(super.pointsNoDuplicate[i], lp) > 0)
					lp = super.pointsNoDuplicate[i];
			}
		}
		
		return lp; 
	}
}
