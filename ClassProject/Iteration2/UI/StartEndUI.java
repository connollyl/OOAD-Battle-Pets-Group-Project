package UI;

import java.util.*;
import Pets.Pet;

/**
 * StartEndUI handles display and input operations for the start and end 
 * scenarios of the game
 * 
 * @Author Eric Weber
 * @Version 2.1
 */
public class StartEndUI extends genericUI 
{
    //private Properties constStrings;
    private Display disp;

    /**
     * Constructor for the startend display obeject. It opens the
     * properties file for the startendUI class.
     */
    public StartEndUI()
    {
        PROPERTY_FILE_NAME = "StartEndUI";
        constStrings = getContStrings(PROPERTY_FILE_NAME);
        disp = Display.getDisplay();
    }
    
    /**
     * Clears screen and displays start of current fight information.
     * @param currentFightNum is current fight.
     */
    public void showInitFight(int currentFightNum) 
    {
      disp.blankln(2);
      disp.println(prop("startFight") + (currentFightNum) + "!");
    }
    
    /**
     * Displays the results for given fight for given pet.
     * @param pet is the winning pet
     */
    public void printWinFightResults(Pet pet) 
    {
            
            disp.println(pet.getName() + prop("winFight"));
            disp.println(pet.getName() + prop("totalWon1") + pet.getFightsWon() + prop("totalWon2"));
    }

    /**
     * Prints the results if fight is tied, and displays the list of tied pets.
     * @param plist is list of tied pets for given pets.
     */
    public void printTieFightResults(List<Pet> plist) 
    {
      disp.println(prop("fightTie"));
      for (int i = 0; i < plist.size(); i++) {
              plist.get(i).incrementFightsWon();
              disp.println(plist.get(i).getName() + prop("totalWon1") + plist.get(i).getFightsWon() + prop("totalWon2"));
      }
    }
    
    /**
     * Prints results of tied battle.
     * @param petList is tied pets for battle. 
     */
    public void printTieBattleResults(List<Pet> petList) 
    {
      disp.println(prop("battleTie"));
      for (int i = 0; i < petList.size(); i++) {
              disp.println(petList.get(i).getName() + " has " + petList.get(i).getFightsWon() + prop("totalWins"));
      }
    }
    
    /**
     * Displays damage numbers for each round.
     * @param petName is pet that took damage.
     * @param damageTaken is number representation of damage taken.
     * @param healthRemaining is number representation of health remaing for given pet.
     */
    public void displayDamageTaken(String petName, double damageTaken, double healthRemaining) 
    {
            disp.println(petName + " recieved " + damageTaken + " damage.");
            disp.println(petName + " has " + healthRemaining + " remaining.");
            disp.blankln(2);            
    }

    /**
     * Display text for when pet goes to sleep.
     * @param petName is name of pet that went to sleep.
     */
    public void petWentToSleep(String petName) 
    {
            disp.println(prop("sleepyPet1") + petName + prop("sleepyPet2"));
            disp.blankln();
            disp.println(petName + prop("sleepyPet3"));
            disp.blankln();
    }

    /**
     * Displays the result for given battle.
     * @param petList is list of pets.
     * @param p is winner.
     */
    public void printWinBattleResults(List<Pet> petList, Pet p) 
    {
      for (int i = 0; i < petList.size(); i++) {
              disp.println(petList.get(i).getName() + " has " + petList.get(i).getFightsWon() + prop("totalWins"));
      }
      disp.println(prop("endBattle") + " " + p.getName() + prop("fameAndGlory"));
            
    }

    /**
     * Prompt user for the start of another new battle.
     * @return if the player wants to initiate another battle.
     */
    public boolean promptAnotherBattle() 
    {
        disp.println(prop("playAgain"));

        boolean playAgain = disp.readBoolean();     
        
        if(!playAgain)
        {
            disp.clearScreen();
            disp.println(prop("thx4plyn"));
        }
        disp.clearScreen();
        return playAgain;
    }
    
    
   //---------------------------------------------------------------------
    /*public static void main(String[] args)
    {
             
    }*/
}
