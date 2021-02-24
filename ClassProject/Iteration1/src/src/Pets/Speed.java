package Pets;

import java.util.Random;
/**
 * This class allows the creation of and use of
 * the speed pet type.
 * Author: Logan Connolly & Aidan Gengler
 */
public class Speed extends Pet
{
	private final double TFIVE = 0.25;	// Used for obtaining 25% of the max health.
	private final double SFIVE = 0.75;	// Used for obtaining 75% of the max health.
	private final double FIVE = 5;		// Used to generate a random number between 0 and 5.
	private final double TEN = 10;		// Used to add the conditional damage.
	
	/**
	 * Creates the Speed pet object and initializes maxHP, HP and name
	 * @param health
	 * @param n
	 */
	public Speed(double health, String n)
	{
		super(health, n);
	}
	
	/**
	 * Returns the type of the pet
	 * @return Speed
	 */
	public String getType()
	{
		return "Speed";
	}
	
	/**
	 * Uses the pet specific skill given a skill object and
	 * skill target
	 * @param skill
	 * @param enemy
	 */
	public void useSkill(Skills skill, Pet enemy)
	{
		Random randNum = new Random();
		double dmg = randNum.nextDouble() * FIVE;
		
		if (skill == Skills.ROCK_THROW)
		{
			this.rockThrow(enemy, dmg);
		}
		else if (skill == Skills.SCISSORS_POKE)
		{
			this.scissorsPoke(enemy, dmg);
		}
		else if (skill == Skills.PAPER_CUT)
		{
			this.paperCut(enemy, dmg);
		}
		else if (skill == Skills.SHOOT_THE_MOON)
		{
			this.shootTheMoon(enemy, dmg);
		}
		else if (skill == Skills.REVERSAL_OF_FORTUNE)
		{
			this.reversalOfFortune(enemy, dmg);
		}
		
		this.coolDownDecrement();
		this.setCoolDown(skill);

	}
	
	/**
	 * Uses the speed specific version of the skill rock throw
	 * @param enemy
	 * @param dmg
	 */
	public void rockThrow(Pet enemy, double dmg)
	{
		if((enemy.getHP() >= (enemy.getMaxHP() * SFIVE)) && 
				((enemy.getSkill() == Skills.SCISSORS_POKE) || 
						(enemy.getSkill() == Skills.PAPER_CUT)))
		{
			enemy.calculateHealth(dmg + TEN);
		}
		else
		{
			enemy.calculateHealth(dmg);
		}
	}
	
	/**
	   * Uses the speed specific version of the skill scissors poke
	   * @param enemy
	   * @param dmg
	   */
	public void scissorsPoke(Pet enemy, double dmg)
	{
		if((enemy.getHP() < (enemy.getMaxHP() * SFIVE)) && 
				(enemy.getHP() >= (enemy.getMaxHP() * TFIVE)) &&
				((enemy.getSkill() == Skills.ROCK_THROW) || 
						(enemy.getSkill() == Skills.PAPER_CUT)))
		{
			enemy.calculateHealth(dmg + TEN);
		}
		else
		{
			enemy.calculateHealth(dmg);
		}
	}
	
	  /**
	   * Uses the speed specific version of the skill paper cut
	   * @param enemy
	   * @param dmg
	   */
	public void paperCut(Pet enemy, double dmg)
	{
		if((enemy.getHP() < (enemy.getMaxHP() * TFIVE)) && 
				((enemy.getSkill() == Skills.ROCK_THROW) || 
						(enemy.getSkill() == Skills.SCISSORS_POKE)))
		{
			enemy.calculateHealth(dmg + TEN);
		}
		else
		{
			enemy.calculateHealth(dmg);
		}
	}
	
	public void shootTheMoon(Pet enemy, double dmg)
	{
		
	}
	
	public void reversalOfFortune(Pet enemy, double dmg)
	{
		
	}
}