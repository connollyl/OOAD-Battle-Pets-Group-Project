package Game;
import java.util.*;

/**
 * Generates random numbers with a seed input.
 * Code referenced from :
 * https://www.geneseo.edu/~baldwin/reference/random.html
 * Author: Logan Garza
 */
public class RNG 
{
	public final int UPPER_BOUND = 5;
	Random generator;
	/**
	 * Creates a RNG using a given seed
	 * @param seed
	 */
	public RNG(long seed) 
	{
		generator = new Random(seed);
	}
	
	/**
	 * Returns a random value between 0 and 5
	 * @return double
	 */
	public double getRandomValue()
	{
		return generator.nextDouble() * UPPER_BOUND;
	}
	
	/*
	public static void main(String args[])
	{
		RNG rng = new RNG(4555);
		int index = 10;
		while(index != 0)
		{
			System.out.println(rng.getRandomValue());
			index--;
		}
	}
	*/
}
