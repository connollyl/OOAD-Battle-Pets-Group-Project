package Pets;

/**
 * This class is a storage class for all data to due with pets. 
 * It has information on the pets hp, skill cooldowns, random damage taken and dealt,
 * whether the pet is sleeping or not, and how many fights the pet has won.
 * Author: Logan Connolly & Aidan Gengler
 */

import java.util.Map;
import java.util.Observable;
import java.util.Set;

import Events.AttackEvent;
import Events.AttackEvent.AttackEventBuilder;
import Events.BaseEvent;
import Events.EventTypes;
import Game.Referee;
import UI.RoundUI;
import interfaces.Playable;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author garzal
 *
 */
public class Pet implements Playable
{
	private double maxHP; // Max health points that the pets can have.
	private double HP; // Current health points.
	private double randomDamageTaken; // Damage that was taken last round.
	private double randomDamageDealt; // Damage that was dealt last round.
	private double differenceSumDamage;
	private double fullDamageTaken;
	private String name; // Name of the pet .
	PetTypes type; // Type of pet this is.
	private Skills skillToUse; // Skill that is being used during next attack.
	private Skills guessedSkill;
	private int fightsWon = 0; // Number of Fights that this pet has won.
	private int petPlacement;
	private PlayerTypes playerType;
	private String petMasterName; // "Player" name
	private RoundUI rui = new RoundUI();
	
	private Map<Skills, PetSkill> skillMap = new HashMap<Skills, PetSkill>();
	
	/**
	 * Creates the pet object and initializes maxHP, HP, and name
	 * 
	 * @param health
	 * @param petName
	 */
	public Pet(double health, PetTypes type, String petName, String pMN, int petNum)
	{
		this.maxHP = health;
		this.HP = health;
		this.type = type;
		this.name = petName;
		this.playerType = PlayerTypes.TOPOFTHEFOODCHAIN;
		this.petMasterName = pMN;
		this.petPlacement = petNum;
		this.skillMap.put(Skills.ROCK_THROW, new PetSkill(Skills.ROCK_THROW));
		this.skillMap.put(Skills.SCISSORS_POKE, new PetSkill(Skills.SCISSORS_POKE));
		this.skillMap.put(Skills.PAPER_CUT, new PetSkill(Skills.PAPER_CUT));
		this.skillMap.put(Skills.SHOOT_THE_MOON, new PetSkill(Skills.SHOOT_THE_MOON));
		this.skillMap.put(Skills.REVERSAL_OF_FORTUNE, new PetSkill(Skills.REVERSAL_OF_FORTUNE));
	}

	/**
	 * Returns the pets placement in the list
	 * @return int for the index
	 */
	public int getPlace()
	{
		return petPlacement;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.Playable#getSkill()
	 */
	public Skills getSkill()
	{
		return skillToUse;
	}
	
	/**
	 * Returns the pet type of the pet
	 * @return petType
	 */
	public PetTypes getPetType()
	{
		return type;
	}
	
	/**
	 * Returns the sum of the difference in damage between this pet and the pet that hit it
	 * @return a sum of damage differences as a double.
	 */
	public double getDamageDifference()
	{
		return differenceSumDamage;
	}
	
	/**
	 * Calculates the sum of the difference in damage between this pet and the pet that hit it.
	 */
	public void calculateDamageDifference()
	{
		differenceSumDamage = differenceSumDamage + (randomDamageDealt  - randomDamageTaken);
	}
	
	/**
	 * Sets the damage that was taken to damage for use in
	 * outputting the round's damage total.
	 * @param dmg
	 */
	public void setRandomDamageTaken(double dmg)
	{
		this.randomDamageTaken = dmg;
	}
	
	/**
	 * Sets the random damage dealt by this class.
	 * @param dmg
	 */
	public void setRandomDamageDealt(double dmg)
	{
		this.randomDamageDealt = dmg;
	}
	
	/**
	 * Returns the damage taken this round.
	 */
	public double getRandomDamageTaken()
	{
		return randomDamageTaken;
	}
	
	/**
	 * Returns the random damage dealt by this pet.
	 * @return randomDamageDealt
	 */
	public double getRandomDamageDealt()
	{
		return randomDamageDealt;
	}

	/**
	 * Checks if the current skill is under recharge.
	 * 
	 * @param skill
	 * @return true for a recharge, false for no recharge
	 */
	public boolean checkRecharge(Skills skill)
	{
		if (skillMap.get(skill).isOnCD())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the cooldown of a given skill.
	 * @param skill
	 * @return int as the cooldown of the skill
	 */
	public int getSkillRechargeTime(Skills skill)
	{
		return skillMap.get(skill).getSkillCooldown();
	}
	
	/**
	 * Returns the max hp of the pet as set in the battle settings.
	 * @return
	 */
	public double getMaxHp()
	{
		return this.maxHP;
	}
	
	/**
	 * Returns the current health of the pet.
	 * 
	 * @return HP
	 */
	public double getCurrentHp()
	{
		return this.HP;
	}
	
	/**
	 * Sets the value of health for the pet.
	 * 
	 * @param HP
	 */
	public void setCurrentHp(double HP) 
	{
		this.HP = HP;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.Playable#calculateHpPercent()
	 */
	public double calculateHpPercent()
	{
		return (this.HP / maxHP);
	}
	
	/**
	 * Sets the pets current hp to the max hp and resets the sum of difference in damage
	 */
	public void resetHp()
	{
		this.HP = this.maxHP;
		this.differenceSumDamage = 0;
	}
	/**
	 * Returns the name of the pet.
	 * @return name
	 */
	public String getPetName()
	{
		return this.name;
	}
	public String getPlayerName()
	{
		return petMasterName;
	}
	/**
	 * Sets the value of name to whatever the user wants.
	 * @param n
	 */
	public void setName(String n)
	{
		this.name = n;
	}
	
	/**
	 * Sets a specific skill to a cooldown. 1 tick for the first three skills, and 6
	 * ticks for the last 2 skills.
	 * 
	 * @param skill
	 */
	public void setRechargeTime(Skills skill)
	{
		this.skillMap.get(skill).setRecharge(skill);
	}
	
	/**
	 * Increments the cooldown to remove ticks for the skills. Once ticks reach
	 * zero, the skill is able to be used.
	 */
	public void decrementRechargeTimes()
	{
		Iterator<Map.Entry<Skills, PetSkill>> iter = skillMap.entrySet().iterator();
		
		while (iter.hasNext()) 
		{
			Map.Entry<Skills, PetSkill> skillCooldown = (Map.Entry<Skills, PetSkill>)iter.next();
			((PetSkill) skillCooldown.getValue()).decrementRecharge();
		}
	}
	
	/**
	 * Resets the cooldowns of all the pets skills
	 */
	public void resetRecharge()
	{
		Iterator<Map.Entry<Skills, PetSkill>> iter = skillMap.entrySet().iterator();
		
		while (iter.hasNext()) 
		{
			Map.Entry<Skills, PetSkill> skillCooldown = (Map.Entry<Skills, PetSkill>)iter.next();
			((PetSkill) skillCooldown.getValue()).resetRecharge();
		}
	}
	
	/**
	 * Sets the current skill for use in next round
	 * @param skill
	 * @return true if skill is on cool down, 
	 * false if it is not.
	 */
	public void setSkill(Skills skill)
	{
		skillToUse = skill; 
		if(skill != null)
		{
			this.setRechargeTime(skill);
		}
	}
	
	/**
	 * Obtains the current skill 
	 * @return the skill to be used in the
	 * current round
	 */
	@Override
	public Skills chooseSkill()
	{
		rui.skillSelect(this);
		return skillToUse;
	}
	
	/**
	 * Takes a skill as a parameter and sets that as the choosen skill for this pet, used with GUI
	 * @param skills
	 */
	public void guiChooseSkill(Skills skills)
	{
		skillToUse = skills;
		this.setRechargeTime(skills);
	}
	
	/**
	 * Sets the skill guessed that the next pet will used as a result of using Shoot the Moon
	 * @param skill
	 */
	public void setGuessedSkill(Skills skill)
	{
		guessedSkill = skill; 
	}
	
	/**
	 * Returns the skill guessed from Shoot the Moon
	 * @return guessedSkill
	 */
	public Skills getSkillPrediction()
	{
		return guessedSkill;
	}
	/**
	 * Checks if the current pet has less than 0 health.
	 * @return true if knocked out,
	 * false if not.
	 */
	public boolean isAwake()
	{
		if (HP >= 0)
		{
			return true;
		}
		
		return false;
	}
		
	/**
	 * Increments the victories for this pet.
	 */
	public void incrementFightsWon()
	{
		fightsWon++;
	}
	
	/**
	 * Returns the number of victories.
	 * @return fightsWon
	 */
	public int getFightsWon()
	{
		return fightsWon;
	}

	/**
	 * Resets the fights won by the pet.
	 */
	public void resetFightsWon()
	{
		fightsWon = 0;
	}
	
	/**
	 * Returns the sum of the random and conditional damage taken by the pet.
	 * @return
	 */
	public double getFullDamageTaken()
	{
		return fullDamageTaken;
	}
	
	/* 
	 * @see interfaces.Playable#reset()
	 */
	@Override
	public void reset()
	{
		resetHp();
		resetRecharge();
	}
	
	/**
	 * Returns the name and type of this pet.
	 * @return name and type
	 */
	@Override
	public String toString(){
		return "Name: " + this.getPetName() + " Type: " + this.getPetType();
	}

	/* 
	 * @see interfaces.Playable#updateHp(double)
	 */
	@Override
	public void updateHp(double hp) 
	{
		this.HP -= hp;
		this.fullDamageTaken = hp;
	}

	/* (non-Javadoc)
	 * @see interfaces.Playable#setPlayerType(Pets.PlayerTypes)
	 */
	@Override
	public void setPlayerType(PlayerTypes type) 
	{
		playerType = type;
	}

	/* (non-Javadoc)
	 * @see interfaces.Playable#getPlayerType()
	 */
	@Override
	public PlayerTypes getPlayerType() 
	{
		return playerType;
	}

	/*
	 * Updates the Observers about state changes.
	 */
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		//TODO Probably something eventually 
		// (Doug laughs maniacally)
	}

}