package insect;

public abstract class Grasshopper extends Insect implements Locomotion 
{	
	public Grasshopper(int size, String color)
	{
		super(size, color); 
	}

	@Override
	public void move()
	{
		System.out.println("hop"); 
	}
	
	@Override 
	public void attack()
	{
		System.out.println("bite"); 
	}

	public abstract String antennae();
}
