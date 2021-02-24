package UI;
import java.util.*;
import Pets.Pet;
import Pets.Skills;
import Pets.PetTypes;
import UI.Display.INCLUSIVITY;

/**
 * This class deals with the user interface portion of the 
 * Rounds.
 * Author:  Logan Garza & Eric Weber
 */

public class RoundUI extends genericUI 
{
    private Display disp;

    /**
     * Constructor for the ui.
     */
	public RoundUI() 
	{
            PROPERTY_FILE_NAME = "RoundUI";
            constStrings = getContStrings(PROPERTY_FILE_NAME);
            disp = Display.getDisplay();
	}

	/**
	 * Displays the round and number of it.
	 * @param roundNumber
	 */
   public void showInitialRound(int roundNumber)
   {
       disp.println(prop("roundNumber") + roundNumber);

   }
        
   /**
    * Prompts for and obtains the skills for the pets.
    * @param currentPet
    */
	public void skillSelect(Pet currentPet) 
	{
		displayPetInfo(currentPet.getName(), currentPet.getType());
        Skills[] skillList = Skills.getNumSkills();
		disp.println(prop("chooseSkill"));
		int chosenSkill = disp.readBetween(1, skillList.length, INCLUSIVITY.INCLUSIVE);
		if(currentPet.checkRecharge(skillList[chosenSkill - 1]))
		{
			while(currentPet.checkRecharge(skillList[chosenSkill - 1]))
			{
				disp.println(skillList[chosenSkill - 1].toString() + prop("cooldown") + 
						currentPet.getCooldown(skillList[chosenSkill - 1]));
				chosenSkill = disp.readBetween(1, skillList.length, INCLUSIVITY.INCLUSIVE);
			} 
		}
		disp.println(prop("skillConfirmation") + skillList[chosenSkill - 1].toString());
		currentPet.setCoolDown(skillList[chosenSkill - 1]);
		if(skillList[chosenSkill - 1] == Skills.SHOOT_THE_MOON)
			promptPredictSkill(currentPet);
	}

	/**
	 * Displays the pet info.
	 * @param name
	 * @param type
	 */
	public void displayPetInfo(String name, PetTypes type) 
	{
		disp.println("Pet: " + name + " Of Type: " + type.toString());
	}
      
	/**
	 * Displays damage and HP.
	 * @param pets
	 */
   public void displayDamageAndHP(List<Pet> pets)
   {
    	for(int i = 0; i < pets.size(); i++)
      {
    		disp.println(pets.get(i).getName() + " took " + 
               pets.get(i).getFullDamageTaken() + " and has " 
               + pets.get(i).getHP() + prop("healthLeft"));
      }
   }
    
   /**
    * Prompts the prediction that is done for shoot the moon.
    * @param p
    */
   public void promptPredictSkill(Pet p) 
   {
      Skills[] skillList = Skills.getNumSkills();
      disp.println(prop("predictSkill"));
      int chosenSkill = disp.readBetween(1, skillList.length, INCLUSIVITY.INCLUSIVE);
      disp.println(prop("skillPredictConfirm") + skillList[chosenSkill - 1].toString());
      p.setGuessedSkill(skillList[chosenSkill - 1]);
   }

   /*public static void main(String[] args)
   {

   }*/
}
