package Game;
import Pets.PetTypes;
import Pets.Skills;
import interfaces.Calculatable;
import interfaces.Playable;

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
	public double calculateDamage(Playable first, Playable second, double randDamage)
	{
		if(first.getPetType() == PetTypes.POWER)
		{
			return calcPowerDamage(first, second, randDamage);
		}
		else if(first.getPetType() == PetTypes.SPEED)
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
	public double calcPowerDamage(Playable first, Playable second, double randDamage)
	{
		if(first.getSkill() == Skills.ROCK_THROW)
		{
			return calcPowerRTDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.SCISSORS_POKE)
		{
			return calcPowerSPDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.PAPER_CUT)
		{
			return calcPowerPCDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.SHOOT_THE_MOON)
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
	public double calcSpeedDamage(Playable first, Playable second, double randDamage)
	{
		if(first.getSkill() == Skills.ROCK_THROW)
		{
			return calcSpeedRTDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.SCISSORS_POKE)
		{
			return calcSpeedSPDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.PAPER_CUT)
		{
			return calcSpeedPCDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.SHOOT_THE_MOON)
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
	public double calcIntelligenceDamage(Playable first, Playable second, double randDamage)
	{
		if(first.getSkill() == Skills.ROCK_THROW)
		{
			return calcIntelligenceRTDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.SCISSORS_POKE)
		{
			return calcIntelligenceSPDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.PAPER_CUT)
		{
			return calcIntelligencePCDamage(first, second, randDamage);
		}
		else if(first.getSkill() == Skills.SHOOT_THE_MOON)
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
	public double calcShootTheMoon(Playable first, Playable second, double randDamage)
	{
		if (first.getSkillPrediction() == second.getSkill())
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
	public double calcReversalOfFortune(Playable first, Playable second, double randDamage)
	{
		double damage = randDamage + first.getDamageDifference();
		return damage;
	}
	
	/**
	 * Calculates power's version of rock throw.
	 */
	public double calcPowerRTDamage(Playable first, Playable second, double randDamage)
	{
		if(second.getSkill() == Skills.SCISSORS_POKE)
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
	public double calcPowerSPDamage(Playable first, Playable second, double randDamage)
	{
		if(second.getSkill() == Skills.PAPER_CUT)
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
	public double calcPowerPCDamage(Playable first, Playable second, double randDamage)
	{
		if(second.getSkill() == Skills.ROCK_THROW)
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
	public double calcSpeedRTDamage(Playable first, Playable second, double randDamage)
	{
		if((second.getCurrentHp() >= (second.getMaxHp() * SPEEDVALSF)) && 
				((second.getSkill() == Skills.SCISSORS_POKE) || 
						(second.getSkill() == Skills.PAPER_CUT)))
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
	public double calcSpeedSPDamage(Playable first, Playable second, double randDamage)
	{
		if((second.getCurrentHp() < (second.getMaxHp() * SPEEDVALSF)) && 
				(second.getCurrentHp() >= (second.getMaxHp() * SPEEDVALTF)) &&
				((second.getSkill() == Skills.ROCK_THROW) || 
						(second.getSkill() == Skills.PAPER_CUT)))
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
	public double calcSpeedPCDamage(Playable first, Playable second, double randDamage)
	{
		if((second.getCurrentHp() < (second.getMaxHp() * SPEEDVALTF)) && 
				((second.getSkill() == Skills.ROCK_THROW) || 
						(second.getSkill() == Skills.SCISSORS_POKE)))
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
	public double calcIntelligenceRTDamage(Playable first, Playable second, double randDamage)
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
	public double calcIntelligenceSPDamage(Playable first, Playable second, double randDamage)
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
	public double calcIntelligencePCDamage(Playable first, Playable second, double randDamage)
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
