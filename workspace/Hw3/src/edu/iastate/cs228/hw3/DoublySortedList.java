package edu.iastate.cs228.hw3;

import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.Comparator;

/**
 * 
 * @author  Sean Hinchee
 *
 */

/**
 * IMPORTANT: In the case of any minor discrepancy between the comments before a method
 * and its description in the file proj3.pdf, use the version from the file. 
 *
 */

public class DoublySortedList 
{
	 private int size;     			// number of different kinds of fruits	 
	 private Node headN; 			// dummy node as the head of the sorted linked list by fruit name 
	 private Node headB; 			// dummy node as the head of the sorted linked list by bin number
	 
	 // added
	 private int maxFruit = 36; //36 common fruits
	 private int highBin;

	 /**
	  *  Default constructor constructs an empty list. Initialize size. Set the fields nextN and 
	  *  previousN of headN to the node itself. Similarly, set the fields nextB and previousB of 
	  *  headB. 
	  */
	 public DoublySortedList()
	 {
		 size = 0;
		 
		 Node h = new Node();
		 headN = h;
		 headB = h;
		 
		 headN.nextN = headN;
		 headN.previousN = headN;
		 headB.nextB = headB;
		 headB.previousB = headB;
		 highBin = 1;
	 }
	 
	 
	 /**
	  * Constructor over an inventory file consists of lines in the following format  
	  * 
	  * <fruit>  <quantity>  <bin> 
	  * 
	  * Throws an exception if the file is not found. 
	  * 
	  * You are asked to carry out the following operations: 
	  * 
	  *     1. Scan line by line to construct one Node object for each fruit.  
	  *     2. Create the two doubly-linked lists, by name and by bin number, respectively,
	  *        on the fly as the scan proceeds. 
	  *     3. Perform insertion sort on the two lists. Use the provided BinComparator and 
	  *        NameComparator classes to generate comparator objects for the sort.
	  *        
	  * @inventoryFile    name of the input file 
	  */
	 public DoublySortedList(String inventoryFile) throws FileNotFoundException
	 {
		 /* init list */
		 size = 0;
		 
		 Node h = new Node();
		 headN = h;
		 headB = h;
		 
		 headN.nextN = headN;
		 headN.previousN = headN;
		 headB.nextB = headB;
		 headB.previousB = headB;
		 
		 highBin = 1;
		 
		 /* load the file */
		 Node previousB = new Node();
		 Node previousN = new Node();
		 boolean first = true;
		 File file = new File(inventoryFile);
		Scanner s = new Scanner(file);
		while(s.hasNextLine()) {
			String line = s.nextLine();
			Scanner l = new Scanner(line);
			//System.out.println(line);
			String type = l.next();
			int quantity = l.nextInt();
			int bin = l.nextInt();
			
			//init & add Node
			if(first) {
				Node n = new Node(type, quantity, bin, headN, headN, headB, headB);
				n.previousB.nextB = n;
				n.previousN.nextN = n;
				previousB = n;
				previousN = n;
				first = false;
			} else {
				Node n = new Node(type, quantity, bin, headN, previousN, headB, previousB);
				n.previousB.nextB = n;
				n.previousN.nextN = n;
				previousB = n;
				previousN = n;
			}
			
			if(bin > highBin)
				highBin = bin;
			
			size++;
			l.close();
		}
		s.close();
		 
		 /* insertion sort the lists */
		 //generate comparator objects
		 insertionSort(true, new NameComparator());
		 insertionSort(false, new BinComparator());
	 }
	 
	 
	 /**
	  * Called by split() and also used for testing.  
	  * 
	  * Precondition: The doubly sorted list has already been created. 
	  * 
	  * @param size
	  * @param headN
	  * @param headB
	  */
	 public DoublySortedList(int size, Node headN, Node headB)
	 {
		 this.size = size; 
		 this.headN = headN;
		 this.headB = headB;
	 }

	 
	 public int size()
	 {
		 return size; 
	 }

	 
	 /**
	  * Add one type of fruits in given quantity (n). 
	  * 
	  *     1. Search for the fruit. 
	  *     2. If already stored in some node, simply increase the quantity by n
	  *     3. Otherwise, create a new node to store the fruit at the first available bin.
	  *        add it to both linked lists by calling the helper methods insertN() and insertB(). 
	  *        
	  * The case n == 0 should result in no operation.  The case n < 0 results in an 
	  * exception thrown. 
	  * 
	  * @param fruit                      name of the fruit to be added 
	  * @param n	                      quantity of the fruit
	  * @throws IllegalArgumentException  if n < 0 
	  */
	 public void add(String fruit, int n) throws IllegalArgumentException
	 {
		 if(n < 0)
			 throw new IllegalArgumentException();
		 
		 //search for fruit
		Node res = findFruit(fruit);
		if(res != null) {
			res.quantity += n;
		} else {
		 
			 //create a new node to store the fruit at the first available bin
			//fruit, quant, bin, nextN prevN nextB prevB
			 Node nNode = new Node(fruit, n, highBin, headN, headN.previousN, headB, headB.previousB);
			 headB.previousB.nextB = nNode;
			 headN.previousN.nextN = nNode;
			 headB.previousB = nNode;
			 headB.previousN = nNode;
			 highBin++;
			 size++;
		}
	 }
	 
	 
	 /**
	  * The fruit list is not sorted.  For efficiency, you need to sort by name using quickSort. 
	  * Maintain two arrays fruitName[] and fruitQuant[].  
	  * 
	  * After sorting, add the fruits to the doubly-sorted list (see project description) using 
	  * the algorithm described in Section 3.2 of the project description. 
	  * 
	  * Ignore a fruit if its quantity in fruitFile is zero.  
	  * 
	  * @param fruitFile                  list of fruits with quantities. one type of fruit 
	  *                                   followed by its quantity per line.
	  * @throws FileNotFoundException
	  * @throws IllegalArgumentException  if the quantity specified for some fruit in fruitFile 
	  *                                   is negative.
	  */
	 public void restock(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	 {
		 //read the file
		 String[] fruitsMax = new String[maxFruit];
		 int[] quantsMax = new int[maxFruit];
		 
		File file = new File(fruitFile);
		Scanner s = new Scanner(file);
		int i = 0, j = 0;
		while(s.hasNextLine()) {
			String line = s.nextLine();
			Scanner l = new Scanner(line);
			//System.out.println(line);
			String fruit = l.next();
			int quantity = l.nextInt();
			
			if(quantity < 0) {
				l.close();
				s.close();
				throw new IllegalArgumentException();
			}
			
			fruitsMax[i] = fruit;
			quantsMax[j] = quantity;
			i++;
			j++;
			
			l.close();
		}
		s.close();
		int len = i; // was i+1
		
		String[] fruits = new String[len];
		 Integer[] quants = new Integer[len];
		 
		 for(i = 0; i < len; i++) {
			 fruits[i] = fruitsMax[i];
			 quants[i] = quantsMax[i];
		 }
		 
		 
		 //sort the 2 arrays with Quicksort
		 quickSort(fruits, quants, len);
		 
		 
		 //restock the fruits
		 /****
		  * Broken as shit
		  */
		 
		 /****
		 if(size > 0) {
			//walk the lists
			Node cN = headN.nextN;
			Node cB = headB.nextB;
			int h;

			for(h = 0; h < size; h++, cN = cN.nextN, cB = cB.nextB) {
				Node cursorN = cN;
				Node cursorB = cB;
				for(i = h; i < size; i++, cursorN = cursorN.nextN, cursorB = cursorB.nextB) {
					if(cursorN.fruit.compareTo(fruits[i]) >= 0) {
						if(cursorN.fruit.compareTo(fruits[i]) == 0) {
							//same fruit
							cursorN.quantity += quants[i];
						} else {
							//new type
							Node M = new Node();
							M.fruit = fruits[i];
							M.quantity = quants[i];
							//add to N list
							insertN(M, cursorN.previousN, cursorN);
							//add to B list
							int bin = 0;
							Node cur = headB.nextB;
							if(cur.bin > 1)
								bin = 1;
	
							for(i = 1; i < size; i++) {
								cur = cur.nextB;
								if(cur.bin - cur.previousB.bin > 1)
									bin = cur.previousB.bin+1;
							}
							if(bin == 0) {
								//nextB is headB and we found no result already
								bin = highBin;
								highBin++;
								M.bin = bin;
								//will be the last element
								insertB(M, headB.previousB.previousB, headB);
							} else {
								M.bin = bin;
								insertB(M, cur.previousB, cur);
							}
							
						}
					}
				}
					
			}
		 } else {
			 //add all new bins
			 for(i = 0; i < len; i++) {
				 add(fruits[i], quants[i]);
			 }
		 }
		 ****/
			 if(size > 0) {
					//walk the lists
					Node cursorN = headN.nextN;
					Node cursorB = headB.nextB;
					for(i = 0; i < len; i++, cursorN = cursorN.nextN, cursorB = cursorB.nextB) {
						if(i >= size) {
							if(cursorN.fruit.compareTo(fruits[i]) >= 0) {
								if(cursorN.fruit.compareTo(fruits[i]) == 0) {
									//same fruit
									cursorN.quantity += quants[i];
								} else {
									//new type
									Node M = new Node();
									M.fruit = fruits[i];
									M.quantity = quants[i];
									//add to N list
									insertN(M, cursorN.previousN, cursorN);
									//add to B list
									int bin = 0;
									Node cur = headB.nextB;
									if(cur.bin > 1)
										bin = 1;
		
									for(i = 1; i < size; i++) {
										cur = cur.nextB;
										if(cur.bin - cur.previousB.bin > 1)
											bin = cur.previousB.bin+1;
									}
									if(bin == 0) {
										//nextB is headB and we found no result already
										bin = highBin;
										highBin++;
										M.bin = bin;
										//will be the last element
										insertB(M, headB.previousB.previousB, headB);
									} else {
										M.bin = bin;
										insertB(M, cur.previousB, cur);
									}
									
								}
							}
						} else {
							add(fruits[i], quants[i]);
						}
							
					}
				 } else {
					 //add all new bins
					 for(i = 0; i < len; i++) {
						 add(fruits[i], quants[i]);
					 }
				 }
		 
		 
	 }

	 
	 /**
	  * Remove a fruit from the inventory. 
	  *  
	  *     1. Search for the fruit on the N-list.  
	  *     2. If no existence, make no change. 
	  *     3. Otherwise, call the private method remove() on the node that stores 
	  *        the fruit to remove it. 
	  * 
	  * @param fruit
	  */
	 public void remove(String fruit)
	 {
		 Node res = findFruit(fruit);
		 if(res == null)
			 return;
		 
		 remove(res);
	 }
	 
	 
	 /**
	  * Remove all fruits stored in the bin.  Essentially, remove the node.  The steps are 
	  * as follows: 
	  * 
	  *     1. Search for the node with the bin in the B-list.
	  *     2. No change if it is not found.
	  *     3. Otherwise, call remove() on the found node. 
	  * 
	  * @param bin   
	  * @throws IllegalArgumentException  if bin < 1
	  */
	 public void remove(int bin) throws IllegalArgumentException
	 {
		 if(bin < 1)
			 throw new IllegalArgumentException();
		 
		 Node res = findBin(bin);
		 if(res == null)
			 return;
		 
		 remove(res);
		 
	 }
	 
	 
	 /**
	  * Sell n units of a fruit. 
	  * 
	  * Search the N-list for the fruit.  Return in the case no fruit is found.  Otherwise, 
	  * a Node node is located.  Perform the following: 
	  * 
	  *     1. if n >= node.quantity, call remove(node). 
	  *     2. else, decrease node.quantity by n. 
	  *     
	  * @param fruit
	  * @param n	
	  * @throws IllegalArgumentException  if n < 0
	  */
	 public void sell(String fruit, int n) throws IllegalArgumentException 
	 {
		 Node res = findFruit(fruit);
		 if(n >= res.quantity)
			 remove(res);
		 else
			 res.quantity -= n;
	 }
	 
	 
	 /** 
	  * Process an order for multiple fruits as follows.  
	  * 
	  *     1. Sort the ordered fruits and their quantities by fruit name using the private method 
	  *        quickSort(). 
	  *     2. Traverse the N-list. Whenever a node with the next needed fruit is encountered, 
	  *        let m be the ordered quantity of this fruit, and do the following: 
	  *        a) if m < 0, throw an IllegalArgumentException; 
	  *        b) if m == 0, ignore. 
	  *        c) if 0 < m < node.quantity, decrease node.quantity by n. 
	  *        d) if m >= node.quanity, call remove(node).
	  * 
	  * @param fruitFile
	  */
	 public void bulkSell(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	 {
		//read the file
		 String[] fruitsMax = new String[maxFruit];
		 int[] quantsMax = new int[maxFruit];
		 
		File file = new File(fruitFile);
		Scanner s = new Scanner(file);
		int i = 0, j = 0;
		while(s.hasNextLine()) {
			String line = s.nextLine();
			Scanner l = new Scanner(line);
			//System.out.println(line);
			String fruit = l.next();
			int quantity = l.nextInt();
			
			if(quantity < 0) {
				l.close();
				s.close();
				throw new IllegalArgumentException();
			}
			
			fruitsMax[i] = fruit;
			quantsMax[j] = quantity;
			i++;
			j++;
			
			l.close();
		}
		s.close();
		int len = i; // was i+1
		
		String[] fruits = new String[len];
		 Integer[] quants = new Integer[len];
		 
		 for(i = 0; i < len; i++) {
			 fruits[i] = fruitsMax[i];
			 quants[i] = quantsMax[i];
		 }
		 
		 
		 //quicksort
		 //sort the 2 arrays with Quicksort
		 quickSort(fruits, quants, len);
		 
		 //sell
		 /*
		   2. Traverse the N-list. Whenever a node with the next needed fruit is encountered, 
			  *        let m be the ordered quantity of this fruit, and do the following: 
			  *        a) if m < 0, throw an IllegalArgumentException; 
			  *        b) if m == 0, ignore. 
			  *        c) if 0 < m < node.quantity, decrease node.quantity by n. 
			  *        d) if m >= node.quanity, call remove(node).
		 */
		 Node cursorN = headN.nextN;
		 int h = 0;
		 for(i = 0; i < size; i++, cursorN = cursorN.nextN) {
			 if(fruits[h].compareTo(cursorN.fruit) == 0) {
				 //found a fruit
				 if(quants[h] < 0)
					 throw new IllegalArgumentException();
				 else if(quants[h] > 0 && quants[h] < cursorN.quantity)
					 cursorN.quantity -= quants[h];
				 else if(quants[h] >= cursorN.quantity)
					 remove(cursorN);
				 h++;
			 }
		 }
		 
		 
	 }
	 
	 
	 /**
	  * 
	  * @param fruit
	  * @return quantity of the fruit (zero if not on stock)
	  */
     public int inquire(String fruit)
     {
    	 Node res = findFruit(fruit);
    	 return res.quantity;   	 
      }
     
	 
	 /**
	  * Output a string that gets printed out as the inventory of fruits by names.  
	  * The exact format is given in Section 5.1.  Here is a sample:   
	  *
	  *  
	  * fruit   	quantity    bin
	  * ---------------------------
	  * apple  			50  	5
	  * banana    		20      9
	  * grape     		100     8
	  * pear      		40      3 
	 */
	 public String printInventoryN()
	 {	 
		 String str = String.format("%-16s%-16s%-16s\n", "fruit", "quantity", "bin");
		 str += "----------------------------------\n";
		 
		 Node cursor = headN;
		 int i;
		 for(i = 0; i < size; i++) {
			 str += String.format("%-16s%-16s%-16s\n", cursor.nextN.fruit, cursor.nextN.quantity, cursor.nextN.bin);
			 cursor = cursor.nextN;
		 }
		 
		 return str; 
	 }
	 
	 /**
	  * Output a string that gets printed out as the inventory of fruits by storage bin. 
	  * Use the same formatting rules for printInventoryN().  Below is a sample:  
	  * 
	  * bin 	fruit     	quantity      
	  * ----------------------------
	  * 3		pear      	40             
	  * 5		apple     	50            
	  * 8		grape     	100           
	  * 9		banana    	20  
	  *           	 
	  */
	 public String printInventoryB()
	 {
		 String str = String.format("%-16s%-16s%-16s\n", "fruit", "quantity", "bin");
		 str += "----------------------------------\n";
		 
		 Node cursor = headB;
		 int i;
		 for(i = 0; i < size; i++) {
			 str += String.format("%-16s%-16s%-16s\n", cursor.nextB.fruit, cursor.nextB.quantity, cursor.nextB.bin);
			 cursor = cursor.nextB;
		 }
		 
		 return str; 
	 }
	 
	 
	 @Override
	 /**
	  *  The returned string should be printed out according to the format in Section 5.1, 
	  *  exactly the same required for printInventoryN(). 
	  */
	 public String toString()
	 {
		 
		 return printInventoryN(); 
	 }
	 
	 
	 /**
	  *  Relocate fruits to consecutive bins starting at bin 1.  Need only operate on the
	  *  B-list. 
	  */
	 // 
	 public void compactStorage()
	 {
		//sort by bin? this function is poorly defined or explained
		 Node cursor = headB.nextB;
		 int i, hBin = 1;
		 for(i = 0; i < size; i++) {
			 cursor.bin = i+1;
			 hBin = cursor.bin;
			 cursor = cursor.nextB;
		 }
		 highBin = hBin;
	 }
	 
	 
	 /**
	  *  Remove all nodes storing fruits from the N-list and the B-list.
	  */
	 public void clearStorage()
	 {
		 headN.nextN = headN;
		 headN.previousN = headN;
		 headB.nextB = headB;
		 headB.previousB = headB;
		 size = 0;
	 }
	 
	 
	 /** 
	  * Split the list into two doubly-sorted lists DST1 and DST2.  Let N be the total number of 
	  * fruit types. Then DST1 will contain the first N/2 types fruits in the alphabetical order.  
	  * DST2 will contain the remaining fruit types.  The algorithm works as follows. 
	  * 
	  *     1. Traverse the N-list to find the median of the fruits by name. 
	  *     2. Split at the median into two lists: DST1 and DST2.  
	  *     3. Traverse the B-list.  For every node encountered add it to the B-list of DST1 or 
	  *        DST2 by comparing node.fruit with the name of the median fruit. 
	  *   
	  * DST1 and DST2 must reuse the nodes from this list. You cannot make a copy of each node 
	  * from this list, and arrange these copy nodes into DST1 and DST2. 
	  * 
	  * @return   two doubly-sorted lists DST1 and DST2 as a pair. 
	  */
	 public Pair<DoublySortedList> split()
	 {
		 DoublySortedList a = new DoublySortedList();
		 DoublySortedList b = new DoublySortedList();
		 
		 int median = size/2-1;
		 //7/2 = 3 (split of 3 | 4)
		 //up to and including the median
		 Node M = new Node();
		 
		 //populate N lists
		 int i;
		 Node cursor = headN.nextN;
		 Node cursorT = cursor;
		 for(i = 0; i < size; i++, cursor = cursorT) {
			 cursorT = cursorT.nextN;
			 if(i <= median) {
			 	//a.add(cursor.fruit, cursor.quantity);
				// addN(a, cursor);
				 insertN(cursor, a.headN.previousN, a.headN);
			//	 System.out.println("adding to a");
				 a.size++;
			 } else {
				//b.add(cursor.fruit, cursor.quantity);
				 //addN(b, cursor);
				 insertN(cursor, b.headN.previousN, b.headN);
				// System.out.println("adding to b");
				 b.size++;
			 }
			 if(i == median)
				 M = cursorT.previousN;
			 //System.out.println(i);
			 //System.out.println(cursorT.fruit);
			// System.out.println(a.toString());
			// System.out.println(cursor.fruit);
		 }
		 		 
		 //System.out.println(median +  " "+ M.fruit);
		 
		 insertionSort(a);
		 insertionSort(b);
		 
		 cursor = headB.nextB;
		 cursorT = cursor;
		 for(i = 0; i < size; i++, cursor = cursorT.previousB) {
			 cursorT = cursorT.nextB;
			 if(cursor.fruit.compareTo(M.fruit) <= 0) {
				 //add to a
				 //addB(a, cursor);
				 insertB(cursor, a.headB.previousB, a.headB);
			 } else {
				 //add to b
				 //addB(b, cursor);
				 insertB(cursor, b.headB.previousB, b.headB);
			 }
		 }
		 
		 size = 0;
		 headN.nextN = headN;
		 headN.previousN = headN;
		 headB.nextB = headB;
		 headB.previousB = headB;
		 highBin = 1;
		 return new Pair<DoublySortedList>(a, b); 
	 }
	 
	 
	 /**
	  * Perform insertion sort on the doubly linked list with head node using a comparator 
	  * object, which is of either the NameComparator or the Bincomparator class. 
	  * 
	  * Made a public method for testing by TA. 
	  * 
	  * @param sortNList   sort the N-list if true and the B-list otherwise. 
	  * @param comp
	  */
	 public void insertionSort(boolean sortNList, Comparator<Node> comp)
	 {
		 if(sortNList) {
			 //N
			 
			 Node cursorI = headN.nextN.nextN;
			 Node cursorH = new Node();
			 Node nextI = new Node();
			 
			 int i;
			 for(i = 1; i < size; i++) {
				// Point tmp = points[i];
				 int h = i;
				cursorH = cursorI;
				nextI = cursorI.nextN;
				 
				//System.out.println("i: " + i);
				 while(h > 0 && comp.compare(cursorH.previousN, cursorI)  > 0) {
					 //points[h] = points[h-1];					 
					//System.out.println("h: " + h );
					cursorH = cursorH.previousN;
					 h--;
				 }
				 if(h != i) {
					 //points[h] = tmp;
					 
					 //cursorH = cursorH.nextN;
					 
					 //remove I
					 cursorI.previousN.nextN = cursorI.nextN;
					 cursorI.nextN.previousN = cursorI.previousN;
					
					 //insert I where it belongs before H
					 insertN(cursorI, cursorH.previousN, cursorH);					 
				 }
				// cursorI = cursorI.nextN;
				 cursorI  = nextI;
			 }
			// System.out.println(headN.previousN.fruit);
		 } else {
			 //B
			 
			 Node cursorI = headB.nextB.nextB;
			 Node cursorH = new Node();
			 Node nextI = new Node();
			 
			 int i;
			 for(i = 1; i < size; i++) {
				// Point tmp = points[i];
				 int h = i;
				cursorH = cursorI;
				nextI = cursorI.nextB;
				 
				//System.out.println("i: " + i);
				 while(h > 0 && comp.compare(cursorH.previousB, cursorI)  > 0) {
					 //points[h] = points[h-1];					 
					//System.out.println("h: " + h );
					cursorH = cursorH.previousB;
					 h--;
				 }
				 if(h != i) {
					 //points[h] = tmp;
					 
					 //cursorH = cursorH.nextB;
					 
					 //remove I
					 cursorI.previousB.nextB = cursorI.nextB;
					 cursorI.nextB.previousB = cursorI.previousB;
					
					 //insert I where it belongs before H
					 insertB(cursorI, cursorH.previousB, cursorH);					 
				 }
				// cursorI = cursorI.nextB;
				 cursorI  = nextI;
			 }
			// System.out.println(headB.previousB.fruit);
			 
		 }
	 }
	 

	 /**
	  * Sort an array of fruit names using sort.  After sorting, quant[i] is the 
	  * quantity of the fruit with name[i].  
	  * 
	  * Made a public method for testing by TA. 
	  * 
	  * @param size		number of fruit names 
	  * @param fruit   	array of fruit names 
	  * @param quant	array of fruit quantities 
	  */
	 public void quickSort(String fruit[], Integer quant[], int size)
	 {
		 quickSortRec(fruit, quant, 0, size-1);
	 }
	 
	 
	 // --------------
	 // helper methods 
	 // --------------
	 
	 private void quickSortRec(String fruit[], Integer quant[], int first, int last) {
		if(first >= last) return;
		int p = partition(fruit, quant, first, last);
		quickSortRec(fruit, quant, first, p - 1);
		quickSortRec(fruit, quant, p + 1, last);
	 }
	 
	 /**
	  * Add a node between two nodes prev and next in the N-list.   
	  * 
	  * @param node
	  * @param prev  preceding node after addition 
	  * @param next  succeeding node 
	  */
	 private void insertN(Node node, Node prev, Node next)
	 {
		 prev.nextN = node;
		 next.previousN = node;
		 node.nextN = next;
		 node.previousN = prev;
	 }
	
	 
	 /**
	  * Add a node between two nodes prev and next in the B-list.  
	  * 
	  * @param node
	  * @param prev  preceding node 
	  * @param next  succeeding node 
	  */
	 private void insertB(Node node, Node prev, Node next)
	 {	 
		 prev.nextB = node;
		 next.previousB = node;
		 node.nextB = next;
		 node.previousB = prev;
	 }
	 
	 
	 /**
	  * Remove node from both linked lists. 
	  * 
	  * @param node
	  */
	 private void remove(Node node)
	 {
		 Node resN = findFruit(node.fruit);
		 Node resB = findBin(node.bin);
		 resN.previousN.nextN = resN.nextN;
		 resB.previousB.nextB = resB.nextB;
		 resN.nextN.previousN = resN.previousN;
		 resB.nextB.previousB = resB.previousB;
		 size--;
	 }
	  
	 
	 /**
	  * 
	  * @param fruit    array of fruit names 
	  * @param quant	array of fruit quantities corresponding to fruit[]
	  * @param first
	  * @param last
	  * @return
	  */
	 private int partition(String fruit[], Integer quant[], int first, int last)
	 {
		 	int pivot = last;
			int i = first - 1;
			
			int j;
			for(j = first; j < last; j++) {
				if(fruit[j].compareTo(fruit[pivot]) <= 0) {
					i++;
					swap(i, j, fruit, quant);
				}
				
			}
			swap(i+1, last, fruit, quant);
			
			return i + 1;
	 }
	 private void swap(int a, int b, String fruit[], Integer quant[]) {
		 String tf = fruit[a];
		 int tq = quant[a];
		 fruit[a] = fruit[b];
		 quant[a] = quant[b];
		 fruit[b] = tf;
		 quant[b] = tq;
	 }
	 
	 //added
	 /**
	  *  Searches for a matching node by criteria
	  * @param fruit name of fruit to find
	  * @return the node that matches fruit ;; null if none found
	  */
	 private Node findFruit(String fruit) {
		 Node cursor = headN.nextN;
		 int i;
		 for(i = 0; i < size; i++) {
			 if(cursor.fruit.equals(fruit)) {
				 return cursor;
		 	}
			cursor = cursor.nextN;
		 }
		 return null;
	 }
	 
	 /**
	  *  Searches for a matching node by criteria
	  * @param bin number to find
	  * @return the node that matches bin ;; null if none found
	  */
	 private Node findBin(int bin) {
		 Node cursor = headB.nextB;
		 int i;
		 for(i = 0; i < size; i++) {
			 if(cursor.bin == bin) {
				 return cursor;
		 	}
			cursor = cursor.nextB;
		 }
		 return null;
	 }
	 
	 
	 /***
	  * insertion sorts a dsl's n list
	  * @param dsl input list
	  */
	 private void insertionSort(DoublySortedList dsl) {
		Node dslHeadN = dsl.headN;
		NameComparator comp = new NameComparator();
		
		 //N
		 
		 Node cursorI = dslHeadN.nextN.nextN;
		 Node cursorH = new Node();
		 Node nextI = new Node();
		 
		 int i;
		 for(i = 1; i < dsl.size; i++) {
			// Point tmp = points[i];
			 int h = i;
			cursorH = cursorI;
			nextI = cursorI.nextN;
			 
			//System.out.println("i: " + i);
			 while(h > 0 && comp.compare(cursorH.previousN, cursorI)  > 0) {
				 //points[h] = points[h-1];					 
				//System.out.println("h: " + h );
				cursorH = cursorH.previousN;
				 h--;
			 }
			 if(h != i) {
				 //points[h] = tmp;
				 
				 //cursorH = cursorH.nextN;
				 
				 //remove I
				 cursorI.previousN.nextN = cursorI.nextN;
				 cursorI.nextN.previousN = cursorI.previousN;
				
				 //insert I where it belongs before H
				 insertN(cursorI, cursorH.previousN, cursorH);					 
			 }
			// cursorI = cursorI.nextN;
			 cursorI  = nextI;
		 }
		// System.out.println(dslHeadN.previousN.fruit);
	 }
	 
}
