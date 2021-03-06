package edu.iastate.cs228.hw3;

import java.io.FileNotFoundException;

/**
 * 
 * @author Sean Hinchee
 *
 */

public class DSLTest 
{
	public static void main(String args[]) throws FileNotFoundException
	{
		// Conduct your own JUnit tests in a class, say, HW3Test.
		DoublySortedList dsl = new DoublySortedList();
		System.out.println(dsl.toString());
		dsl.add("apple", 5);
		System.out.println(dsl.toString());
		dsl.add("pear", 3);
		System.out.println(dsl.toString());
		dsl.remove("apple");
		System.out.println(dsl.toString());
		dsl.compactStorage();
		System.out.println(dsl.toString());
		dsl.remove(1);
		System.out.println(dsl.toString());
		dsl.add("potato", 2);
		dsl.add("ryberry", 7);
		System.out.println(dsl.toString());
		System.out.println(dsl.inquire("ryberry"));
		dsl.sell("ryberry", 3);
		System.out.println(dsl.toString());
		
		//second test battery
		DoublySortedList dsl2 = new DoublySortedList("test.txt.txt");
		System.out.println(dsl2.toString());
		System.out.println(dsl2.printInventoryB());
		
		//insertion sort
		/*
		dsl.insertionSort(true, new NameComparator());
		System.out.println(dsl.toString());
		*/
		dsl.add("katieberry", 12);
		dsl.add("seanberry", 69);
		dsl.add("bberry", 51);
		
		//split
		Pair<DoublySortedList> p = dsl2.split();
		System.out.println(p.getFirst().toString());
		System.out.println(p.getSecond().toString());
		
		
		//restock
		dsl2.restock("restock.txt");
		System.out.println(dsl2.toString());
		dsl2.restock("restock.txt");
		System.out.println(dsl2.toString());
		dsl2.bulkSell("restock.txt");
		System.out.println(dsl2.toString());

	}
}
