package Game;
import java.util.ArrayList;
import java.util.List;

import Pets.Pet;
import UI.StartEndUI;

/*
 * This class deals with initiating a battle,
 * initiates a list of fights, and deals with their aftermath.
 * Author: Aidan Gengler, Andrew Cummings, Logan Garza
 */


public class Battle 
{
	private List<Pet> petList; // List of pets for use in tracking data and sending to fights.
	private List<Pet> sortedPets; // List of tied pets in order to determine ties.
	private List<Fight> fightList; // List of fights to initiate.
	private BattleSetup settings; // Settings that provide data for setting up the ensuing battle.
	private Referee referee; // Link to the referee. Used to reset the sleeping pets.
	private StartEndUI ui; // UI to use for the ending statements.
	private Fight currentFight; // The current fight that is running.

	/**
	 * Constructor for Battle class. Initializes all data 
	 * @param battleSettings
	 * @param pets
	 * @param ref
	 * @param ui
	 */
	public Battle(BattleSetup battleSettings, List<Pet> pets, Referee ref, StartEndUI ui) 
	{
		this.fightList = new ArrayList<Fight>();
		this.settings = battleSettings;
		this.petList = pets;
		this.referee = ref;
		this.ui = ui;
		this.initializeList();
	}

	/**
	 * Returns the list of pets.
	 * @return petList
	 */
	public List<Pet> getPets() 
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
		sortedPets =  new ArrayList <Pet>();
		List<Pet> tempList = new ArrayList <Pet>();
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
	public int findMax(List <Pet> tempList) 
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
	 * Returns the pets with the most wins
	 * @return winner
	 */
	private Pet findWinner()
	{
		Pet winner = petList.get(0);
		
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
