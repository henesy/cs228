package edu.iastate.cs228.hw3;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class Node 
{
    public String fruit;   		// name of the fruit  
    public int quantity; 
    public int bin; 			// number for storage bin 
    
    public Node nextN;			 
    public Node previousN;
    public Node nextB; 
    public Node previousB; 
    
    /**
     * Creates a dummy node. 
     */
    public Node()
    {  	
    	// no implementation needed
    }
    
    
    /**
     * 
     * @param fruit			name of this type of fruit 
     * @param quantity		e.g., number of units (i.e., # apples, # bunches of grapes, etc.)
     * @param bin			number of the storage bin for the fruit
     * @param nextN         next node in the N-list sorted by name 
     * @param previousN 	previous node in the N-list 
     * @param nextB			next node in the B-list sorted by storage bin number 
     * @param previousB		previous node in the B list 
     */
    public Node(String fruit, int quantity, int bin, 
    		Node nextN, Node previousN, Node nextB, Node previousB)
    {
      this.fruit = fruit;
      this.quantity = quantity; 
      this.bin = bin;
      this.nextN = nextN;
      this.previousN = previousN; 
      this.nextB = nextB; 
      this.previousB = previousB; 
    }
    
    /**
     * Write out the fruit name, quantity, and bin number stored at the node.
     */
    public String toString() 
    {
    	// TODO 
    	
        return fruit + "\t" + quantity + "\t" + bin + "\n"; 
    }
}
