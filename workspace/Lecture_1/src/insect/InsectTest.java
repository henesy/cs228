package insect;

public class InsectTest 
{

	public static void main(String[] args) 
	{
		// hop
/*		Locomotion b = new Locust(2, "Black"); 
		b.move(); 
	*/	
		
		// compile error: Cannot instantiate the type Insect
// 		Insect i = new Insect(3, "Green"); 
		
		// compile error: getSwarm() undefined for Insect
/*		Insect b = new Bee(1, "Golden-Black", "Lake"); 
		System.out.println(b.getColor()); 
		System.out.println(b.getSwarm());
*/		 
		
		// crawl 
		// Brown 
/*	    Mantis m = new Mantis(5, "Green"); 
	    m.move(); 
	    Insect i = m.preyOn(); 
	    System.out.println(i.getColor()); 
	*/
		
		// ClassCastException
/*		Grasshopper g = new Locust(3, "Red"); 
		Katydid k = (Katydid) g; 
	*/
		
		// bite
		// Short
/*		Grasshopper g = new Katydid(2, "Green"); 
		g.attack(); 
		g = new Locust(3, "Black"); 
		System.out.println(((Locust) g).antennae()); 
 		Locomotion b = g; 
	*/	
		
		// compile error: Cannot cast from Locust to Katydid
/*		Insect k = new Katydid(2, "Green"); 
		Grasshopper g = (Katydid) k; 
		Locust l = (Katydid) k; 
*/		
		
		// crawl 
		// bite
		// Orange Blossom
/*		Insect i = new Mantis(4, "Yellow"); 
		((Mantis) i).move(); 
		((Mantis) i).preyOn().attack(); 
		i = new Bee(1, "Golden-Black", "Hill"); 
		((Bee) i).makeHoney(); 
*/		

		Bee b1 = new Bee(1, "Golden-Black", "Hill"); 

		// testing overriden equals() 
		Bee b2 = new Bee(1, "Golden-Black", "Hill"); 
		Bee b3 = new Bee(2, "Golden-Black", "Hill"); 
		Bee b4 = new Bee(1, "Yellow", "Hill"); 
		Bee b5 = new Bee(1, "Golden-Black", "Lake"); 
		Bee b6 = new Bee(2, null, null); 
		Bee b7 = new Bee(3, "Golden-Black", null); 
		Bee b8 = new Bee(3, "Golden-Black", null); 
		Bee b9 = new Bee(2, null, null); 
		Bee b10 = new Bee(2, null, "Hill"); 
		Bee b11 = new Bee(2, null, "Hill"); 

		
		
		System.out.println(b8.equals(b7)); 
		System.out.println(b6.equals(b9)); 
		System.out.println(b10.equals(b11)); 
		System.out.println(b1.equals(b6)); 
		System.out.println(b7.equals(b10)); 
		System.out.println(b1.equals(b7)); 
		System.out.println(b1.equals(b11)); 

		/*
		System.out.println(b1.equals(b2)); 
		System.out.println(b1.equals(b3)); 
		System.out.println(b1.equals(b4)); 
		System.out.println(b1.equals(b5)); 
		*/

		// testing overriden clone()
		// uncomment the following in Bee.java
		//     - ,Cloneable in the header
		//     - the overriden clone() method 
/*		Bee b6 = (Bee) (b1.clone()); 
		System.out.println(b1.equals(b6)); 
*/		

/*		// testing comparator class 
		Mantis m = new Mantis(3, "Green"); 
		Locust l = new Locust(2, "Black"); 
		Bee b = new Bee(1, "Yellow", "Hill"); 
		InsectComparator comp = new InsectComparator();   
		System.out.println(comp.compare(b, l));
*/		
	}
}
