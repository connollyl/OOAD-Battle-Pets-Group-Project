package Game;
import Pets.Pet;
import Pets.PetTypes;
import Pets.Skills;
import interfaces.Calculatable;

/*
 * This class contains all calculation logic
 * for all attacks.
 * Author: Aidan Gengler
 */

public class Calculator implements Calculatable 
{	
	private final double STMVAL = 20;
	private final double POWMULT = 5;
	private final double SPEEDADD = 10;
	private final double SPEEDVALSF = 0.75;
	private final double SPEEDVALTF = 0.25;
	private final double INTELADDTWO = 2;
	private final double INTELADDTHREE = 3;
	
	/**
	 * Finds the type of pet that is attacking and sends data on to a 
	 * specialized method for it.
	 */
	@Override
	public double calculateDamage(Pet first, Pet second, double randDamage)
	{
		if(first.getType() == PetTypes.POWER)
		{
			return calcPowerDamage(first, second, randDamage);
		}
		else if(first.getType() == PetTypes.SPEED)
		{
			return calcSpeedDamage(first, second, randDamage);
		}
		else
		{
			return calcIntelligenceDamage(first, second, randDamage);
		}
	}
	
	/**
	 * Finds the corresponding skill used by the attacking pet, and
	 * sends data to corresponding method.
	 */
	public double calcPowerDamage(Pet first, Pet second, double randDamage)
	{
		if(first.chooseSkill() == Skills.ROCK_THROW)
		{
			return calcPowerRTDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.SCISSORS_POKE)
		{
			return calcPowerSPDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.PAPER_CUT)
		{
			return calcPowerPCDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.SHOOT_THE_MOON)
		{
			return calcShootTheMoon(first, second, randDamage);
		}
		else
		{
			return calcReversalOfFortune(first, second, randDamage);
		}
	}
	
	/**
	 * Finds the corresponding skill used by the attacking pet, and
	 * sends data to corresponding method.
	 */
	public double calcSpeedDamage(Pet first, Pet second, double randDamage)
	{
		if(first.chooseSkill() == Skills.ROCK_THROW)
		{
			return calcSpeedRTDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.SCISSORS_POKE)
		{
			return calcSpeedSPDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.PAPER_CUT)
		{
			return calcSpeedPCDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.SHOOT_THE_MOON)
		{
			return calcShootTheMoon(first, second, randDamage);
		}
		else
		{
			return calcReversalOfFortune(first, second, randDamage);
		}
	}
	
	/**
	 * Finds the corresponding skill used by the attacking pet, and
	 * sends data to corresponding method.
	 */
	public double calcIntelligenceDamage(Pet first, Pet second, double randDamage)
	{
		if(first.chooseSkill() == Skills.ROCK_THROW)
		{
			return calcIntelligenceRTDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.SCISSORS_POKE)
		{
			return calcIntelligenceSPDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.PAPER_CUT)
		{
			return calcIntelligencePCDamage(first, second, randDamage);
		}
		else if(first.chooseSkill() == Skills.SHOOT_THE_MOON)
		{
			return calcShootTheMoon(first, second, randDamage);
		}
		else
		{
			return calcReversalOfFortune(first, second, randDamage);
		}
	}
	
	/**
	 * Calculates shoot the moon. 
	 */
	public double calcShootTheMoon(Pet first, Pet second, double randDamage)
	{
		if (first.getGuessedSkill() == second.chooseSkill())
		{
			return (randDamage + STMVAL);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates Reversal of fortune.
	 */
	public double calcReversalOfFortune(Pet first, Pet second, double randDamage)
	{
		double damage = randDamage + first.getDamageDifference();
		return damage;
	}
	
	/**
	 * Calculates power's version of rock throw.
	 */
	public double calcPowerRTDamage(Pet first, Pet second, double randDamage)
	{
		if(second.chooseSkill() == Skills.SCISSORS_POKE)
		{
			return (randDamage * POWMULT);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates power's version of scissor poke.
	 */
	public double calcPowerSPDamage(Pet first, Pet second, double randDamage)
	{
		if(second.chooseSkill() == Skills.PAPER_CUT)
		{
			return (randDamage * POWMULT);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates power's version of paper cut.
	 */
	public double calcPowerPCDamage(Pet first, Pet second, double randDamage)
	{
		if(second.chooseSkill() == Skills.ROCK_THROW)
		{
			return (randDamage * POWMULT);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates Speed's version of rock throw.
	 */
	public double calcSpeedRTDamage(Pet first, Pet second, double randDamage)
	{
		if((second.getHP() >= (second.getMaxHP() * SPEEDVALSF)) && 
				((second.chooseSkill() == Skills.SCISSORS_POKE) || 
						(second.chooseSkill() == Skills.PAPER_CUT)))
		{
			return (randDamage + SPEEDADD);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates Speed's version of scissor poke.
	 */
	public double calcSpeedSPDamage(Pet first, Pet second, double randDamage)
	{
		if((second.getHP() < (second.getMaxHP() * SPEEDVALSF)) && 
				(second.getHP() >= (second.getMaxHP() * SPEEDVALTF)) &&
				((second.chooseSkill() == Skills.ROCK_THROW) || 
						(second.chooseSkill() == Skills.PAPER_CUT)))
		{
			return (randDamage + SPEEDADD);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates Speed's version of paper cut.
	 */
	public double calcSpeedPCDamage(Pet first, Pet second, double randDamage)
	{
		if((second.getHP() < (second.getMaxHP() * SPEEDVALTF)) && 
				((second.chooseSkill() == Skills.ROCK_THROW) || 
						(second.chooseSkill() == Skills.SCISSORS_POKE)))
		{
			return (randDamage + SPEEDADD);
		}
		else
		{
			return randDamage;
		}
	}
	
	/**
	 * Calculates Intelligence's version of rock throw.
	 */
	public double calcIntelligenceRTDamage(Pet first, Pet second, double randDamage)
	{
		double damage = randDamage;
		
		if (second.checkRecharge(Skills.ROCK_THROW))
		{
			damage += INTELADDTWO;
		}
		if (second.checkRecharge(Skills.SCISSORS_POKE))
		{
			damage += INTELADDTHREE;
		}
		if (second.checkRecharge(Skills.SHOOT_THE_MOON))
		{
			damage += INTELADDTWO;
		}
		
		return damage;
	}
	
	/**
	 * Calculates Intelligence's version of scissor poke.
	 */
	public double calcIntelligenceSPDamage(Pet first, Pet second, double randDamage)
	{
		double damage = randDamage;
		
		if (second.checkRecharge(Skills.SCISSORS_POKE))
		{
			damage += INTELADDTWO;
		}
		if (second.checkRecharge(Skills.PAPER_CUT))
		{
			damage += INTELADDTHREE;
		}
		if (second.checkRecharge(Skills.SHOOT_THE_MOON))
		{
			damage += INTELADDTWO;
		}
		
		return damage;
	}
	
	/**
	 * Calculates Intelligence's version of paper cut.
	 */
	public double calcIntelligencePCDamage(Pet first, Pet second, double randDamage)
	{
		double damage = randDamage;
		
		if (second.checkRecharge(Skills.SCISSORS_POKE))
		{
			damage += INTELADDTWO;
		}
		if (second.checkRecharge(Skills.PAPER_CUT))
		{
			damage += INTELADDTHREE;
		}
		if (second.checkRecharge(Skills.SHOOT_THE_MOON))
		{
			damage += INTELADDTWO;
		}
		
		return damage;
	}
}
