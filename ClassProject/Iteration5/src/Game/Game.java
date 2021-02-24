package Game;

import interfaces.Startable;
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

public class Game  implements Startable
{
	private static final startType gameType = startType.CONSOLE;
	
	private StartEndUI ui = new StartEndUI();
	private boolean battleRunning = true;
	private boolean gameRunning = true;
	private BattleSetup battleSettings;
	private Battle battle;
	private List<Playable> pets = new ArrayList<Playable>();
	private Referee referee;
	
	/**
	 * Starts the Game
	 */
	public void start() 
	{
		try
		{
			while(gameRunning) 
			{
				battleRunning = true;
				newGame();
				gameRunning = ui.promptAnotherBattle();
				pets.clear();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns if the game will launch GUI or Text-based
	 * @return
	 */
	public static int getGameType()
	{
		return gameType.getType();
	}
	
	/**
	 * Initializes a new game and will continue running
	 * until the user decides to end it.
	 * @return
	 * @throws IOException
	 */
	public boolean newGame() throws IOException {
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
	private void createPets(BattleSetup settings) {
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
		}
	}

	/**
	 * Returns a reference to the list of pets.
	 * @return petList
	 */
	public List<Playable> getPets() {
		return battle.getPets();
	}
	
	/**
	 * Runs the text-based console version of the game
	 * @throws IOException
	 */
	protected void runConsole() throws IOException
	{
		while(gameRunning) {
			battleRunning = true;
			newGame();
			gameRunning = ui.promptAnotherBattle();
			pets.clear();
		}
	}

}
