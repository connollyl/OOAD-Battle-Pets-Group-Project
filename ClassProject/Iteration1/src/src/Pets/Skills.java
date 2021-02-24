package Pets;

import java.util.ArrayList;
/**
 * Contains a list of available skills used by pets. Has a toString function
 * that allows the skills to be read in a nice format.
 * @author garzal
 *
 */
public enum Skills
{
	ROCK_THROW, 
	SCISSORS_POKE, 
	PAPER_CUT, 
	SHOOT_THE_MOON, 
	REVERSAL_OF_FORTUNE;
	

	@Override
	public String toString() {
	String skillName = "";
	String[] nameArr = name().toLowerCase().split("_");
	for(int i = 0; i < nameArr.length;i++) {
		String formatName = nameArr[i].substring(0,1).toUpperCase();
		formatName += nameArr[i].substring(1,nameArr[i].length());
		if(i != nameArr.length) {
			skillName += formatName + " ";
		}else {
			skillName += formatName;
		}
	}
	return skillName;
	}
} 
