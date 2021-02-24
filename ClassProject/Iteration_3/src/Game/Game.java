package Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Pets.AIPet;
import Pets.Pet;
import Pets.PetTypes;
import Pets.PlayerTypes;
import Pets.Skills;
import UI.StartEndUI;
import interfaces.Playable;

/*
 * This class is the entry point of the program.
 * It acts as the backbone for the events that will 
 * drive the rest of the program.
 * Author: Everyone
 */

public class Game 
{
	private static StartEndUI ui = new StartEndUI();
	private static boolean battleRunning = true;
	private static boolean gameRunning = true;
	private static BattleSetup battleSettings;
	private static Battle battle;
	private static List<Playable> pets = new ArrayList<Playable>();
	private static Referee referee;

	/**
	 * Main method for the game.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		while(gameRunning) {
			battleRunning = true;
			newGame();
			gameRunning = ui.promptAnotherBattle();
			pets.clear();
		}

	}
	
	/**
	 * Initializes a new game and will continue running
	 * until the user decides to end it.
	 * @return
	 * @throws IOException
	 */
	public static boolean newGame() throws IOException {
		while (battleRunning) 
		{
			battleSettings = new BattleSetup();
			createPets(battleSettings);
			referee = new Referee(pets, battleSettings.getSeed());
			battle = new Battle(battleSettings, pets, referee, ui);
			battleRunning = battle.startBattle();
		}
		return false;
	}
	
	
	
	/**
	 * Gets all pets in battle, and returns it to referee
	 * @return 
	 */
	private static void createPets(BattleSetup settings) {
		for (int i = 0; i < settings.getNumPets(); i++) {
			 
			switch (settings.getPetTypes().get(i).toUpperCase()) 
			{
			case "P":
				if (settings.getPlayerTypes().get(i) == PlayerTypes.TOPOFTHEFOODCHAIN)
				{
					pets.add(new Pet(settings.getBattleMaxHp(), PetTypes.POWER, settings.getPetNames().get(i), settings.getMasterNames().get(i), i));
				}
				else
				{
					pets.add(new AIPet(settings.getBattleMaxHp(), PetTypes.POWER, settings.getPetNames().get(i), settings.getMasterNames().get(i), i));
				}
				break;
			case "I":
				if (settings.getPlayerTypes().get(i) == PlayerTypes.TOPOFTHEFOODCHAIN)
				{
					pets.add(new Pet(settings.getBattleMaxHp(), PetTypes.INTELLIGENCE, settings.getPetNames().get(i), settings.getMasterNames().get(i), i));
				}
				else
				{
					pets.add(new AIPet(settings.getBattleMaxHp(), PetTypes.INTELLIGENCE, settings.getPetNames().get(i), settings.getMasterNames().get(i), i));
				}
				break;
			default:
				if (settings.getPlayerTypes().get(i) == PlayerTypes.TOPOFTHEFOODCHAIN)
				{
					pets.add(new Pet(settings.getBattleMaxHp(), PetTypes.SPEED, settings.getPetNames().get(i), settings.getMasterNames().get(i), i));
				}
				else
				{
					pets.add(new AIPet(settings.getBattleMaxHp(), PetTypes.SPEED, settings.getPetNames().get(i), settings.getMasterNames().get(i), i));
				}
				break;
			}
			// pets.add(p);
		}
	}

	/**
	 * Returns a reference to the list of pets.
	 * @return petList
	 */
	public List<Playable> getPets() {
		return battle.getPets();
	}


}
