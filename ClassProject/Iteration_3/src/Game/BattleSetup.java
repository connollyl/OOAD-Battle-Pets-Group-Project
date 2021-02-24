package Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Pets.PlayerTypes;
import UI.BattleSetupUI;

/*
 * This class obtains all data for the ensuing battle
 * and holds it for futher use in Battle.
 * Author: Andrew Cummings & Logan Garza
 */

public class BattleSetup {

	private BattleSetupUI ui = new BattleSetupUI();
	private int numFights;
	private int numPets;
	private double battleMaxHp;
	private long RNGSeed;
	private String name;
	private String type;
	private PlayerTypes playerType;
	private String masterName;
	private List<String> petNames;
	private List<String> petTypes;
	private List<PlayerTypes> playerTypes;
	private List<String> masterNames;

	/**
	 * Obtains the input using the ui, and initializes
	 * all values for the battle.
	 * @throws IOException
	 */
	public BattleSetup() throws IOException {
		this.numFights = ui.setupGame();
		this.battleMaxHp = ui.settingsMaxHp();
		this.numPets = ui.promptNumPets();
		this.petNames = new ArrayList<String>();
		this.petTypes = new ArrayList<String>();
		this.playerTypes = new ArrayList<PlayerTypes>();
		this.masterNames = new ArrayList<String>();
		for(int i = 1; i < this.numPets + 1; i++)
		{
			this.playerType = ui.promptPlayerType();
			this.playerTypes.add(playerType);
			this.masterName = ui.promptPlayerName();
			this.masterNames.add(masterName);
			this.name = ui.promptPetName(masterName);
			this.petNames.add(name);
			this.type = ui.promptPetType();
			this.petTypes.add(type);
		}
		this.RNGSeed = ui.promptSeed();
		
	}
	
	
	/**
	 * Returns the randomizations seed.
	 * @return RNGSeed
	 */
	public long getSeed() {
		return this.RNGSeed;
	}
	
	/**
	 * Returns the number of fights.
	 * @return numFights
	 */
	public int getNumFights() {
		return this.numFights;
	}

	/**
	 * Returns the max value of HP.
	 * @return battleMaxHp
	 */
	public double getBattleMaxHp() {
		return this.battleMaxHp;
	}

	/**
	 * Returns the number of Pets.
	 * @return numPets
	 */
	public int getNumPets() {
		return this.numPets;
	}

	/**
	 * Returns the pet names.
	 * @return petNames
	 */
	public List<String> getPetNames() {
		return this.petNames;
	}

	/**
	 * Returns the types of the pets.
	 * @return petTypes
	 */
	public List<String> getPetTypes() {
		return this.petTypes;
	}
	/**
	 * Returns the types of the Player.
	 * @return PlayerTypes
	 */
	public List<PlayerTypes> getPlayerTypes()
	{
		return this.playerTypes;
	}
	/**
	 * Returns the Names of the Players.
	 * @return PlayerNames
	 */
	public List<String> getMasterNames()
	{
		return this.masterNames;
	}
	
	
	
	
	

}
