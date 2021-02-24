package GUI_Components;
import java.util.*;

/*
 * This class deals with initializing the unentered 
 * names in the pet creation stage
 * Author: Aidan Gengler, Andrew Cummings, Logan Garza
 */

public class RNGNames 
{
	private static String names[] = {"Aidan", "Andrew", "Logan", "Eric", "Doug", "Jessy James", "James Cameron", "Chuck Norris", "Tim", 
			"Robin Williams", "Zack", "Gilbert Gotfried", "Dwayne Johnson", "Snookie", "Michelle", "Michael", "A-A-Ron", "Ti-Moth-ee",
			"Teflon Don", "John Gotti", "Jeffrey Dahmer", "Walter Ellis", "David SpanBauer", "Ed Gein", "Jack The Tripper",
			"Bubbles", "Saad Maan", "Chris P. Bacon", "Jay-Quel-in", "Fluffy", "Rocky Balboa", "Where am I", "O-Shaq-Hen-A-Sea", 
			"Optimus Prime", "Jeff Foxworthy", "Larry the Guy", "Guy Fieri", "John Gotti", "Charles Manson", "Bradford Bishop", 
			"Eduardo Rivera", "Jason Derek Brown", "Ayman Al-Zawahiri", "Jaber A. Elbaneh", "Abdullah Ahmed", "Joanne Chesimard",
			"Daniel Andreas", "Sun Kailiang", "Huang Zhenyu", "Bjorn Sundin", "Donald Trump", "Ronald Dump", "Hillary Clinton",
			"Billary Pinton", "Tom Cruise", "Kevin Hart", "Johnny Depp", "Jim Carrey", "Mr. Garvey", "Bah-Lock-Ay", "De-Nice", 
			"Pree-Zent"};
	
	private static Random randNameIndex = new Random();
	
	/**
	 * Returns a random name from the array.
	 * @return
	 */
	public String getRandomName()
	{
		return names[randNameIndex.nextInt(names.length - 1)];
	}
	
	/**
	 * Tesbed Main for this class.
	 * @param args
	 */
	public static void main(String[] args)
	{
		for(int i = 0; i < 200; i++)
		{
			System.out.println(names[randNameIndex.nextInt(names.length - 1)]);
		}
	}
}
