package Game;
import java.util.ArrayList;
import java.util.List;
import Pets.Pet;
import UI.StartEndUI;

/*
 * This class deals with initiating a fight,
 * initiates a list of rounds, and deals with their aftermath.
 * Author: Aidan Gengler, Andrew Cummings, Logan Garza
 */

public class Fight 
{
	private List<Round> rounds;
	private List<Pet> sortedPets;
	private List<Pet> currentPets;
	private List<Pet> sleepingPets;
	private Referee ref;
	private StartEndUI ui;
	private int fightNum, numTied;
	
	/**
	 * Initializes all data required to run a fight.
	 * @param i
	 * @param petList
	 * @param referee
	 * @param uI
	 */
	public Fight(int fightNums,List<Pet> petList, Referee referee, StartEndUI uI) 
	{
		this.fightNum = fightNums;
		this.sleepingPets = new ArrayList<Pet>();
		sortedPets = new ArrayList<Pet>();
		this.currentPets =  petList; 
		this.rounds = new ArrayList<Round>();
		this.ref = referee;
		this.ui = uI;
	}

	/**
	 * Starts the fight and sets a variable number of rounds to
	 * run through.
	 */
	public void startFight() 
	{
		ui.showInitFight(fightNum);
		int round = 0;
		while(currentPets.size() > 1) 
		{
			rounds.add(new Round(currentPets, round + 1, ref));
			rounds.get(round).startRound();
			round++;
		}
		
		replaceSleepingPets();
		
		if(isFightTied())
		{
			for (int i = 0; i < sortedPets.size(); i++)
			{
				sortedPets.get(i).incrementFightsWon();
			}

			ui.printTieFightResults(sortedPets);
		}
		else
		{
			Pet winner = findWinner();
			winner.incrementFightsWon();
			ui.printWinFightResults(winner);
		}
	}
	
	/**
	 * Returns the winning pet.
	 * @return Winning Pet
	 */
	private Pet findWinner()
	{
		int winnerIndex;
		if(currentPets.isEmpty())
		{
			winnerIndex = findMax(ref.getSleepingPets());
			return ref.getSleepingPets().get(winnerIndex);
		}
		winnerIndex = findMax(currentPets);
		return currentPets.get(winnerIndex);
	}

	/**
	 * Returns true if the fight is tied,
	 * false if there is a single winner.
	 * @return boolean
	 */
	private boolean isFightTied() 
	{
		return sortPets();
	}	
	
	/**
	 * Sorts the pets from most HP to least.
	 * Returns true if tied, false if not.
	 * @return boolean
	 */
	private boolean sortPets()
	{
		boolean isTied = false;
		sortedPets = new ArrayList <Pet>();
		List<Pet> tempList = new ArrayList <Pet>();
		
		for (int i = 0; i < currentPets.size(); i++)
		{
			tempList.add(currentPets.get(i));
		}
		for(int i = 0; i < ref.getSleepingPets().size(); i++)
		{
			tempList.add(ref.getSleepingPets().get(i));
		}
		
		while(!(tempList.isEmpty()))
		{
			int maxIndex = findMax(tempList);
			sortedPets.add(tempList.get(maxIndex));
			tempList.remove(maxIndex);
		}
		
		
		for (int i = numTied + 1; i < sortedPets.size(); i++)
		{
			sortedPets.remove(i);
		}
		
		return isTied;
	}
	
	/**
	 * Places sleeping pets back into the current pets list.
	 */
	private void replaceSleepingPets()
	{
		while(!(sleepingPets.isEmpty()))
		{
			currentPets.add(sleepingPets.get(0));
			sleepingPets.remove(0);
		}
	}
	
	/**
	 * Finds index of pet with most HP left.
	 * @param tempList
	 * @return max index
	 */
	private int findMax(List <Pet> tempList)
	{
		int maxIndex = 0;
		
		if (tempList.size() > 1)
		{
			for(int i = 1; i < tempList.size(); i++)
			{
				if(tempList.get(maxIndex).getHP() < tempList.get(i).getHP())
				{
					maxIndex = i;
				}
			}
		}
		
		return maxIndex;
	}
}
