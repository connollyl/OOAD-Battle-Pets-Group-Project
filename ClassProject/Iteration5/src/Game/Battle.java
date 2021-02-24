package Game;
import java.util.ArrayList;
import java.util.List;

import GUI.FXMLPresenter;
import Pets.Pet;
import UI.StartEndUI;
import interfaces.Playable;

/*
 * This class deals with initiating a battle,
 * initiates a list of fights, and deals with their aftermath.
 * Author: Aidan Gengler, Andrew Cummings, Logan Garza
 */


public class Battle 
{
	private List<Playable> petList; // List of pets for use in tracking data and sending to fights.
	private List<Playable> sortedPets; // List of tied pets in order to determine ties.
	private List<Playable> winners;
	private List<Fight> fightList; // List of fights to initiate.
	private BattleSetup settings; // Settings that provide data for setting up the ensuing battle.
	private Referee referee; // Link to the referee. Used to reset the sleeping pets.
	private StartEndUI ui; // UI to use for the ending statements.
	private Fight currentFight; // The current fight that is running.
	private FXMLPresenter presenter;
	private int numFights;
	private int fight;
	private Referee ref;

	/**
	 * Constructor for Battle class. Initializes all data 
	 * @param battleSettings
	 * @param pets
	 * @param ref
	 * @param ui
	 */
	public Battle(BattleSetup battleSettings, List<Playable> pets, Referee ref, StartEndUI ui) 
	{
		this.fightList = new ArrayList<Fight>();
		this.settings = battleSettings;
		this.petList = pets;
		this.referee = ref;
		this.ui = ui;
		this.initializeList();
	}
	
	/**
	 * Constructor for battle for use with the GUI
	 * @param pets
	 * @param fightNum
	 * @param pres
	 * @param referee
	 */
	public Battle(List<Playable> pets, int fightNum, FXMLPresenter pres, Referee referee) 
	{
		this.fightList = new ArrayList<Fight>();
		this.petList = pets;
		numFights = fightNum;
		presenter = pres;
		ref = referee;
		this.initializeGuiList();
	}

	/**
	 * Returns the list of pets.
	 * @return petList
	 */
	public List<Playable> getPets() 
	{
		return this.petList;
	}

	/**
	 * Checks to see if the battle was tied.
	 * @return true if two pets have the same number of fight wins
	 */
	public boolean isBattleTied() 
	{
		sortList();
		
		if(sortedPets.get(0).getFightsWon() == sortedPets.get(1).getFightsWon())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sorts the list of pets into a sorted list by number of wins, used to check ties
	 */
	public void sortList() 
	{
		sortedPets =  new ArrayList <Playable>();
		List<Playable> tempList = new ArrayList <Playable>();
		for (int i = 0; i < petList.size(); i++)
		{
			tempList.add(petList.get(i));
		}
		
		while(!(tempList.isEmpty()))
		{
			int maxIndex = findMax(tempList);
			sortedPets.add(tempList.get(maxIndex));
			tempList.remove(maxIndex);
		}
	}
	
	/**
	 * Finds the pet with the most number of fight wins
	 * @param tempList
	 * @return index of the pet with highest fight wins
	 */
	public int findMax(List<Playable> tempList) 
	{
		int maxIndex = 0;
		
		if (tempList.size() > 1)
		{
			for(int i = 1; i < tempList.size(); i++)
			{
				if(tempList.get(maxIndex).getFightsWon() < tempList.get(i).getFightsWon())
				{
					maxIndex = i;
				}
			}
		}
		
		return maxIndex;
	}
	
	/**
	 * Initalizes a list with the given number of fights.
	 */
	private void initializeList() {
		for(int i = 0; i < settings.getNumFights(); i++) 
		{
			this.fightList.add(new Fight(i+1, this.petList, referee, ui));
		}
	}	
	
	/**
	 * Initalizes a list with the given number of fights.
	 */
	private void initializeGuiList() {
		for(int i = 0; i < numFights; i++) 
		{
			this.fightList.add(new Fight(i+1, this.petList, presenter, ref, this));
		}
	}
	
	/**
	 * Prints out battle results based on whether or not the battle was
	 * tied or not
	 * @return
	 */
	public boolean isTied() 
	{
		
		if (isBattleTied())
		{
			ui.printTieBattleResults(sortedPets);
			return true;
		}
		
		ui.printWinBattleResults(petList, findWinner());	
		return false;
	}
	
	/**
	 * Prints out battle results based on whether or not the battle was
	 * tied or not in the GUI version.
	 * @return
	 */
	public boolean guiIsTied() 
	{
		if (isBattleTied())
		{
			presenter.displayTieBattleResultsGUI(sortedPets);
			return true;
		}
		else
		{
			presenter.displayWinBattleResultsGUI(petList, findWinner());
			return false;
		}
	}	
	
	/**
	 * Starts the battle and iterates through the fights
	 * @return
	 */
	public boolean startBattle() 
	{
		for (int i = 0; i < fightList.size(); i++) 
		{
			currentFight = fightList.get(i);
			currentFight.startFight();
			referee.resetPets();
		}

		isTied();
		return false;
	}
	
	/**
	 * Starts the battle and iterates through the fights
	 * @return
	 */
	public void startGuiBattle() 
	{
		fight = 0;
		nextFight();
	}

	/**
	 * Resets the pets and preps the next fight
	 */
	public void nextFight() 
	{
		ref.resetPets();
		if(fight < numFights) 
		{
			currentFight = fightList.get(fight);
			fight++;
			presenter.displayFightInfoGUI(fight);
			currentFight.startGuiFight();
		}
		else
		{
			this.endBattle();
		}
	}

	/**
	 * Checks to see if the fight is tied, for use with the GUI
	 */
	private void endBattle() 
	{
		guiIsTied();
		presenter.endBattle();
	}

	/**
	 * Returns the pets with the most wins
	 * @return winner
	 */
	public Playable findWinner()
	{
		Playable winner = petList.get(0);
		
		for(int i = 1; i < petList.size(); i++)
		{
			if (winner.getFightsWon() < petList.get(i).getFightsWon())
			{
				winner = petList.get(i);
			}
		}
		return winner;
	}
}
