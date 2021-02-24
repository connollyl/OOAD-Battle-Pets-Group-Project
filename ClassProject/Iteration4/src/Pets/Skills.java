package Pets;

/**
 * This enum holds the name of the possible skills a pet can use.
 */
public enum Skills {
	ROCK_THROW, 
	SCISSORS_POKE, 
	PAPER_CUT, 
	SHOOT_THE_MOON, 
	REVERSAL_OF_FORTUNE;
	
	
	
	
	/**
	 * Returns a Skills array
	 * @return
	 */
	public static Skills[] getNumSkills(){
		return Skills.values();
	}
	
	/**
	 * Returns the name of the skill as a string
	 * @return skillName
	 */
	@Override
	public String toString() 
	{
		
		String skillName = "";
		String[] nameArr = name().toLowerCase().split("_",0);
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
