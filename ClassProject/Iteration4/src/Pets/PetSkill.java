package Pets;

/**
 * This class does logic for the cooldowns of the skills that a pet.
 * Author: Aidan
 */
public class PetSkill
{
	private final int CD_BASIC_SKILLS = 2; // Sets cool down to 2
	private final int CD_ADVANCED_SKILLS = 7; // Number of rounds that must go by before use of skills 4 and 5.
	private Skills skill;
	private int currentRechargeTime;
	private boolean isOnCD;
	
	/**
	 * Creates a skill for a pet
	 * @param skill
	 */
	public PetSkill(Skills skill)
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
	public void setRecharge(Skills skill)
	{
		if(skill == Skills.SHOOT_THE_MOON || skill == Skills.REVERSAL_OF_FORTUNE)
		{
			this.currentRechargeTime = CD_ADVANCED_SKILLS;
		}
		else
		{
			this.currentRechargeTime = CD_BASIC_SKILLS;
		}
	}
	
	/**
	 * Resets the cooldown of the skill
	 */
	public void resetRecharge()
	{
		this.currentRechargeTime = 0;
	}
}
