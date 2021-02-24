package UI;
import java.util.*;

import Pets.Pet;
import Pets.Skills;

/**
 * Console UI handles all console input and output operations for the game.
 * @Author Eric Weber, with help from Logan Garza
 * @Version 0.5
 */
public class ConsoleUI 
{
   private Buffer screen;
   private InputReader reader;
   
   /**
    * Default constructor for ConsoleUI, uses the <code>System.out</code> 
    * and <code>System.in</code> output and input streams respectively.
    */
   public ConsoleUI()
   {
      screen = new Buffer(System.out);
      reader = new InputReader(System.in);
   }
 
   /**
    * Default constructor for ConsoleUI, uses the <code>System.out</code> 
    * and <code>System.in</code> output and input streams respectively.
    * @param text - single line of text to display.
    */
   public void show(String text)
   {
      screen.add(text);
      writeScreen();
   }
   
   /**
    * Displays welcome text, and prompts user to input petHP
    * @return - a positive integer representing the desired HP.
    */
   public int setupGame()
   {  
      //clearScreen();
      screen.add("Welcome to BattlePets! Grab a friend "
				+ "or a stranger and prepare for battle!");
      //screen.blankLine();
      screen.add("First Things First! How Many Fights Would You Like to Participate in?");      
      writeScreen();
      return getPositiveInt();
   }
   

   public double settingsMaxHp() {
	   screen.add("How Much Health Will Each Pet Have For the Battle?");
	   writeScreen();
	   return getPositiveInt();
   }
   
   

   public String promptPetName() {
	   
	   screen.add("Please choose your pet's name!");
	   writeScreen();
	   return getString();
   }
   
   
   public String promptType() {
	   ArrayList<String> valid = new ArrayList<String>(){{add("P");add("S");add("I");}};
	   screen.add("Please Select A Type For Your Pet.");
	   screen.add("P for Power");
	   screen.add("S for Speed");
	   screen.add("I for Intelligence");
	   writeScreen();
	   return getValidStringIgnoreCase(valid, "Please enter a valid pet Type");
   }
   
   
   /**
    * Generates and displays a summary of a pet's name and type
    * @param name - String representation of pet's name.
    * @param type - String representation of pet's type.*/
   public void displayPetInfo(String name, String type)
   {
	  //clearScreen();
      screen.add("Pet Info:");
      screen.add("    -Name: " + name);
      screen.add("    -Type: " + type);
      writeScreen();
   }
   
   /**
    * Calls the <code>screen.write</code> method to output all text stored
    * in the screen buffer: Handles all thrown exceptions.
    * @see <code>UI.ConsoleUI.Buffer</code>
    */
   private void writeScreen()
   {
      try
      {
         screen.write();
      }
      catch(Exception e)
      {
         System.out.println("Error Writing to screen: " + e);
      }
   }
   
   /**
    * Gets the next valid integer from the InputStream and handles exceptions.
    * @return an integer
    */
   public int getInt()
   {
      try
      {
         return reader.getInt();
      }
      catch(java.util.InputMismatchException ime)
      {
         screen.add("Please enter a valid integer");
         writeScreen();
         try
         {
        	 reader.getLine();
         }
         catch(Exception e)
         {
        	 System.out.println(e.getStackTrace());
         }
         return getInt();
      }
      catch(Exception e)
      {
         System.out.println("Error getting user input");
      }
      
      return 0;
   }
   
   /**
    * Gets the next positive integer from the InputStream.
    * @return a positive integer
    * @see <code>getInt</code>
    */
   public int getPositiveInt()
   {
      int input = getInt();
      while(input < 0)
      {
         screen.add("Please enter a positive Integer");
         input = getInt();
      }
      return input;
   }
   
   /**
    * Gets the next valid string from the InputStream.
    * @return a string
    */
   public String getString()
   {
      try
      { 	
         return reader.getString();
      }
      catch(java.util.InputMismatchException ime)
      {
         screen.add("Please enter a valid string");
         writeScreen();
         return getString();
      }
      catch(Exception e)
      {
         System.out.println("Error getting user input");
      }
      return null;
   }
   
   /**
    * Gets the next string from the InputStream that is contained in the 
    * ArrayList.
    * @param valid - <code>ArrayList</code> containing all acceptable strings.
    * @param promptText - Text to display when prompting for another string
    * @return a valid String as defined by the parameter valid. 
    */
   public String getValidStringIgnoreCase(ArrayList<String> valid, String promptText)
   {
      String input = getString();
      while(!valid.contains(input.toUpperCase()))
      {
         screen.add(promptText);
         writeScreen();
         input = getString();
      }
      return input;
   }
   
   /**
    * Clears the console screen: Handles errors from 
    * <code>UI.ConsoleUI.InputReader.clearScreen()</code>.
    */
   public void clearScreen()
   {
      try
      {
         screen.clearScreen();
      }
      catch(Exception e)
      {
         System.out.println("Error clearing Screen");
      }
   }
   /**
    * This function is called when a fight is started.
    * It outputs the current fight in the battle.
    * @param currentFightNum represents current fight in battle
    */
   public void showInitFight(int currentFightNum) {
	   screen.add("Start Of Fight " + (currentFightNum +1)+ "!");
	   writeScreen();
   }
   /**
    * Prompts the player to choose a skill. Takes the selected skill
    * and returns it.
    * @param skillList is the list of all the skills
    * @param currentPet is the current pet in question.
    * @return the selected skill
    */
   public Skills skillSelect(ArrayList<Skills>skillList, Pet currentPet) 
   {
	   int indexOfChosenSkill;
	   screen.add(currentPet.getName());
	   indexOfChosenSkill = pickSkill(skillList, currentPet, 0, skillList.size()+1);
	   screen.add("You Chose the Skill: " + skillList.get(indexOfChosenSkill));
	   currentPet.setSkill(skillList.get(indexOfChosenSkill));
	   writeScreen();
	   return skillList.get(indexOfChosenSkill);
   }
   /**
    * Checks to see if the selected skill is one of potential skills to 
    * be selected. If the skill is on cooldown, or the input is wrong, 
    * it will prompt the player again.
    * @param skillList is the list of select-able skills.
    * @param p is the pet in question
    * @param min is the boundary smallest integer value that represents a skill in skillList
    * @param max is the boundary for largest integer value that represents a skill in skillList
    * @return
    */
   private int pickSkill(ArrayList<Skills>skillList, Pet p, int min, int max) 
   {
	   screen.add("Please Choose a Skill: ");
	   for(int i = 0; i < skillList.size();i++)
	   {
			screen.add(Integer.toString(i+1) + "\t-" + skillList.get(i).toString());
	   }
	   writeScreen();
	   int choice = getPositiveInt();
	   if((choice < max && choice > min) && (!p.checkCoolDown(skillList.get(choice - 1))))
	   {
		   return choice-1;
	   }
	   else if(!(choice < max && choice > min))
	   {
		   screen.add("Invalid Command, Please Try again.");
		   writeScreen();
		   return pickSkill(skillList, p, min, max);
	   }
	   else if(p.checkCoolDown(skillList.get(choice - 1)))
		{
			   screen.add("Skill is on cooldown! Please choose a different skill.");
			   writeScreen();
			   return pickSkill(skillList, p, min, max);			   
	    }
	return choice - 1;


   }
   /**
    * This method prints the winner of each fight.
    * @param plist petList
    */
   public void printWinFightResults(ArrayList<Pet> plist)
   {
	   plist.get(0).incrementFightsWon();
	   screen.add(plist.get(0).getName() + " has won the fight!");
	   screen.add(plist.get(0).getName() + " now has " + plist.get(0).getFightsWon() + " wins!");
	   writeScreen();
   }
   /**
    * Prints out the output for fights that result in a tie.
    * @param plist is a pet List
    */ 
   public void printTieFightResults(ArrayList<Pet> plist)
   {
	   screen.add("The fight has ended in a tie! How rare!");
	   for(int i = 0; i < plist.size(); i++)
	   {
		   plist.get(i).incrementFightsWon();
		   screen.add(plist.get(i).getName() + " now has " + plist.get(i).getFightsWon() + " wins!");		   
	   }
	   writeScreen();

   }
   /**
    * Prints out the output for battles that result in a tie.
    * @param plist is a pet list
    */
   public void printTieBattleResults(ArrayList<Pet> plist)
   {
	   screen.add("The battle has ended in a tie. How anticlimatic");
	   for(int i = 0; i < plist.size(); i++)
	   {
		   screen.add(plist.get(i).getName() + " has " + plist.get(i).getFightsWon() + " total wins");		   
	   }
	   writeScreen();
   }
   /**
    * Displays the damage take for any given pet when damage
    * is take.
    * @param petName
    * @param damageTaken
    * @param healthRemaining
    */
   public void displayDamageTaken( String petName, double damageTaken, double healthRemaining)
   {
      screen.add(petName + " recieved " + damageTaken + " damage.");
      screen.add(petName + " has " + healthRemaining + " remaining.");
      screen.blankLine();
      screen.blankLine();
      writeScreen();
   }
   /**
    * When a pet takes enough damage to pass out, this method is called.
    * @param petName
    */
   public void petWentToSleep(String petName)
   {
       screen.add("OH NO!... " + petName + " recieved so much damage that");
       screen.add("they passed out... hopefully not forever...");
       screen.blankLine();
       screen.add(petName + "'s owner blacked out from embarasement...");
       screen.add("they woke up in the nearest animal hospital (-$500)");
       screen.blankLine();
       writeScreen();
   }
       
/**
 * This method prints out the results of the battle.
 * @param plist is a pet list
 */
 public void printWinBattleResults(ArrayList<Pet> plist) 
{
	   for(int i = 0; i < plist.size(); i++)
	   {
		   screen.add(plist.get(i).getName() + " has " + plist.get(i).getFightsWon() + " total wins");		   
	   }
	   if(plist.get(0).getFightsWon() > plist.get(1).getFightsWon())
		   screen.add("The battle has ended! " + plist.get(0).getName() + " has won all the "
			   +"fame and glory in the world!!!");
	   else
		   screen.add("The battle has ended! " + plist.get(1).getName() + " has won all the "
				   +"fame and glory in the world!!!");
	   writeScreen();
}
     
}

