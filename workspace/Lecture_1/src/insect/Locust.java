package insect;

public class Locust extends Grasshopper
{
	public Locust(int size, String color)
	{
		super(size, color); 
	}

	@Override
	public String antennae()
	{
		return "Short"; 
	}
}
