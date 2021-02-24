
/**
 * This class is the main class that allows the game to run,
 * and interacts with all other classes
 * Author: Logan Garza, with help from Eric Weber and Andrew Cummings.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import Pets.Skills;
import Pets.Pet;
import UI.ConsoleUI;


/**
 * This is the main class that runs the game. It handles pet creation,
 * fights, battles, and game rules.
 * @author garzal
 *
 */
public class Game
{
	private static Scanner in = new Scanner(System.in);
	private static ConsoleUI ui = new ConsoleUI();
	
	//<ARRAY LISTS>
	private static List<Pet> pets = new ArrayList<>();
	private static List<Pet> sleepingPets = new ArrayList<>();
	private static List<Skills> selectedSkills = new ArrayList<>();
	
	private static final int numPets = 2;
	private static final HashMap<String, String> pTypeMap = new HashMap<>();
	
	private static int numFights;
	private static int livePets = numPets;
	private static double currMaxHp;

	/**
	 * Main function that runs the program.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		numFights = ui.setupGame();
		currMaxHp = ui.settingsMaxHp();
		createPets(numPets);
		startBattle();
		if(isBattleTied(pets))
			ui.printTieBattleResults(pets);
		else
			ui.printWinBattleResults(pets);
		System.exit(1);
	}
	
	/**
	 * Creates a pet for the player and then displays its info.
	 * @param petCount
	 */
	public static void createPets(int petCount) 
	{
		for (int i = 0; i < petCount; i++) 
		{
			pets.add(createPlayer());
			displayPetInfo(pets.get(i));
		}
	}
	
	/**
	 * Creates a pet for the player.
	 * @return a Pet object
	 */
	public static Pet createPlayer() 
	{
		String pName = ui.promptPetName();
		String petType = ui.promptType();
		return createPet(petType, pName);
	}
		
	/**
	 * Creates a pet with the chosen petType
	 * @param petType
	 * @param name
	 * @return Pet obj
	 */
	public static Pet createPet(String petType, String name) 
	{
		Pet p;
		switch(petType.toUpperCase()) 
		{
		case "P":
			p = new Pets.Power(currMaxHp, name);
			break;
		case "I":
			p = new Pets.Intelligence(currMaxHp, name);
			break;
		default:
			p = new Pets.Speed(currMaxHp, name);
			break;	
		}
		return p;
	}
	
	/**
	 * Displays pet information, such as name and type
	 * @param pet
	 */
	public static void displayPetInfo(Pet pet) 
	{
		ui.displayPetInfo(pet.getName(), pet.getType());
	}
	
	/**
	 * Checks to see if any pet is KO'd on the battlefield,
	 * and adds them to the sleeping pets list.
	 * @return true if sleeping pets exist, false otherwise
	 */
	private static boolean isAnyKO() 
	{
		int size = pets.size() - 1;
		for (int i = size; i >= 0; i--) 
		{
			if (pets.get(i).isKO()) 
			{
				sleepingPets.add(pets.get(i));
				pets.remove(i);
			}
		}
		if(sleepingPets.size() > 0)
			return true;
		return false;
	}
	/**
	 * returns true if there is only one pet left alive.
	 * @return true for one pet left, false otherwise
	 */
	private static boolean onePetLeft() 
	{
		if (isAnyKO()) 
		{
			if (pets.size() <= 1) 
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Starts the battle sequence, ends when there are no more
	 * fights left. 
	 */
	private static void startBattle() 
	{
		for (int i = 0; i < numFights; i++) 
		{
			ui.showInitFight(i);
			startFight();
		}
	}
	
	/**
	 * Starts a fight and handles fighting sequences,
	 * such as dealing damage and choosing skills,
	 * ends when one pet is left standing and begins a 
	 * new fight if there are more available.
	 */
	private static void startFight() 
	{
		while (!onePetLeft()) 
		{
			for (int i = 0; i < pets.size(); i++) 
			{
				selectedSkills.add(ui.skillSelect(Skills.getSkillList(), pets.get(i)));	
			}

			singleDamagePhase();
			for (int i = 0; i < pets.size(); i++) {
				Pet currentPet = pets.get(i);
				ui.displayDamageTaken(currentPet.getName(), currentPet.getDamage(), currentPet.getHP());
			}
			damgePhaseReset();
		}
		fightReset();
	}
	/**
	 * This function basically runs the turn between
	 * pets, having them choose skills chronologically,
	 * and then calling useSkill to do damage.
	 */
	private static void singleDamagePhase() 
	{
		Pet currentPet, nextPet;
		Skills chosenSkill;
		for(int i = 0; i < pets.size();i++) 
		{
			currentPet = pets.get(i);
			chosenSkill = selectedSkills.get(i);
			if(i == pets.size() - 1) 
			{
				nextPet = pets.get(0);
			}else
			{
				nextPet = pets.get(i+1);
			}
			pets.get(i).useSkill(chosenSkill, nextPet);
			currentPet.setCoolDown(chosenSkill);
			currentPet.coolDownDecrement();
		}
		//End Of Damage Phase
	}
	
	/**
	 * Checks to see if the battle is tied by seeing if any pets have the
	 * same number of fights that happen to be the most wins out of any
	 * other pets.
	 * @param plist
	 * @return true if tied, false otherwise
	 */
	private static boolean isBattleTied(ArrayList<Pet> plist)
	{
		if(plist.get(0).getFightsWon() == plist.get(1).getFightsWon())
			return true;
		return false;
	}
	
	/**
	 * Checks to see if a fight is tied by seeing if any pets
	 * KO'd each other at the same time, resulting in a tie.
	 * @param plist
	 * @return true if tied, false otherwise
	 */
	private static boolean isFightTied(ArrayList<Pet> plist)
	{
		if(plist.size() == 2)
			return true;
		return false;
	}
	
	/**
	 * Clears selected chosen skills from the pets.
	 */
	private static void damgePhaseReset() 
	{
		selectedSkills.clear();
	}
	
	/**
	 * After a fight is over, checks to see if any fights resulted in a tie,
	 * and prints to the screen fight results.
	 * If there were any sleeping pets, they get their HP back and are added back
	 * to the pets list, and clears the sleeping pets list at the end.
	 */
	private static void fightReset()
	{
		if(isFightTied(sleepingPets))
			ui.printTieFightResults(sleepingPets);
		else
			ui.printWinFightResults(pets);
		for(int i = 0; i < sleepingPets.size(); i++)
		{
			pets.add(sleepingPets.get(i)); 
		}
		for(int i = 0; i < pets.size(); i++)
		{
			pets.get(i).setHP(currMaxHp);
			pets.get(i).coolDownDecrement();
		}	
		sleepingPets.clear();
	}
	
	
	/**
	 * Andrew Cummings claimed he spoke to you in your office and said
	 * that based on what we had, it was ok to not have this implemented 
	 * for now, but will be needed for the next iteration.
	 * @return null for now, should be a skills object.
	 */
	public Skills chooseSkill() {
		return null;
		//Skills.

	}

}
