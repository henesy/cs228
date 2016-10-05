package insect;

public class Mantis extends Insect implements Locomotion, Pollination
{
	public Mantis(int size, String color)
	{
		super(size, color); 
	}
	
	@Override
	public void move()
	{
		System.out.println("crawl"); 
	}
	
	@Override 
	public boolean pollinate()
	{
		return false; 
	}

	@Override 
	public void attack()
	{
		System.out.println("strike"); 
	}
		
	public Grasshopper preyOn()
	{
		return new Locust(3, "Brown"); 
	}
}
