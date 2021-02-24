package Game;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import GUI.FXMLPresenter;
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
	private FXMLPresenter presenter;
	private Battle battle;
	private int roundNum;
	
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
	 * Constructor for fight, for use with the GUI
	 * @param fightNums
	 * @param petList
	 * @param pres
	 * @param referee
	 * @param b
	 */
	public Fight(int fightNums,List<Playable> petList, FXMLPresenter pres, Referee referee, Battle b) 
	{
		this.fightNum = fightNums;
		this.sleepingPets = new ArrayList<Playable>();
		sortedPets = new ArrayList<Playable>();
		this.currentPets =  petList; 
		this.rounds = new ArrayList<Round>();
		ref = referee;
		presenter = pres;
		battle = b;
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
	 * Starts a fight for the GUI
	 */
	public void startGuiFight() 
	{
		presenter.setFight(this);
		roundNum = 0;
		nextRound();
	}
	
	/**
	 * Starts the next round if there are more than one pet remaining, used with GUI
	 */
	public void nextRound() 
	{
		if(currentPets.size() > 1) 
		{
			roundNum++;
			rounds.add(new Round(currentPets, roundNum, presenter));
			presenter.displayRoundInfoGUI(roundNum);
			rounds.get(roundNum - 1).startGuiRound();
		}
		else
		{
			endFight();
		}
		
	}

	/**
	 * The the current fight and adds a victory to the winning pet
	 */
	private void endFight() 
	{
		replaceSleepingPets();
		
		if(isFightTied())
		{
			for (int i = 0; i < sortedPets.size(); i++)
			{
				sortedPets.get(i).incrementFightsWon();
			}
		}
		else
		{
			Playable winner = findWinner();
			winner.incrementFightsWon();
			presenter.displayWinFightResultsGUI(winner);
		}
		battle.nextFight();
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
			winnerIndex = findMax(ref.getLastRoundPets());
			return ref.getLastRoundPets().get(winnerIndex);
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
		
		if(sortedPets.get(0).getCurrentHp() == sortedPets.get(1).getCurrentHp())
			isTied = true;
		
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
