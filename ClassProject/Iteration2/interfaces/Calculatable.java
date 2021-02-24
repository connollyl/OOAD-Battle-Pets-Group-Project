package interfaces;

import Pets.Pet;
/**
 * This interface defines the behavior for damage calcuators.
 * Author:
 */
public interface Calculatable 
{
	/**
	 * Calculates the damage that one pet does to another.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return double
	 */
	public abstract double calculateDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage a power type pet will do to another pet.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return double
	 */
	public abstract double calcPowerDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage a speed type pet will do to another pet.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage a intelligence type pet will do to another pet.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligenceDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage the skill shoot the moon will do to another pet. 
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcShootTheMoon(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage dealt by the Reversal of Fortune skill
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcReversalOfFortune(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of rock throw when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcPowerRTDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of scissor poke when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcPowerSPDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of paper cut when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcPowerPCDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of rock throw when used by a speed type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedRTDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of scissor poke when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedSPDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of paper cut when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedPCDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of rock throw when used by a Intelligence type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligenceRTDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of scissor poke when used by a Intelligence type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligenceSPDamage(Pet first, Pet second, double randDamage);
	/**
	 * Calculates the damage of paper cut when used by a Intelligence type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligencePCDamage(Pet first, Pet second, double randDamage);

}
