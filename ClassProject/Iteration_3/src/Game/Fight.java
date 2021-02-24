package Game;
import java.util.ArrayList;
import java.util.List;
import Pets.Pet;
import UI.StartEndUI;
import interfaces.Playable;

/*
 * This class deals with initiating a fight,
 * initiates a list of rounds, and deals with their aftermath.
 * Author: Aidan Gengler, Andrew Cummings, Logan Garza
 */

public class Fight 
{
	private List<Round> rounds;
	private List<Playable> sortedPets;
	private List<Playable> currentPets;
	private List<Playable> sleepingPets;
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
	public Fight(int fightNums,List<Playable> petList, Referee referee, StartEndUI uI) 
	{
		this.fightNum = fightNums;
		this.sleepingPets = new ArrayList<Playable>();
		sortedPets = new ArrayList<Playable>();
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
			Playable winner = findWinner();
			winner.incrementFightsWon();
			ui.printWinFightResults(winner);
		}
	}
	
	/**
	 * Returns the winning pet.
	 * @return Winning Pet
	 */
	private Playable findWinner()
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
		sortedPets = new ArrayList <Playable>();
		List<Playable> tempList = new ArrayList <Playable>();
		
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
	 * @param currentPets2
	 * @return max index
	 */
	private int findMax(List<Playable> currentPets2)
	{
		int maxIndex = 0;
		
		if (currentPets2.size() > 1)
		{
			for(int i = 1; i < currentPets2.size(); i++)
			{
				if(currentPets2.get(maxIndex).getCurrentHp() < currentPets2.get(i).getCurrentHp())
				{
					maxIndex = i;
				}
			}
		}
		
		return maxIndex;
	}
}
