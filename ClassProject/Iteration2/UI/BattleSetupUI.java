package UI;

import java.io.*;
import java.util.*;

/**
 * This class handles all UI display functions involved in 
 * setting up the current battle and the current battles settings.
 * @author Logan Garza, Eric Weber
 *
 */
public class BattleSetupUI extends genericUI 
{
    //private Properties bsuiOutputStrings;
    private Display disp;

       /**
     * Constructor for battleSetupUI, it retrieves the file 
     * location of the properties (in most cases a string) for 
     * the UI used during battle setup.
     */
    public BattleSetupUI() 
    { 
        PROPERTY_FILE_NAME = "BattleSetupUI";
        constStrings = getContStrings(PROPERTY_FILE_NAME);
        disp = Display.getDisplay();
    }
    

   /**
     * Displays welcome text, and prompts user for number of 
     * fights for the battle.
     * @return - the number of fights the user wants participate 
     * in.
     */
    public int setupGame() 
    {
        disp.clearScreen();
        disp.println(prop("setupGame"));
        return disp.readPositiveInt();
    }

    /**
     * Generates and displays a summary of a pet's name and type
     * @param name - String representation of pet's name.
     * @param type - String representation of pet's type.
     */
    public void displayPetInfo(String name, String type) 
    {
            disp.clearScreen();
            disp.println(prop("petInfoTitle"));
            disp.println(prop("petInfoName") + name);
            disp.println(prop("petInfoType") + type);
    }
    /**
     * Prompts the user for the max Hp they want for all
     * pets in the battle.
     * @return double value used for player max HP value.
     */
    public double settingsMaxHp() 
    {
            disp.println(prop("maxHp"));
            return disp.readPositiveInt();
    }
      /**
     * Prompts the user for the number of pets participating
     * in the battle.
     * @return chosen integer representation of the number of pets
     * participating the battle
     */
    public int promptNumPets() {
            disp.println(prop("numPets"));
            return disp.readBetween(2, Integer.MAX_VALUE-1, Display.INCLUSIVITY.INCLUSIVE);
    }
     /**
     * Prompts current player for their pet name, and returns 
     * that string representation.
     * @param index of the current pet being prompted
     * @return the text used in prompting the 
     * @throws IOException if string is not collected properly
     */
    public String promptPetName(int index) throws IOException 
    {
            disp.println("Player " + index + prop("petName"));
            return disp.readString();
    }
    /**
     * Prompts current player for the type of their pet.
     * @return the text used in UI
     * @throws InputMismatchException if user input is not string
     * @throws IOException if input is gather improperly.
     */
    public String promptPetType() throws InputMismatchException, IOException
    {
      disp.println(getPetTypeDisplay());
      String type = disp.readString();

      if(!type.equalsIgnoreCase(prop("petTypePower")) 
              && !type.equalsIgnoreCase(prop("petTypeSpeed")) 
              && !type.equalsIgnoreCase(prop("petTypeIntel"))
        )
      {
          disp.println(prop("validPetType"));
          return promptPetType();
      }
      else
      {
         disp.clearScreen();
          return type;
      }
    }
    /**
     * Prompts the user for seed used in random number generator,
     * and returns that value in in the form of a Long.
     * @return the long representation for the long entered\
     * by the player
     */
    public long promptSeed()
    {
      disp.clearScreen();
    	disp.println(prop("seedPrompt"));
    	Long l = disp.readLong();    	
      disp.clearScreen();
      return l;
    }
    /**
     * Retrieves pet types from battesetupUI.properties
     * and returns them as a string value to be displayed.
     * @return string representation of pet types.
     */
    private String getPetTypeDisplay()
    {
        StringBuilder output = new StringBuilder();
        output.append(prop("selectPetType"));
        output.append("\n");
        output.append(prop("petType1Text"));
        output.append("\n");
        output.append(prop("petType2Text"));
        output.append("\n");
        output.append(prop("petType3Text"));
        
        return output.toString();
    }
    
    /**
     * TESTBED MAIN
     * @param args
     */
    public static void main(String[] args)
    {
        
    }
}
