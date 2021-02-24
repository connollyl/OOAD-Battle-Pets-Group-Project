package Pets;

import java.util.Map;

/**
 * This class is the abstract pet class that
 * power, speed, and intelligence use for
 * creating the different pet types.
 * Author: Logan Connolly & Aidan Gengler & Andrew Cummings
 */
public abstract class Pet
{
	private final int CD_BASIC_SKILLS = 2; // Sets cool down to 2\
	// remove constants
	private final int FIVE = 5; // Size of array for skill coolDowns.
	private final int SIX = 6; // Number of rounds that must go by before use of skills 4 and 5.
	private double maxHP; // Max health points that the pets can have.
	private double HP; // Current health points.
	private double damage; // Damage that was dealt last round.
	private String name; // Name of the pet.
	private Skills skillToUse; // Skill that is being used during next attack.
	private int fightsWon = 0;

	private int[] coolDowns = new int[FIVE]; // Tracks cool downs of all five attacks.
	
	private Map<Skills, PetSkill> skillMap;
	
	/**
	 * Creates the pet object and initializes maxHP, HP, and name
	 * 
	 * @param health
	 * @param petName
	 */
	public Pet(double health, String petName)
	{
		this.maxHP = health;
		this.HP = health;
		this.name = petName;
	}
	
	/**
	 * Calculates the health lost durning battle.
	 * 
	 * @param hpLoss
	 */
	public void calculateHealth(double hpLoss)
	{
		this.setDamage(hpLoss);
		this.HP = HP - hpLoss;
	}
	
	/**
	 * Checks if the current skill is under cooldown.
	 * 
	 * @param skill
	 * @return true for a cooldown, false for no cooldown
	 */
	public boolean checkCoolDown(Skills skill)
	{
		if (coolDowns[skill.getSkillIndex() - 1] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Sets a specific skill to a cooldown. 1 tick for the first three skills, and 6
	 * ticks for the last 2 skills.
	 * 
	 * @param skill
	 */
	public void setCoolDown(Skills skill)
	{
		if ((skill != Skills.SHOOT_THE_MOON) && (skill != Skills.REVERSAL_OF_FORTUNE))
		{
			coolDowns[skill.getSkillIndex() - 1] = CD_BASIC_SKILLS;
		}
		else
		{
			coolDowns[skill.getSkillIndex() - 1] = SIX;
		}
	}
	
	/**
	 * Increments the cooldown to remove ticks for the skills. Once ticks reach
	 * zero, the skill is able to be used.
	 */
	public void coolDownDecrement()
	{
		for (int i = 0; i < coolDowns.length; i++)
		{
			if (coolDowns[i] > 0)
			{
				coolDowns[i] = coolDowns[i] - 1;
			}
		}
	}
	
	/**
	 * Returns the maximum health allowed.
	 * 
	 * @return maxHP
	 */
	public double getMaxHP()
	{
		return this.maxHP;
	}
	
	/**
	 * Returns the current health of the pet.
	 * 
	 * @return HP
	 */
	public double getHP()
	{
		return this.HP;
	}
	
	/**
	 * Sets the value of health for the pet.
	 * 
	 * @param HP
	 */
	public void setHP(double HP) {
		this.HP = HP;
	}
	
	/**
	 * Returns the name of the pet.
	 * @return name
	 */
	public String getName()
	{
		return this.name;
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
	 * Sets the current skill for use in next round
	 * @param skill
	 * @return true if skill is on cool down, 
	 * false if it is not.
	 */
	public boolean setSkill(Skills skill)
	{
		if (!checkCoolDown(skill))
		{
			skillToUse = skill;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Obtains the current skill 
	 * @return the skill to be used in the
	 * current round
	 */
	public Skills getSkill()
	{
		return skillToUse;
	}
	
	/**
	 * Checks if the current pet has less than 0 health.
	 * @return true if knocked out,
	 * false if not.
	 */
	public boolean isKO()
	{
		if (HP <= 0)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the damage that was taken to damage for use in
	 * outputting the round's damage total.
	 * @param dmg
	 */
	public void setDamage(double dmg)
	{
		damage = dmg;
	}
	
	/**
	 * Returns the damage taken this round.
	 * @return damage
	 */
	public double getDamage()
	{
		return damage;
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
	 * Returns the name and type of this pet.
	 * @return name and type
	 */
	@Override
	public String toString(){
		return "Name: " +this.getName() + " Type: " + this.getType();
	}
	public abstract String getType();
	
	public abstract void useSkill(Skills skill, Pet enemy);
	
	public abstract void rockThrow(Pet enemy, double dmg);

	public abstract void scissorsPoke(Pet enemy, double dmg);

	public abstract void paperCut(Pet enemy, double dmg);

	public abstract void shootTheMoon(Pet enemy, double dmg);

	public abstract void reversalOfFortune(Pet enemy, double dmg);
}