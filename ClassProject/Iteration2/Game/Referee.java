package Game;
import java.util.ArrayList;
import java.util.List;

import Pets.Pet;
import Pets.Skills;
import UI.RoundUI;

/*
 * This class mediates between the Pets, calculator, and RNG
 * classes. It also maintains the state of each pet.
 * Author: Aidan Gengler
 */
public class Referee
{
	private List<Pet> pets;
	private List<Pet> sleepingPets;
	private RNG randomizer;
	private Calculator calc;
	private RoundUI rui = new RoundUI();
	
	/**
	 * Constructor for the referee class. Initializes the lists and 
	 * seed for the RNG and pets.
	 * @param petList
	 * @param seed
	 */
	public Referee(List<Pet> petList, long seed)
	{
		pets = petList;
		sleepingPets = new ArrayList<>();
		randomizer = new RNG(seed);
		calc = new Calculator();
	}

	/** 
	 * Calculates the damage of the current round.
	 */
	public void singleDamagePhase() 
	{
		
		decrementCooldown(pets);
		Pet currentPet, nextPet;
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
			nextPet.takeDamage(damage);
		}
		rui.displayDamageAndHP(pets);
		
		checkSleepers();
		calculateDifferenceInDamage();
		
		// End Of Damage Phase
	}
	
	/**
	 * Checks for sleeping pets, and sends them to the 
	 * sleeping pets list.
	 */
	private void checkSleepers()
	{
		Pet currentPet;
		for (int i = 0; i < pets.size(); i++) 
		{
			currentPet = pets.get(i);
			if (currentPet.isSleeping())
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
	 * @param p
	 */
	public void decrementCooldown(List<Pet> p)
	{
		for(int i = 0; i < pets.size(); i ++)
		{
			pets.get(i).decrementRecharge();
		}
	}
	
	/**
	 * Resets the pet HP and recharges.
	 */
	private void resetPetParts()
	{
		for(int i = 0; i < pets.size(); i++)
		{
			pets.get(i).resetHP();
			pets.get(i).resetRecharge();
		}
	}
	
	/**
	 * Returns the list of sleeping pets.
	 * @return
	 */
	public List<Pet> getSleepingPets()
	{
		return this.sleepingPets;
	}
}
