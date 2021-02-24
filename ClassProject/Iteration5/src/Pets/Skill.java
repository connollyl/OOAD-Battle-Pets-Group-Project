package Pets;

/**
 * This class does logic for the cooldowns of the skills that a pet.
 * Author: Aidan
 */
public class Skill
{
	private Skills skill;
	private int currentRechargeTime;
	private boolean isOnCD;
	
	/**
	 * Creates a skill for a pet
	 * @param skill
	 */
	public Skill(Skills skill)
	{
		this.skill = skill;
		this.currentRechargeTime = 0;
	}
	
	/**
	 * Returns the cooldown of the skill
	 * @return currentRechargeTime
	 */
	public int getSkillCooldown()
	{
		return this.currentRechargeTime;
	}
	
	/**
	 * Checks to see if the skill is on cooldown
	 * @return true if the skill has a cooldown greater than zero
	 */
	public boolean isOnCD()
	{
		if(this.currentRechargeTime > 0)
			return true;
		return false;
	}
	
	/**
	 * Lowers the cooldown of the skill by one if cooldown is greater than zero
	 */
	public void decrementRecharge()
	{
		if(currentRechargeTime > 0)
		{
			currentRechargeTime--;
		}
	}
	
	/**
	 * Sets the cooldown based on which skill is used
	 * @param skill used
	 */
	public void setRechargeTime(int time)
	{
		this.currentRechargeTime = time;
	}
	
	public int getRechargeTime()
	{
		return this.currentRechargeTime;
	}
	
	/**
	 * Resets the cooldown of the skill
	 */
	public void resetRecharge()
	{
		this.currentRechargeTime = 0;
	}
}
