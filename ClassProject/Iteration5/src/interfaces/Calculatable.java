package interfaces;
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
	public abstract double calculateDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage a power type pet will do to another pet.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return double
	 */
	public abstract double calcPowerDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage a speed type pet will do to another pet.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage a intelligence type pet will do to another pet.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligenceDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage the skill shoot the moon will do to another pet. 
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcShootTheMoon(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage dealt by the Reversal of Fortune skill
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcReversalOfFortune(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of rock throw when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcPowerRTDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of scissor poke when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcPowerSPDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of paper cut when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcPowerPCDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of rock throw when used by a speed type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedRTDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of scissor poke when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedSPDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of paper cut when used by a power type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcSpeedPCDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of rock throw when used by a Intelligence type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligenceRTDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of scissor poke when used by a Intelligence type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligenceSPDamage(Playable first, Playable second, double randDamage);
	/**
	 * Calculates the damage of paper cut when used by a Intelligence type.
	 * @param first
	 * @param second
	 * @param randDamage
	 * @return
	 */
	public abstract double calcIntelligencePCDamage(Playable first, Playable second, double randDamage);

}
