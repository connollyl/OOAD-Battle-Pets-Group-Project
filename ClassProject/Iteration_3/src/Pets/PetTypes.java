package Pets;
/**
 * This enum hold the pet types and tells how to output them
 */
 public enum PetTypes 
{
	POWER,
	SPEED,
	INTELLIGENCE;
	
	 /**
	  * Returns the type of the pet as a string
	  * @Return petType
	  */
	public String toString() 
	{
		String petType = name().toLowerCase();
		String fristLetter = petType.substring(0,1).toUpperCase();
		petType = fristLetter + petType.substring(1,petType.length());
		return petType;
	}
}