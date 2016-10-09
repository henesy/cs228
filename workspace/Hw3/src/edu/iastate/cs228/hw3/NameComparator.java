package edu.iastate.cs228.hw3;

import java.util.Comparator;

public class NameComparator implements Comparator<Node>
{
	public int compare(Node n1, Node n2)
	{
		return n1.fruit.compareTo(n2.fruit);  
	}
}
