package Game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import Events.AttackEvent;
import Events.AttackEvent.AttackEventBuilder;
import Events.FightStartEvent;
import Events.FightStartEvent.FightStartEventBuilder;
import Events.PlayerEventInfo;
import Events.PlayerEventInfo.PlayerEventInfoBuilder;
import Events.RoundStartEvent;
import Events.RoundStartEvent.RoundStartEventBuilder;
import GUI.FXMLPresenter;
import Pets.Pet;
import Pets.Skills;
import UI.RoundUI;
import interfaces.Playable;

/*
 * This class mediates between the Pets, calculator, and RNG
 * classes. It also maintains the state of each pet.
 * Author: Aidan Gengler
 */
public class Referee extends Observable
{
	private List<Playable> pets;
	private List<Playable> sleepingPets;
	private List<Playable> lastRoundPets = new ArrayList<>();
	private RNG randomizer;
	private Calculator calc;
	private List <AttackEvent> attackEvents = new ArrayList<AttackEvent>();
	private RoundStartEvent roundStartEvent;
	private FightStartEvent fightStartEvent;
	private RoundUI rui;
	private List <PlayerEventInfo> playerEventInfo = new ArrayList <PlayerEventInfo>();
	private AttackEventBuilder attackEventBuilder = new AttackEventBuilder();
	private RoundStartEventBuilder roundEventBuilder = new RoundStartEventBuilder();
	private FightStartEventBuilder fightEventBuilder = new FightStartEventBuilder();
	private PlayerEventInfoBuilder playerEventInfoBuilder = new PlayerEventInfoBuilder();
	private FXMLPresenter pres;
	
	/**
	 * Constructor for the referee class. Initializes the lists and 
	 * seed for the RNG and pets.
	 * @param pets2
	 * @param seed
	 */
	public Referee(List<Playable> pets2, long seed)
	{
		pets = pets2;
		sleepingPets = new ArrayList<>();
		randomizer = new RNG(seed);
		rui = new RoundUI();
		calc = new Calculator();
		for(Playable p : pets2)
		{
			this.addObserver(p);
		}
		this.generatePlayerEventInfo();	
	}
	
	public Referee(List<Playable> pets2, long seed, FXMLPresenter fxmlPresenter) 
	{
		pets = pets2;
		sleepingPets = new ArrayList<>();
		lastRoundPets = new ArrayList<>();
		randomizer = new RNG(seed);
		calc = new Calculator();
		pres = fxmlPresenter;
		for(Playable p : pets2)
		{
			this.addObserver(p);
		}
		this.generatePlayerEventInfo();	
	}

	/**
	 * Creates list of PlayerEventInfo objects.
	 */
	public void generatePlayerEventInfo()
	{
		Skills[] skillArr = Skills.getNumSkills();
		List <Skills> skillList = new ArrayList <Skills>();
		for(int i = 0; i < skillArr.length; i++)
		{
			skillList.add(skillArr[i]);
		}
		for(int i = 0; i < pets.size(); i++)
		{
			playerEventInfo.add(playerEventInfoBuilder.withPetType(pets.get(i).getPetType())
					.withPlayerType(pets.get(i).getPlayerType())
					.withPetSkills(skillList)
					.withStartingHp(pets.get(i).getMaxHp())
					.build());
			notifyPlayerEvent(playerEventInfo);
		}
	}
	/**
	 *Builds roundStartEvent object.
	 */
	public void roundStarted(int roundNumber)
	{
		roundStartEvent = roundEventBuilder.withRoundNumber(roundNumber).build();
		notifyRoundEvent(roundStartEvent);
	}
	/**
	 *Builds fightStartEvent object.
	 */
	public void fightStarted(int fightNumber)
	{
		
		fightStartEvent = fightEventBuilder.withFightNumber(fightNumber)
				.withPlayerEventInfo(playerEventInfo).build();
		notifyFightEvent(fightStartEvent);
	}
	
	/** 
	 * Calculates the damage of the current round.
	 */
	public void singleDamagePhase() 
	{
		decrementCooldown(pets);
		Playable currentPet, nextPet;
		double damage, randomDamage;
		for (int i = 0; i < pets.size(); i++) 
		{
			randomDamage = randomizer.getRandomValue();
			currentPet = pets.get(i);
			currentPet.setRandomDamageDealt(randomDamage);
			
			if (i == pets.size() - 1) 
			{
				nextPet = pets.get(0);
			} 
			else 
			{
				nextPet = pets.get(i + 1);
			}
			
			nextPet.setRandomDamageTaken(randomDamage);
			damage = calc.calculateDamage(currentPet, nextPet, randomDamage);
			nextPet.updateHp(damage);

			attackEvents.add(attackEventBuilder
					.withAttackIndex(currentPet.getPlace())
					.withVictimIndex(nextPet.getPlace())
					.withAttackSkill(currentPet.getSkill())
					.withRandomDamage(randomDamage)
					.withConditionalDamage(damage - randomDamage)
					.build());
			notifyAttackEvent(attackEvents);
		}
		rui.displayDamageAndHP(pets);
		checkSleepers();
		calculateDifferenceInDamage();
		// End Of Damage Phase
	}
	
	/** 
	 * Calculates the damage of the current round.
	 */
	public void guiSingleDamagePhase() 
	{
		decrementCooldown(pets);
		Playable currentPet, nextPet;
		double damage, randomDamage;
		for (int i = 0; i < pets.size(); i++) 
		{
			randomDamage = randomizer.getRandomValue();
			currentPet = pets.get(i);
			currentPet.setRandomDamageDealt(randomDamage);
			
			if (i == pets.size() - 1) 
			{
				nextPet = pets.get(0);
			} 
			else 
			{
				nextPet = pets.get(i + 1);
			}
			
			nextPet.setRandomDamageTaken(randomDamage);
			damage = calc.calculateDamage(currentPet, nextPet, randomDamage);
			nextPet.updateHp(damage);

			attackEvents.add(attackEventBuilder
					.withAttackIndex(currentPet.getPlace())
					.withVictimIndex(nextPet.getPlace())
					.withAttackSkill(currentPet.getSkill())
					.withRandomDamage(randomDamage)
					.withConditionalDamage(damage - randomDamage)
					.build());
			notifyAttackEvent(attackEvents);
		}
		pres.displayDamageAndHPGUI(pets);
		checkSleepers();
		calculateDifferenceInDamage();
	}
	
	/**
	 * Checks for sleeping pets, and sends them to the 
	 * sleeping pets list.
	 */
	private void checkSleepers()
	{
		Playable currentPet;
		int sleepCounter = 0;
		for(int j = 0; j < pets.size(); j++)
		{
			if(!pets.get(j).isAwake())
				sleepCounter++;
			if(sleepCounter == pets.size())
			{
				lastRoundPets.addAll(pets);
			}
		}
		for (int i = 0; i < pets.size(); i++) 
		{
			currentPet = pets.get(i);
			if (!currentPet.isAwake())
			{
				sleepingPets.add(currentPet);
				pets.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Calculates the random damage differential for each
	 * pet in the current pet list.
	 */
	private void calculateDifferenceInDamage()
	{
		for (int i = 0; i < pets.size(); i++) 
		{
			pets.get(i).calculateDamageDifference();
		}
	}
	
	/**
	 * Resets the current pets list by adding the pets 
	 * from sleep back into pets.
	 */
	public void resetPets()
	{
		while(!(pets.isEmpty()))
		{
			sleepingPets.add(pets.get(0));
			pets.remove(pets.get(0));
		}
		if(!lastRoundPets.isEmpty())
			lastRoundPets.clear();
		int nextPet = 0;
		
		while(!(sleepingPets.isEmpty()))
		{
			int nextPetObj = findNextPet(nextPet);
			pets.add(sleepingPets.get(nextPetObj));
			sleepingPets.remove(nextPetObj);
			nextPet++;
		}
		
		resetPetParts();
	}
	
	/**
	 * Next Pet to be placed in the pets list.
	 * @param indexVal
	 * @return
	 */
	private int findNextPet(int indexVal)
	{
		int indexFinder = 0;
		
		while(sleepingPets.get(indexFinder).getPlace() != indexVal)
		{
			indexFinder++;
		}
		
		return indexFinder;
	}
	
	/**
	 * Makes all cooldowns decrement and recharge a bit.
	 * @param pets2
	 */
	public void decrementCooldown(List<Playable> pets2)
	{
		for(int i = 0; i < pets.size(); i ++)
		{
			pets.get(i).decrementRechargeTimes();
		}
	}
	
	/**
	 * Resets the pet HP and recharges.
	 */
	private void resetPetParts()
	{
		for(int i = 0; i < pets.size(); i++)
		{
			pets.get(i).resetHp();
			pets.get(i).resetRecharge();
		}
	}
	
	/**
	 * Returns the list of sleeping pets.
	 * @return
	 */
	public List<Playable> getSleepingPets()
	{
		return this.sleepingPets;
	}
	
	public List<Playable> getLastRoundPets()
	{
		return this.lastRoundPets;
	}
	/**
	 *Lets observers know when Attack Event is Updated.
	 */
	public void notifyAttackEvent(List <AttackEvent> e)
	{
		setChanged();
		notifyObservers(e);
		clearChanged();
	}
	/**
	 *Lets observers know when Player Event is Updated.
	 */
	public void notifyPlayerEvent(List <PlayerEventInfo> e)
	{
		setChanged();
		notifyObservers(e);
		clearChanged();
	}
	/**
	 *Lets observers know when Round Event is Updated.
	 */
	public void notifyRoundEvent(RoundStartEvent e)
	{
		setChanged();
		notifyObservers(e);
		clearChanged();
	}
	/**
	 *Lets observers know when Fight Event is Updated.
	 */
	public void notifyFightEvent(FightStartEvent e)
	{
		setChanged();
		notifyObservers(e);
		clearChanged();
	}
}
