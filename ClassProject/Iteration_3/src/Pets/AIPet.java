package Pets;
import java.util.*;

import Game.Referee;
import interfaces.Playable;
/*
 * This class is a storage class for all data to due with AI pets. 
 * It has information on the pets hp, skill cooldowns, random damage taken and dealt,
 * whether the pet is sleeping or not, and how many fights the pet has won.
 * Author: Logan Connolly
 */
public class AIPet implements Playable
{
	private final int AI_SEED = 6666969; // hardcoded seed for AI random number generator
	private final int GENERATORBOUND = 5; // upper bound for the next int generator
	Random skillGenerator = new Random(AI_SEED);
	private double maxHP; // Max health points that the pets can have.
	private double HP; // Current health points.
	private double randomDamageTaken; // Damage that was taken last round.
	private double randomDamageDealt; // Damage that was dealt last round.
	private double differenceSumDamage;
	private double fullDamageTaken;
	private Skills skillToUse;
	private Skills guessedSkill;
	private String name; // Name of the pet .
	PetTypes type; // Type of pet this is.
	private int fightsWon = 0; // Number of Fights that this pet has won.
	private int petPlacement;
	private String petMasterName;
	private PlayerTypes playerType;
	private Skills[] skillsList = Skills.values();
	private Map<Skills, PetSkill> skillMap = new HashMap<Skills, PetSkill>();
	private Referee subject;
	
	/**
	 * Constructor for the AI Pets
	 * @param health
	 * @param type
	 * @param petName
	 * @param petMastername
	 * @param petNum
	 */
	public AIPet(double health, PetTypes type, String petName, String petMastername, int petNum)
	{
		this.maxHP = health;
		this.HP = health;
		this.type = type;
		this.name = petName;
		this.petMasterName = petMastername;
		this.petPlacement = petNum;
		this.playerType = PlayerTypes.IAMNOTAROBOT;
		this.skillMap.put(Skills.ROCK_THROW, new PetSkill(Skills.ROCK_THROW));
		this.skillMap.put(Skills.SCISSORS_POKE, new PetSkill(Skills.SCISSORS_POKE));
		this.skillMap.put(Skills.PAPER_CUT, new PetSkill(Skills.PAPER_CUT));
		this.skillMap.put(Skills.SHOOT_THE_MOON, new PetSkill(Skills.SHOOT_THE_MOON));
		this.skillMap.put(Skills.REVERSAL_OF_FORTUNE, new PetSkill(Skills.REVERSAL_OF_FORTUNE));
	}
	
	/**
	 * Generates an integer between 1 and 5
	 * @return
	 */
	private int getRandomNumber()
	{
		return skillGenerator.nextInt(GENERATORBOUND);
	}
	
	/**
	 * Returns index of where the pet is in the pet list
	 * @return int
	 */
	public int getPlace()
	{
		return petPlacement;
	}
	
	/**
	 * Returns the difference in damage the pet has taken
	 */
	public double getDamageDifference()
	{
		return differenceSumDamage;
	}
	
	/**
	 * Calculates the cumulative sum of the difference in damage the pet has taken
	 */
	public void calculateDamageDifference()
	{
		differenceSumDamage = differenceSumDamage + (randomDamageDealt  - randomDamageTaken);
	}
	
	/**
	 * Calculates the Hp of the pet after damage has been taken
	 * @param hpLoss
	 */
	public void takeDamage(double hpLoss)
	{
		this.fullDamageTaken = hpLoss;
		this.HP = HP - hpLoss;
	}
	
	/**
	 * Sets the random damage the pet took during round
	 */
	public void setRandomDamageTaken(double dmg)
	{
		this.randomDamageTaken = dmg;
	}
	
	/**
	 * Sets the random damage the pet did that round
	 */
	public void setRandomDamageDealt(double dmg)
	{
		this.randomDamageDealt = dmg;
	}
	
	/**
	 * Returns the random damage the took during the round
	 * @return randomDamageTaken
	 */
	public double getRandomDamageTaken()
	{
		return randomDamageTaken;
	}
	
	/**
	 * Returns the random damage dealt during the round
	 * @return randomDamageDealt
	 */
	public double getRandomDamageDealt()
	{
		return randomDamageDealt;
	}
	
	/**
	 * Retrieves a random number, finds the skill associated with the skill, and returns the skill
	 * if the skill is not recharging
	 * @Return Skill
	 */
	@Override
	public Skills chooseSkill() 
	{
		int skillIndex = getRandomNumber();
		boolean valid = true;
		while (valid)
		{
			if (getSkillRechargeTime(skillsList[skillIndex]) <= 0)
			{
				setRechargeTime(skillsList[skillIndex]);
				setSkill(skillsList[skillIndex]);
				return skillsList[skillIndex];
			}
			skillIndex = getRandomNumber();
		}
		return null;	
	}
	
	/**
	 * Returns the Max hp of the pet
	 * @return maxHp
	 */
	public double getMaxHp()
	{
		return this.maxHP;
	}
	
	/**
	 * Returns the name of the player associated with the pet
	 * @return petMasterName
	 */
	@Override
	public String getPlayerName() 
	{
		return petMasterName;
	}
	
	/**
	 * Returns the name of the pet
	 * @return name
	 */
	@Override
	public String getPetName() 
	{
		return name;
	}
	
	/**
	 * Returns the type of pet this pet is
	 * @return type
	 */
	@Override
	public PetTypes getPetType() 
	{
		return type;
	}
	
	/**
	 * Checks to see if the pet still has greater than 0 hp
	 * @return true if the pet has > 0 hp
	 */
	@Override
	public boolean isAwake() 
	{
		if (HP >= 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * Returns the current hp of the pet
	 * @return HP
	 */
	@Override
	public double getCurrentHp() 
	{
		return HP;
	}

	/**
	 * Calculates the hp of the pet based on the damage taken
	 */
	@Override
	public void updateHp(double hp) 
	{
		this.HP -= hp;
		this.fullDamageTaken = hp;
	}

	/**
	 * Resets the HP of the pet back to max
	 */
	@Override
	public void resetHp() 
	{
		this.HP = this.maxHP;
		this.differenceSumDamage = 0;
	}

	/**
	 * Sets the current hp of the pet
	 */
	@Override
	public void setCurrentHp(double currentHp) 
	{
		this.HP = currentHp;		
	}

	/**
	 * Generates a random number, find the associated skill, and guesses that skill for shoot the moon
	 * @return guessedSkill
	 */
	@Override
	public Skills getSkillPrediction() 
	{
		int skillIndex = getRandomNumber();
		setGuessedSkill(skillsList[skillIndex]);
		return guessedSkill;
	}

	/**
	 * Returns the recharge time of the given skill
	 * @return int
	 */
	@Override
	public int getSkillRechargeTime(Skills skill) 
	{
		return skillMap.get(skill).getSkillCooldown();
	}

	/**
	 * Gets the percent of health left
	 * @return double (ratio of health left)
	 */
	@Override
	public double calculateHpPercent() 
	{
		return (this.HP / maxHP);
	}
	
	/**
	 * Resets the recharge of all skills
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
	 * Resets the pet
	 */
	@Override
	public void reset() 
	{
		resetHp();
		resetRecharge();
	}

	/**
	 * Reduces the recharge of the the skills by one
	 */
	@Override
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
	 * Sets the recharge of a given skill
	 */
	@Override
	public void setRechargeTime(Skills skill) 
	{
		this.skillMap.get(skill).setRecharge(skill);
		
	}
	
	/**
	 * Returns the random + conditional damage taken by the pet
	 * @return fullDamageTaken
	 */
	@Override
	public double getFullDamageTaken()
	{
		return fullDamageTaken;
	}
	
	/**
	 * Sets the number of fights won by the pet to 0
	 */
	public void resetFightsWon()
	{
		fightsWon = 0;
	}
	
	/**
	 * Returns the number of fights won by the pet
	 * @return fightsWon
	 */
	public int getFightsWon()
	{
		return fightsWon;
	}
	
	/**
	 * Increases the fights won by one
	 */
	public void incrementFightsWon()
	{
		fightsWon++;
	}
	
	/**
	 * Returns a string representation of the pet
	 * @return string
	 */
	@Override
	public String toString(){
		return "Name: " + this.getPetName() + " Type: " + this.getPetType();
	}
	
	//TESTBED MAIN
	public static void main(String[] args)
    {
        AIPet pet1, pet2, pet3;
        pet1 = new AIPet(100d, PetTypes.POWER, "Scat", "john", 1);
        pet2 = new AIPet(100d, PetTypes.SPEED, "Man", "john", 2);
        pet3 = new AIPet(100d, PetTypes.INTELLIGENCE, "beep", "john", 3);
        System.out.println(pet1.chooseSkill());
        System.out.println(pet1.chooseSkill());
        System.out.println(pet2.chooseSkill());
        System.out.println(pet2.chooseSkill());
        System.out.println(pet3.chooseSkill());
        System.out.println(pet3.chooseSkill());
        pet1.takeDamage(66.6d);
        pet2.takeDamage(42.0d);
        pet3.takeDamage(69.69d);
        pet1.incrementFightsWon();
        pet1.incrementFightsWon();
        pet2.incrementFightsWon();
        System.out.println(pet1.getCurrentHp());
        System.out.println(pet2.getCurrentHp());
        System.out.println(pet3.getCurrentHp());
        System.out.println(pet1.getFightsWon());
        System.out.println(pet2.getFightsWon());
        System.out.println(pet3.getFightsWon());
    }

	/**
	 * Checks to see if a skill is recharging
	 * @return true if skill is recharging
	 */
	@Override
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
	 * Returns the skill the pet choose to use
	 * @return skillToUse
	 */
	@Override
	public Skills getSkill() {
		return skillToUse;
	}

	/**
	 * Stores the pets skill choice
	 */
	@Override
	public void setSkill(Skills skills) {
		skillToUse = skills;
		
	}

	/**
	 * Stores the guessed skill for shoot the moon
	 */
	@Override
	public void setGuessedSkill(Skills skills) {
		guessedSkill = skills; 
	}

	/**
	 * TBD
	 */
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		//TODO Probably something eventually 
		// (Doug laughs maniacally)
	}

	/**
	 * Returns the type of player this pet is being controlled by
	 * @return playerType
	 */
	@Override
	public PlayerTypes getPlayerType() {
		return playerType;
	}

	/**
	 * Sets the type of player this pet is controlled by
	 */
	@Override
	public void setPlayerType(PlayerTypes type) 
	{
		playerType = type;
	}

}
