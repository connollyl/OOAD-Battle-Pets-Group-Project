package Pets;
import java.util.Random;

/**
 * This class allows the creation of and use of
 * the power pet type.
 * Author: Logan Connolly & Aidan Gengler
 */
public class Power extends Pet
{
	private final double FIVE = 5;		// Damage multiplier and generates a random number between 0 and 5.
	
	/**
	 * Constructor for Power Pet, having Pet's attributes passed down.
	 * @param health
	 * @param name
	 */
	public Power(double health, String name)
	{
		super(health, name);
	}
	/**
	 * Returns the petType
	 */
	public String getType()
	{
		return "Power";
	}
	
	/**
	 * Decides which skill was chosen by the pet, and
	 * calls the certain skill function.
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
	 * Uses the power specific version of the skill rock throw
	 * @param enemy
	 * @param dmg
	 */
	public void rockThrow(Pet enemy, double dmg)
	{
		if(enemy.getSkill() == Skills.SCISSORS_POKE)
		{
			enemy.calculateHealth(dmg * FIVE);
		}
		else
		{
			enemy.calculateHealth(dmg);
		}
	}
	
	 /**
	   * Uses the power specific version of the skill scissors poke
	   * @param enemy
	   * @param dmg
	   */
	public void scissorsPoke(Pet enemy, double dmg)
	{
		if(enemy.getSkill() == Skills.PAPER_CUT)
		{
			enemy.calculateHealth(dmg * FIVE);
		}
		else
		{
			enemy.calculateHealth(dmg);
		}
	}
	
	  /**
	   * Uses the power specific version of the skill paper cut
	   * @param enemy
	   * @param dmg
	   */
	public void paperCut(Pet enemy, double dmg)
	{
		if(enemy.getSkill() == Skills.ROCK_THROW)
		{
			enemy.calculateHealth(dmg * FIVE);
		}
		else
		{
			enemy.calculateHealth(dmg);
		}
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