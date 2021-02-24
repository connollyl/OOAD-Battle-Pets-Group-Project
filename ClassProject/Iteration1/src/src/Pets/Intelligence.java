package Pets;

import java.util.Random;
/**
 * This class allows the creation of and use of the 
 * Intelligence pet type.
 * Author: Logan Connolly & Aidan Gengler
 */
public class Intelligence extends Pet
{
	private final double FIVE = 5;		// Used to generate a random number between 0 and 5.
	private final double TWO = 2;		// Used as conditional damage
	private final double THREE = 3;		// Used as conditional damage
	
  /**
   * Creates the Intelligence pet object and initializes maxHP, HP and name
   * @param health
   * @param n
   */
	public Intelligence(double health, String n)
	{
		super(health, n);
	}
	
   /**
	* Returns the type of the pet
	* @return Intelligence
	*/
	public String getType()
	{
		return "Intelligence";
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
	 * Uses the Intelligence specific version of the skill rock throw
	 * @param enemy
	 * @param dmg
	 */
	public void rockThrow(Pet enemy, double dmg)
	{
		if (enemy.checkCoolDown(Skills.ROCK_THROW))
		{
			enemy.calculateHealth(TWO);
		}
		if (enemy.checkCoolDown(Skills.SCISSORS_POKE))
		{
			enemy.calculateHealth(THREE);
		}
		if (enemy.checkCoolDown(Skills.SHOOT_THE_MOON))
		{
			enemy.calculateHealth(TWO);
		}

		enemy.calculateHealth(dmg);
	}
	
	/**
	 * Uses the Intelligence specific version of the skill scissors poke
	 * @param enemy
	 * @param dmg
	 */
	public void scissorsPoke(Pet enemy, double dmg)
	{
		if (enemy.checkCoolDown(Skills.SCISSORS_POKE))
		{
			enemy.calculateHealth(TWO);
		}
		if (enemy.checkCoolDown(Skills.PAPER_CUT))
		{
			enemy.calculateHealth(THREE);
		}
		if (enemy.checkCoolDown(Skills.SHOOT_THE_MOON))
		{
			enemy.calculateHealth(TWO);
		}

		enemy.calculateHealth(dmg);
	}
	
	/**
	 * Uses the Intelligence specific version of the skill paper cut
	 * @param enemy
	 * @param dmg
	 */
	public void paperCut(Pet enemy, double dmg)
	{
		if (enemy.checkCoolDown(Skills.ROCK_THROW))
		{
			enemy.calculateHealth(THREE);
		}
		if (enemy.checkCoolDown(Skills.PAPER_CUT))
		{
			enemy.calculateHealth(TWO);
		}
		if (enemy.checkCoolDown(Skills.SHOOT_THE_MOON))
		{
			enemy.calculateHealth(TWO);
		}

		enemy.calculateHealth(dmg);
	}
	/**
	 * TBD
	 */
	public void shootTheMoon(Pet enemy, double dmg)
	{
		
	}
	/**
	 * TBD
	 */
	public void reversalOfFortune(Pet enemy, double dmg)
	{
		
	}
}